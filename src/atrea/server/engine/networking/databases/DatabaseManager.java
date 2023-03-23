package atrea.server.engine.networking.databases;

import atrea.server.engine.accounts.*;
import atrea.server.engine.utilities.Position;
import atrea.server.game.content.items.Item;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.systems.SystemManager;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.LoginDetails;
import atrea.server.engine.networking.packet.RegisterDetails;
import atrea.server.engine.networking.session.LoginResponse;

import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static atrea.server.engine.networking.databases.NetworkOpcodes.*;

public class DatabaseManager {

    private Connection connection;
    private PreparedStatement statement;
    private Entity player;
    private @Setter Session session;
    private int userId;
    private final SystemManager systemManager;

    public DatabaseManager() {
        systemManager = GameManager.getSystemManager();
    }

    public void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
        try {
            if (connection == null)
                connection = AtreaConnectionPool.getConnection();

            assert connection != null;

            if (connection.isClosed())
                System.out.println("Connection is closed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean save() {
        openConnection();

        boolean success = saveCharacter();

        closeConnection();

        return success;
    }

    public void logout() {
        save();

        openConnection();

        ResultSet resultSet;

        String sql = "SELECT id FROM active_users WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LoginResponse login(LoginDetails loginPacket) {
        LoginResponse response = new LoginResponse();

        openConnection();

        ResultSet resultSet;

        int characterSlots = 4;
        String password = null;
        boolean banned = false;
        EAccountRole role = null;
        int[] characters = new int[characterSlots];

        String[] characterColumnNames = new String[characterSlots];

        for (int i = 0; i < characterSlots; i++) {
            characterColumnNames[i] = "character" + i;
        }

        String sql = String.format("SELECT id, password, banned, role, %s FROM users WHERE email = ?", String.join(",", characterColumnNames));

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, loginPacket.getEmail());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
                password = resultSet.getString("password");
                banned = resultSet.getBoolean("banned");
                role = EAccountRole.getRole(resultSet.getString("role"));

                for (int i = 0; i < characterSlots; i++) {
                    characters[i] = resultSet.getInt("character" + i);
                }
            } else
                response.setCode(INVALID_EMAIL);

            if (!password.equals(loginPacket.getPassword()))
                response.setCode(INVALID_INFORMATION);
            else if (banned)
                response.setCode(USER_BANNED);
            else {
                response.setCode(LOGIN_SUCCESSFUL);
                response.setUserId(userId);
                CharacterData[] characterDatum = loadCharacters(characters);
                response.setAccount(new Account(userId, role, characters, characterDatum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public CharacterData[] loadCharacters(int[] characterIds) {
        CharacterData[] characters = new CharacterData[characterIds.length];

        String sql = "SELECT slot, general, world FROM characters WHERE user_id = ? AND id in (?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            for (int i = 0; i < characterIds.length; i++) {
                statement.setInt(i + 2, characterIds[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int slot = resultSet.getByte("slot");

                List<Blob> datum = new ArrayList<>();

                datum.add(resultSet.getBlob("general"));
                /*
                datum.add(resultSet.getBlob("world"));
                datum.add(resultSet.getBlob("skills"));
                datum.add(resultSet.getBlob("inventory"));
                datum.add(resultSet.getBlob("equipment"));
                datum.add(resultSet.getBlob("bank"));
                datum.add(resultSet.getBlob("status"));
                datum.add(resultSet.getBlob("combat"));
                datum.add(resultSet.getBlob("abilities"));
                datum.add(resultSet.getBlob("skill_tree"));
                datum.add(resultSet.getBlob("quests"));
                */

                characters[slot] = DataSerialiser.characters.deserialise(characterIds[slot], datum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return characters;
    }

    public int register(RegisterDetails registerDetails) {
        connection = AtreaConnectionPool.getConnection();

        String email = registerDetails.getEmail();
        String password = registerDetails.getPassword();

        ResultSet rs;

        String sql = "SELECT email FROM users WHERE email = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println("Email taken");
                return EMAIL_TAKEN;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sql = "INSERT INTO users (email, password) VALUES(?,?)";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            statement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return REGISTRATION_SUCCESSFUL;
    }

    public boolean loadCharacter(Entity player, CharacterData characterData) {
        this.player = player;

        boolean success = false;

        try {
            ResultSet rs;

            String sql = "SELECT general FROM characters WHERE id = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, characterData.getId());

            rs = statement.executeQuery();

            if (rs.next()) {
                ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
                Blob blob = rs.getBlob("general");
                buffer.writeBytes(blob.getBytes(1, (int) blob.length()));

                int nameLength = buffer.readByte();
                String name = (String) buffer.readCharSequence(nameLength, Charset.defaultCharset());

                player.setName(name);

                systemManager.getMovementSystem().moveEntity(player.getEntityId(), new Position(0, 0, 0), false);

                Item item = new Item(0, 1, true);

                GameManager.getSystemManager().getInventorySystem().getComponent(player.getEntityId()).addItem(item, true);

                success = true;
            } else {
                success = false;
                System.out.println("Failed to load character!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return success;
    }

    /**
     * Creates a new character in the SQL database.
     *
     * @param slot   The index of the character
     * @param name   The name of the character
     * @param gender The gender of the character
     * @return The character data with the id of 1) the key of
     * the row that the character was inserted or 2) -1 if the
     * insertion failed.
     */
    public CharacterData createCharacter(int slot, String name, EGender gender) {
        openConnection();

        if (session.getAccount().getCharacterIds()[slot] != -1)
            return null;

        String sql = "INSERT INTO characters (user_id, slot, general) VALUES (?, ?, ?)";

        CharacterData characterData = CharacterData.create(name, gender);

        try {;
            List<byte[]> dataList = DataSerialiser.characters.serialise(characterData);

            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.getGeneratedKeys();

            statement.setInt(1, userId);
            statement.setInt(2, slot);
            statement.setBlob(3, new ByteArrayInputStream(dataList.get(0)));

            if (statement.executeUpdate() != 0) {
                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    characterData.setId((int) resultSet.getLong(1));
                } else
                    return null;
            }

            session.getAccount().getCharacters()[slot] = characterData;

            sql = String.format("UPDATE users SET %s = ? WHERE id = ?", "character" + slot);

            statement = connection.prepareStatement(sql);

            statement.setInt(1, characterData.getId());
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return characterData;
    }

    private boolean saveCharacter() {
        String sql = "UPDATE characters SET general, skills, inventory, equipment WHERE userId = ?";

        try {
            statement = connection.prepareStatement(sql);

            //statement.setBlob(1, DatabaseSerializer.characters.serialise(player));
            statement.setBlob(2, DataSerialiser.skills.serialiseAll(systemManager.getSkillSystem().getComponent(player.getEntityId()).getSkills()));
            statement.setBlob(3, DataSerialiser.items.serialiseAll(systemManager.getInventorySystem().getComponent(player.getEntityId()).getInventory().getItems()));
            statement.setBlob(4, DataSerialiser.items.serialiseAll(systemManager.getEquipmentSystem().getComponent(player.getEntityId()).getEquipment().getItems()));
            statement.setInt(5, userId);

            int response = statement.executeUpdate();

            if (response == 0)
                return false;
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    public Account loadAccount(int userId) {
        String sql = "SELECT ";
        return null;
    }
}