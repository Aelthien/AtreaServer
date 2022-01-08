package atrea.server.engine.networking.databases;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.engine.accounts.CharacterGeneralData;
import atrea.server.engine.accounts.CharacterWorldData;
import atrea.server.engine.accounts.ELocation;
import atrea.server.engine.entities.systems.SystemManager;
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
 * {@link Blob} objects for storage in a sql database */
public class DatabaseSerializer {

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
                outputStream.write(item.getQuality());
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
            int quality = buffer.readByte();
            int charges = buffer.readByte();
            int condition = buffer.readByte();
            int modCount = buffer.readByte();

            Item[] mods = new Item[modCount];

            if (modCount != 0) {
                for (int i = 0; i < modCount; i++) {
                    mods[i] = deserialise(buffer);
                }
            }

            return new Item(id, slot, amount, quality, charges, condition, mods, true);
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
        public List<Blob> serialise(CharacterData data) throws SQLException {
            List<Blob> dataBlobs = new ArrayList<>();
            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

            dataBlobs.add(serialiseGeneralData(data.getGeneralData(), buffer));
            dataBlobs.add(serializeWorldData(data.getWorldData(), buffer));

            return dataBlobs;
        }

        public CharacterData deserialise(List<Blob> dataList) throws SQLException {
            CharacterData data = null;
            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

            //CharacterGeneralData generalData = deserialiseGeneralData(dataList.get(0), buffer);
            //CharacterWorldData worldData = deserialiseWorldData(dataList.get(1), buffer);

            return data;
        }

        public CharacterGeneralData deserialiseGeneralData(int characterId, Blob data, ByteBuf buffer) throws SQLException {
            buffer.clear();
            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            int nameLength = buffer.readByte();
            String name = buffer.readCharSequence(nameLength, Charset.defaultCharset()).toString();

            EGender gender = buffer.readByte() == 0 ? MALE : FEMALE;
            int level = buffer.readByte();
            int guild = buffer.readInt();
            ELocation location = ELocation.getLocation(buffer.readByte());

            return new CharacterGeneralData(characterId, name, gender, level, guild, location);
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

        private Blob serialiseGeneralData(CharacterGeneralData data, ByteBuf buffer) throws SQLException {
            buffer.clear();
            buffer.writeByte(data.getName().length());
            buffer.writeCharSequence(data.getName(), Charset.defaultCharset());
            buffer.writeInt(data.getLevel());
            buffer.writeInt(data.getGuild());

            return new SerialBlob(buffer.array());
        }

        private Blob serializeWorldData(CharacterWorldData data, ByteBuf buffer) throws SQLException {
            buffer.clear();
            buffer.writeInt(data.getPosition().getX());
            buffer.writeInt(data.getPosition().getY());
            buffer.writeByte(data.getPosition().getHeight());
            buffer.writeByte(data.isRunning() ? 1 : 0);

            return new SerialBlob(buffer.array());
        }
    }
}