package atrea.server.engine.net.database;

import atrea.server.game.content.skills.ESkill;
import atrea.server.game.entities.DatabaseProcedure;
import atrea.server.engine.entities.Entity;
import atrea.server.game.entities.components.InventoryComponent;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.RegisterDetails;
import atrea.server.engine.networking.packet.LoginDetails;
import atrea.server.engine.networking.session.LoginResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static atrea.server.engine.net.database.NetworkOpcodes.*;

public class DatabaseManager {
    private Connection connection;
    private PreparedStatement statement;
    private Entity player;

    private int userId;

    public DatabaseManager() {

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

    public void save() {
        openConnection();

        saveCharacter();
        //saveSkills();
        saveEquipment();
        saveInventory();
        saveBank();
        savePlayerAppearance();

        closeConnection();
    }

    private void savePlayerAppearance() {
    }

    private void saveBank() {
    }

    private void saveInventory() {

    }

    private void saveEquipment() {
    }

    public void load(Entity player) {
        this.player = player;
        player.setId(userId);

        if (loadCharacter()) {
            //loadSkills();
            //loadEquipment();
            loadInventory();
            //loadBank();
            //getPlayerAppearance();
        }

        closeConnection();
    }

    public void logout() {
        save();

        openConnection();

        ResultSet rs;
        String password = null;

        String sql = "SELECT id FROM active_users WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            rs = statement.executeQuery();

            if (rs.next()) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LoginResponse login(LoginDetails loginPacket) {
        openConnection();

        ResultSet rs;

        LoginResponse loginResponse = new LoginResponse();
        int response = 0;
        String password = null;
        boolean banned = false;

        String sql = "SELECT id, password, banned FROM users WHERE email = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, loginPacket.getEmail());

            rs = statement.executeQuery();

            if (rs.next()) {
                password = rs.getString("password");
                userId = rs.getInt("id");
                banned = rs.getInt("banned") == 1;
            }

            /*
            sql = "SELECT user_id FROM active_logins WHERE user_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            rs = statement.executeQuery();

            if (!rs.next()) {
                byte[] bytes = new byte[16];
                SecureRandom.getInstanceStrong().nextBytes(bytes);
                String key = new String(bytes);

                sql = "INSERT INTO active_logins(user_id, session_key) VALUES(?, ?) ON DUPLICATE KEY DO NOTHING";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, userId);
                statement.setString(2, key);
                statement.executeUpdate();
            }
            else
              response = ALREADY_LOGGED_IN;
            */

            if (!password.equals(loginPacket.getPassword()))
                response = INVALID_INFORMATION;
            else if (banned)
                response = USER_BANNED;
            //if server down
            //response = SERVER_ERROR;
            else {
                loginResponse.setCode(userId);
                response = LOGIN_SUCCESSFUL;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loginResponse.setCode(response);

        return loginResponse;
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

    private boolean loadCharacter() {
        boolean success = false;

        ResultSet rs;

        String sql = "SELECT id, name, level, clan, posx, posy,"
                + " height, energy, running, fight_type, retaliate,"
                + " xp_locked, clanchat"
                + " FROM characters WHERE user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            rs = statement.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
                String name = rs.getString("name");
                int level = rs.getInt("level");
                int clan = rs.getInt("clan");
                int posX = rs.getInt("posx");
                int posY = rs.getInt("posy");
                int height = rs.getInt("height");
                int energy = rs.getInt("energy");
                boolean running = rs.getBoolean("running");
                boolean retaliate = rs.getBoolean("retaliate");
                boolean xp_locked = rs.getBoolean("xp_locked");
                String clanChat = rs.getString("clanChat");

                player.setName(name);

                //player.setClanChatName(clanchat);
                //player.getTransform().setPosition(new Position(posX, posY, height));
                //player.getStatus().setEnergy(energy);
                //player.setRunning(running);
                /*player.getCombat().setAutoRetaliate(retaliate);
                player.setExperienceLocked(xp_locked);
                player.setHasVengeance(venged);
                player.getVengeanceTimer().start(last_venge);
                player.setSpecialPercentage(special_attack);
                player.getSpecialAttackTimer().start(special_timer);
                player.setPoisonDamage(poison_damage);
                player.getCombat().getPoisonImmunityTimer().start(poison_immunity);
                player.getCombat().getFireImmunityTimer().start(fire_immunity);
                player.setSkullTimer(skull_timer);
                player.setSkullType(skull_type);
                player.setSpellbook(spellbook);
                player.getCombat().setFightType(fight_type);*/

                //MessageSender messageSender = player.getPlayerSession().getMessageSender();

                //messageSender.sendName();
                //messageSender.sendMove(player.getTransform().getPosition(), true, true);

                success = true;
            } else {
                System.out.println("Character not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    private boolean loadSkills() {
        int experience;
        int skillpoints;

        String[] skillNames = ESkill.getNames();

        int skillNumber = skillNames.length;

        String[] currentLevelLabels = new String[skillNumber];
        String[] maxLevelLabels = new String[skillNumber];

        int[] maxLevels = new int[skillNumber];
        int[] currentLevels = new int[skillNumber];

        ResultSet rs;

        String sql = "SELECT ";

        String currentLevelLabel;
        String maxLevelLabel;

        sql += "experience, skillPoints, ";

        for (int i = 0; i < skillNumber; i++) {
            maxLevelLabel = skillNames[i] + "_max";
            maxLevelLabels[i] = maxLevelLabel;

            sql += maxLevelLabel + ", ";
        }

        for (int i = 0; i < skillNumber; i++) {
            currentLevelLabel = skillNames[i] + "_current";
            currentLevelLabels[i] = currentLevelLabel;

            sql += currentLevelLabel;

            if (i != skillNumber - 1)
                sql += ", ";
            else
                sql += " ";
        }

        sql += "FROM skills WHERE user_id = ?";

        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            System.out.println(userId);
            rs = statement.executeQuery();

            if (rs.next()) {
                experience = rs.getInt("experience");
                skillpoints = rs.getInt("skillPoints");

                for (int i = 0; i < skillNumber; i++) {
                    maxLevels[i] = rs.getInt(maxLevelLabels[i]);
                }

                for (int i = 0; i < skillNumber; i++) {
                    currentLevels[i] = rs.getInt(currentLevelLabels[i]);
                }
                /*
                player.getSkillManager().setAllMaxLevels(maxLevels);
                player.getSkillManager().setAllCurrentLevels(currentLevels);
                player.getSkillManager().setExperience(experience);
                player.getSkillManager().setSkillPoints(skillPoints);*/
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    private void loadEquipment() {
        ResultSet rs;

        String sql = "SELECT item_id, amount, slot FROM equipment WHERE user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            rs = statement.executeQuery();

            int item;
            int amount;
            int slot;

            while (rs.next()) {
                item = rs.getInt("item_id");
                amount = rs.getInt("amount");
                slot = rs.getInt("slot");

                //player.getEquipment().addItem(new Item(item, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getPlayerAppearance() {
        ResultSet rs;

        String sql = "SELECT gender, head, chest, arms, hands, legs, feet, beard,"
                + " hair_colour, chest_colour, leg_colour, feet_colour, skin_colour"
                + " FROM appearance WHERE user_id = ?";

        int gender, head, chest, arms, hands, legs, feet, beard, hair_colour,
                chest_colour, leg_colour, feet_colour, skin_colour;

        int[] array;

        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            rs = statement.executeQuery();

            if (rs.next()) {
                gender = rs.getInt("gender");
                head = rs.getInt("head");
                chest = rs.getInt("chest");
                arms = rs.getInt("arms");
                hands = rs.getInt("hands");
                legs = rs.getInt("legs");
                feet = rs.getInt("feet");
                beard = rs.getInt("beard");
                hair_colour = rs.getInt("hair_colour");
                chest_colour = rs.getInt("chest_colour");
                leg_colour = rs.getInt("leg_colour");
                feet_colour = rs.getInt("feet_colour");
                skin_colour = rs.getInt("skin_colour");

                array = new int[]{gender, head, chest, arms, hands, legs,
                        feet, beard, hair_colour, chest_colour, leg_colour,
                        feet_colour, skin_colour};

                //player.getAppearance().set(array);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        ResultSet rs;

        String sql = "SELECT item_id, amount, slot FROM inventory WHERE user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            rs = statement.executeQuery();

            int id;
            int amount;
            int slot;
            int quality;

            InventoryComponent inventory;// = player.getComponent(InventoryComponent.class);

            while (rs.next()) {
                id = rs.getInt("item_id");
                amount = rs.getInt("amount");
                slot = rs.getInt("slot");

                //inventory.setItem(new Item(id, amount), slot, false);
            }

            GameManager.getPlayerSessionManager().getPlayerSession(player).getMessageSender().sendInventory();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadBank() {
        ResultSet rs;

        String sql = "SELECT item_id, amount, slot FROM bank WHERE user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            rs = statement.executeQuery();

            int item;
            int amount;
            int slot;
            int tab;

            //Bank[] banks = player.getBanks();

            while (rs.next()) {
                item = rs.getInt("item_id");
                amount = rs.getInt("amount");
                slot = rs.getInt("slot");
                tab = rs.getInt("tab");

                //banks[tab].addToSlot(new Item(item, amount), slot, false);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean saveCharacter() {
        String sql = "UPDATE characters SET ";

        String[] values = {
                "name", "guild", "posx", "posy", "height", "energy",
                "running", "retaliate", "xp_locked", "chat"
        };

        int amount = values.length;

        for (int i = 0; i < amount; i++) {
            sql += values[i] + " = ?";

            if (i == amount - 1)
                sql += ", ";
            else
                sql += " ";
        }

        sql += "WHERE id = ?";

        String name = player.getName();
        int guild = 0;
        int posX = 0;//player.getTransform().getPosition().getX();
        int posY = 0;//player.getTransform().getPosition().getY();

        try {
            statement = connection.prepareStatement(sql);

            statement.setString(1, name);
            statement.setInt(2, guild);
            statement.setInt(3, posX);
            statement.setInt(4, posY);
            statement.setInt(23, userId);

            int response = statement.executeUpdate();

            if (response == 0)
                return false;

        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    private boolean saveSkills() {
        /*String[] skillNames = Skill.getNames();
        int[] experience;
        int[] currentLevels;

        experience = player.getSkillManager().getSkills().getExperience();
        currentLevels = player.getSkillManager().getSkills().getLevel();

        int skillNumber = skillNames.length;

        String sql = "UPDATE skills SET ";

        for (int i = 0; i < skillNumber; i++)
        {
            sql += skillNames[i] + "_xp = ?";

            sql += ", ";
        }

        for (int i = 0; i < skillNumber; i++)
        {
            sql += skillNames[i] + "_current = ?";

            if (i != skillNumber - 1)
                sql += ", ";
            else
                sql += " ";
        }

        sql += "WHERE id = ?";

        try {
            statement = connection.prepareStatement(sql);

            for (int i = 0; i < skillNumber; i++)
            {
                statement.setInt(i + 1, experience[i]);
            }

            for (int i = skillNumber; i < skillNumber + skillNumber; i++)
            {
                statement.setInt(i + 1, currentLevels[i - skillNumber]);
            }

            statement.setInt(47, userId);

            int response = statement.executeUpdate();

            if (response == 0)
            {
                System.out.println("Failed to save skills.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Failed to save skills.");
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
*/
        return true;
    }

    public void save(DatabaseProcedure databaseProcedure) {
        openConnection();
        databaseProcedure.save(connection);
    }
}