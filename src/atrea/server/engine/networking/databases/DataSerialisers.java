package atrea.server.engine.networking.databases;

import atrea.server.engine.accounts.*;
import atrea.server.engine.utilities.Position;
import atrea.server.game.content.items.Item;
import atrea.server.game.content.skills.ESkill;
import atrea.server.game.content.skills.Skill;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static atrea.server.engine.networking.databases.EGender.*;

/**
 * Static class with an abstract serialiser and derived inner classes
 * containing functions for serialisation and deserialisation of data to and from
 * {@link Blob} objects for storage in an SQL database
 */
public class DataSerialisers {
    public static GeneralSerialiser general = new GeneralSerialiser();
    public static WorldSerialiser world = new WorldSerialiser();
    public static InventorySerialiser inventory = new InventorySerialiser();
    public static SkillsSerialiser skills = new SkillsSerialiser();
    public static EquipmentSerialiser equipment = new EquipmentSerialiser();

    public static abstract class Serialiser<T> {
        public abstract SerialBlob Serialise(T data, ByteBuf buffer) throws SQLException;
        public abstract T Deserialise(Blob data, ByteBuf buffer) throws SQLException;
    }

    public static class InventorySerialiser extends Serialiser<CharacterInventoryData> {
        @Override public SerialBlob Serialise(CharacterInventoryData data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            for (Item item : data.getInventory()) {
                buffer.writeInt(item.getId());
                buffer.writeByte(item.getSlot());
                buffer.writeByte(item.getAmount());
                buffer.writeBoolean(item.isHighQuality());
                buffer.writeByte(item.getCharges());
                buffer.writeByte(item.getCondition());
            }

            byte[] bytes = buffer.array();

            if (!bBufferProvided)
                buffer.release();

            return new SerialBlob(bytes);
        }

        @Override public CharacterInventoryData Deserialise(Blob data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            List<Item> items = new ArrayList<>();

            int id;
            int slot;
            int amount;
            boolean highQuality;
            int charges;
            int condition;
            int modCount;

            while (buffer.isReadable()) {
                id = buffer.readInt();
                slot = buffer.readByte();
                amount = buffer.readByte();
                highQuality = buffer.readBoolean();
                charges = buffer.readByte();
                condition = buffer.readByte();

                items.add(new Item(id, slot, amount, highQuality, charges, condition, null, true));
            }

            if (!bBufferProvided)
                buffer.release();

            return new CharacterInventoryData(items);
        }
    }

    public static class SkillsSerialiser extends Serialiser<CharacterSkillsData> {
        @Override public SerialBlob Serialise(CharacterSkillsData data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            List<Skill> skills = data.getSkills();

            for (Skill skill : skills) {
                buffer.writeByte(skill.getSkill().ordinal());
                buffer.writeByte(skill.getCurrentLevel());
                buffer.writeByte(skill.getMaxLevel());
                buffer.writeInt(skill.getExperience());
            }

            byte[] bytes = new byte[buffer.readableBytes()];

            buffer.readBytes(bytes);

            if (!bBufferProvided)
                buffer.release();

            return new SerialBlob(bytes);
        }

        @Override public CharacterSkillsData Deserialise(Blob data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            List<Skill> skills = new ArrayList<>();

            while (buffer.isReadable()) {
                ESkill skill = ESkill.getSkill(buffer.readByte());
                int currentLevel = buffer.readByte();
                int maxLevel = buffer.readByte();
                int experience = buffer.readInt();

                skills.add(new Skill(skill, currentLevel, maxLevel, experience));
            }

            if (!bBufferProvided)
                buffer.release();

            return new CharacterSkillsData(skills);
        }
    }
    public static class WorldSerialiser extends Serialiser<CharacterWorldData> {
        @Override public SerialBlob Serialise(CharacterWorldData data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeInt(data.getPosition().getX());
            buffer.writeInt(data.getPosition().getY());
            buffer.writeByte(data.getPosition().getHeight());
            buffer.writeByte(data.isRunning() ? 1 : 0);

            byte[] bytes = new byte[buffer.readableBytes()];

            buffer.readBytes(bytes);

            if (!bBufferProvided)
                buffer.release();

            return new SerialBlob(bytes);
        }

        @Override public CharacterWorldData Deserialise(Blob data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            int x = buffer.readInt();
            int y = buffer.readInt();
            int height = buffer.readByte();
            boolean running = buffer.readByte() == 1;

            if (!bBufferProvided)
                buffer.release();

            return new CharacterWorldData(new Position(x, y, height), running);
        }
    }

    public static class GeneralSerialiser extends Serialiser<CharacterGeneralData> {
        @Override public SerialBlob Serialise(CharacterGeneralData data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeByte(data.getName().length());
            buffer.writeCharSequence(data.getName(), Charset.defaultCharset());

            buffer.writeByte(data.getAge());
            buffer.writeByte(data.getGender().ordinal());

            byte[] bytes = new byte[buffer.readableBytes()];

            buffer.readBytes(bytes);

            if (!bBufferProvided)
                buffer.release();

            return new SerialBlob(bytes);
        }
        
        @Override public CharacterGeneralData Deserialise(Blob data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            int nameLength = buffer.readByte();
            String name = buffer.readCharSequence(nameLength, Charset.defaultCharset()).toString();
            EGender gender = buffer.readByte() == 0 ? MALE : FEMALE;
            int age = buffer.readByte();

            /*
            int level = buffer.readByte();
            int guild = buffer.readInt();
            ELocation location = ELocation.getLocation(buffer.readByte());
*/
            if (!bBufferProvided)
                buffer.release();

            return new CharacterGeneralData(name, gender, age, ELocation.EMERALD_ISLE);
        }
    }

    public static class EquipmentSerialiser extends Serialiser<CharacterEquipmentData> {
        @Override public SerialBlob Serialise(CharacterEquipmentData data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            for (Item item : data.getEquipment()) {
                buffer.writeInt(item.getId());
                buffer.writeByte(item.getSlot());
                buffer.writeByte(item.getAmount());
                buffer.writeBoolean(item.isHighQuality());
                buffer.writeByte(item.getCharges());
                buffer.writeByte(item.getCondition());
            }

            byte[] bytes = buffer.array();

            if (!bBufferProvided)
                buffer.release();

            return new SerialBlob(bytes);
        }

        @Override public CharacterEquipmentData Deserialise(Blob data, ByteBuf buffer) throws SQLException {
            boolean bBufferProvided = buffer != null;

            if (bBufferProvided) {
                buffer.clear();
            } else {
                buffer = PooledByteBufAllocator.DEFAULT.buffer();
            }

            buffer.writeBytes(data.getBytes(1, (int) data.length()));

            List<Item> items = new ArrayList<>();

            int id;
            int slot;
            int amount;
            boolean highQuality;
            int charges;
            int condition;
            int modCount;

            while (buffer.isReadable()) {
                id = buffer.readInt();
                slot = buffer.readByte();
                amount = buffer.readByte();
                highQuality = buffer.readBoolean();
                charges = buffer.readByte();
                condition = buffer.readByte();

                items.add(new Item(id, slot, amount, highQuality, charges, condition, null, true));
            }

            if (!bBufferProvided)
                buffer.release();

            return new CharacterEquipmentData(items);
        }
    }
}