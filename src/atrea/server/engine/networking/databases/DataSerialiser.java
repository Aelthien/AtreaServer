package atrea.server.engine.networking.databases;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.engine.accounts.CharacterGeneralData;
import atrea.server.engine.accounts.CharacterWorldData;
import atrea.server.engine.accounts.ELocation;
import atrea.server.game.entities.ecs.systems.SystemManager;
import atrea.server.engine.utilities.Position;
import atrea.server.game.content.items.Item;
import atrea.server.game.content.skills.ESkill;
import atrea.server.game.content.skills.Skill;
import atrea.server.engine.utilities.ByteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static atrea.server.engine.networking.databases.EGender.*;

/**
 * Static class with subclasses containing functions
 * for serialisation and deserialisation of data into and from
 * {@link Blob} objects for storage in a sql database
 */
public class DataSerialiser {

    public static ItemSerializer items = new ItemSerializer();
    public static SkillSerializer skills = new SkillSerializer();
    public static CharacterDataSerializer characters = new CharacterDataSerializer();

    protected static SystemManager systemManager;

    public static class ItemSerializer {
        private byte[] serialise(Item item) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                outputStream.write(ByteUtils.intToBytes(item.getId()));
                outputStream.write(item.getSlot());
                outputStream.write(item.getAmount());
                outputStream.write(item.isHighQuality() ? 1 : 0);
                outputStream.write(ByteUtils.shortToBytes(item.getCharges()));
                outputStream.write(item.getCondition());

                Item[] mods = item.getMods();

                outputStream.write(mods.length);

                for (Item mod : mods) {
                    outputStream.writeBytes(serialise(mod));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return outputStream.toByteArray();
        }

        public Blob serialiseAll(Item[] items) throws SQLException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            for (Item item : items) {
                if (item.getId() == -1)
                    continue;

                try {
                    outputStream.write(serialise(item));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return new SerialBlob(outputStream.toByteArray());
        }

        private Item deserialise(ByteBuf buffer) {
            int id = buffer.readInt();
            int slot = buffer.readByte();
            int amount = buffer.readByte();
            boolean highQuality = buffer.readBoolean();
            int charges = buffer.readByte();
            int condition = buffer.readByte();
            int modCount = buffer.readByte();

            Item[] mods = new Item[modCount];

            if (modCount != 0) {
                for (int i = 0; i < modCount; i++) {
                    mods[i] = deserialise(buffer);
                }
            }

            return new Item(id, slot, amount, highQuality, charges, condition, mods, true);
        }

        public Item[] deserialiseAll(ByteBuf buffer) {
            List<Item> items = new ArrayList<>();

            while (buffer.isReadable()) {
                items.add(deserialise(buffer));
            }

            buffer.release();

            return items.toArray(new Item[0]);
        }
    }

    public static class SkillSerializer {
        private byte[] serialise(Skill skill) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                outputStream.write(skill.getSkill().ordinal());
                outputStream.write(skill.getCurrentLevel());
                outputStream.write(skill.getMaxLevel());
                outputStream.write(ByteUtils.intToBytes(skill.getExperience()));
                outputStream.write(skill.isMinorSkill() ? 1 : 0);
                outputStream.write(skill.isExperienceLocked() ? 1 : 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return outputStream.toByteArray();
        }

        public Blob serialiseAll(Skill[] skills) throws SQLException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                for (Skill skill : skills) {
                    outputStream.write(serialise(skill));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new SerialBlob(outputStream.toByteArray());
        }

        private Skill deserialise(ByteBuf buffer) {
            ESkill skill = ESkill.getSkill(buffer.readByte());
            int currentLevel = buffer.readByte();
            int maxLevel = buffer.readByte();
            int experience = buffer.readInt();
            boolean minorSkill = buffer.readByte() == 1;
            boolean experienceLocked = buffer.readByte() == 1;

            return new Skill(skill, currentLevel, maxLevel, experience, minorSkill, experienceLocked);
        }

        public Skill[] deserialiseAll(ByteBuf buffer) {
            List<Skill> skills = new ArrayList<>();

            while (buffer.isReadable()) {
                skills.add(deserialise(buffer));
            }

            buffer.release();

            return skills.toArray(new Skill[0]);
        }
    }

    public static class CharacterDataSerializer {
        public List<byte[]> serialise(CharacterData data) {
            List<byte[]> dataList = new ArrayList<>();

            try {
                dataList.add(serialiseGeneralData(data.getGeneralData()));
                //dataList.add(serializeWorldData(data.getWorldData()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dataList;
        }

        public CharacterData deserialise(int id, List<Blob> dataList) throws SQLException {
            CharacterGeneralData generalData = deserialiseGeneralData(dataList.get(0));
            //CharacterWorldData worldData = deserialiseWorldData(dataList.get(1), buffer);

            return new CharacterData(id, generalData, null);
        }

        public CharacterGeneralData deserialiseGeneralData(Blob data) throws SQLException {
            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            int nameLength = buffer.readByte();
            String name = buffer.readCharSequence(nameLength, Charset.defaultCharset()).toString();
/*
            EGender gender = buffer.readByte() == 0 ? MALE : FEMALE;
            int level = buffer.readByte();
            int guild = buffer.readInt();
            ELocation location = ELocation.getLocation(buffer.readByte());
*/
            return new CharacterGeneralData(name, MALE, 1, -1, ELocation.EMERALD_ISLE);
        }

        private CharacterWorldData deserialiseWorldData(Blob data, ByteBuf buffer) throws SQLException {
            buffer.clear();
            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            int x = buffer.readInt();
            int y = buffer.readInt();
            int height = buffer.readByte();
            boolean running = buffer.readByte() == 1;

            return new CharacterWorldData(new Position(x, y, height), running);
        }

        public byte[] serialiseGeneralData(CharacterGeneralData data) throws SQLException {
            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

            buffer.writeByte(data.getName().length());
            buffer.writeCharSequence(data.getName(), Charset.defaultCharset());

            byte[] bytes = new byte[buffer.readableBytes()];

            buffer.readBytes(bytes);
            buffer.release();

            return bytes;
        }

        private byte[] serializeWorldData(CharacterWorldData data, ByteBuf buffer) throws SQLException {
            byte[] bytes = new byte[0];
            buffer.clear();
            buffer.writeInt(data.getPosition().getX());
            buffer.writeInt(data.getPosition().getY());
            buffer.writeByte(data.getPosition().getHeight());
            buffer.writeByte(data.isRunning() ? 1 : 0);

            buffer.readBytes(bytes);

            return bytes;
        }
    }
}