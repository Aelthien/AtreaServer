package atrea.server.engine.networking.databases;

import atrea.server.engine.accounts.*;
import atrea.server.game.content.items.Item;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.systems.SystemManager;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.LoginDetails;
import atrea.server.engine.networking.packet.RegisterDetails;
import atrea.server.engine.networking.session.LoginResponse;

import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.Setter;

import java.sql.*;

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

    /**
     * Loads the character data from the database.
     *
     * @param characterIds The character ids to load.
     * @return The character data array.
     */
    public CharacterData[] loadCharacters(int[] characterIds) {
        CharacterData[] characters = new CharacterData[characterIds.length];

        String sql = "SELECT slot, general FROM characters WHERE user_id = ? AND id in (?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            for (int i = 0; i < characterIds.length; i++) {
                statement.setInt(i + 2, characterIds[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int slot = resultSet.getByte("slot");

                ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

                CharacterGeneralData generalData = DataSerialisers.general.Deserialise(resultSet.getBlob("general"), buffer);

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

                if (characterIds[slot] != -1)
                    characters[slot] = new CharacterData(characterIds[slot], generalData, null, null, null, null);
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

            String sql = "SELECT general, world, skills FROM characters WHERE id = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, characterData.getId());

            rs = statement.executeQuery();

            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

            if (rs.next()) {
                CharacterGeneralData generalData = DataSerialisers.general.Deserialise(rs.getBlob("general"), buffer);
                CharacterWorldData worldData = DataSerialisers.world.Deserialise(rs.getBlob("world"), buffer);
                CharacterSkillsData skillsData = DataSerialisers.skills.Deserialise(rs.getBlob("skills"), buffer);

                //CharacterInventoryData inventoryData = DataSerialisers.inventory.Deserialise(rs.getBlob("inventory"), buffer);
                //CharacterEquipmentData equipmentData = DataSerialisers.equipment.Deserialise(rs.getBlob("equipment"), buffer);

                player.setName(generalData.getName());

                systemManager.getMovementSystem().moveEntity(player.getEntityId(), worldData);
                systemManager.getSkillsSystem().setSkills(player.getEntityId(), skillsData);
                /*systemManager.getInventorySystem().setInventory(player.getEntityId(), inventoryData);
                systemManager.getEquipmentSystem().setEquipment(player.getEntityId(), equipmentData);

                Item item = new Item(0, 1, true);

                GameManager.getSystemManager().getInventorySystem().getComponent(player.getEntityId()).addItem(item, true);
*/
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
    public CharacterData createCharacter(int slot, String name, EGender gender, int age) {
        openConnection();

        if (session.getAccount().getCharacterIds()[slot] != -1)
            return null;

        String sql = "INSERT INTO characters (user_id, slot, general, world, skills) VALUES (?, ?, ?, ?, ?)";

        CharacterData characterData = CharacterData.create(name, gender, age);

        try {
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.getGeneratedKeys();

            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

            System.out.println("Starting to serialise data");
            statement.setInt(1, userId);
            statement.setInt(2, slot);
            statement.setBlob(3, DataSerialisers.general.Serialise(characterData.getGeneralData(), buffer));
            System.out.println("Serialised general data");
            statement.setBlob(4, DataSerialisers.world.Serialise(characterData.getWorldData(), buffer));
            System.out.println("Serialised world data");
            statement.setBlob(5, DataSerialisers.skills.Serialise(characterData.getSkillsData(), buffer));
            System.out.println("Serialised skills data");

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
        String sql = "UPDATE characters SET general, world, skills, inventory, equipment WHERE userId = ?";

        try {
            statement = connection.prepareStatement(sql);

            ByteBuf buffer = PooledByteBufAllocator.DEFAULT.directBuffer();

            CharacterGeneralData generalData = session.getAccount().getCurrentCharacterData().getGeneralData();
            CharacterWorldData worldData = session.getAccount().getCurrentCharacterData().getWorldData();
            CharacterSkillsData skillsData = new CharacterSkillsData(systemManager.getSkillsSystem().getComponent(player.getEntityId()));
            CharacterInventoryData inventoryData = new CharacterInventoryData(systemManager.getInventorySystem().getComponent(player.getEntityId()));
            CharacterEquipmentData equipmentData = new CharacterEquipmentData(systemManager.getEquipmentSystem().getComponent(player.getEntityId()));

            statement.setBlob(1, DataSerialisers.general.Serialise(generalData, buffer));
            statement.setBlob(2, DataSerialisers.world.Serialise(worldData, buffer));
            statement.setBlob(3, DataSerialisers.skills.Serialise(skillsData, buffer));
            statement.setBlob(4, DataSerialisers.inventory.Serialise(inventoryData, buffer));
            statement.setBlob(5, DataSerialisers.equipment.Serialise(equipmentData, buffer));

            statement.setInt(6, userId);

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