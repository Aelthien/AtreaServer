package atrea.net.database;

import atrea.game.content.items.Item;
import atrea.game.content.skills.ESkill;
import atrea.game.entity.components.impl.SkillComponent;
import atrea.game.entity.components.impl.EquipmentComponent;
import atrea.game.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerLoading {
    private Player player;
    private PreparedStatement statement;
    private Connection connection;
    private int userId;

    public PlayerLoading(Player player) {
        this.player = player;
    }

    public void load(Connection connection) {
        SkillComponent skillManager = player.getComponent(SkillComponent.class);
        //Inventory inventory = player.getComponent(Inventory.class);
        //Equipment equipment = player.getComponent(Equipment.class);

        this.connection = connection;
        userId = player.getId();
        if (loadCharacter()) {
            loadSkills(skillManager);
            //loadEquipment();
            //loadInventory(inventory);
            loadBank();
            getPlayerAppearance();
        }

        /*player.getMessageSender().sendName();
        player.getMessageSender().sendSkills();
        player.getMessageSender().sendInventory();
        player.getMessageSender().sendEquipment();
        player.getMessageSender().sendBank();*/
    }

    private boolean loadCharacter() {
        boolean success = false;

        ResultSet rs;

        String sql = "SELECT id, name, level, clan, posx, posy,"
                + " height, energy, running, spellbook, prayerbook,"
                + " fight_type, retaliate, xp_locked, clanchat, venged, last_venge,"
                + " special_attack, special_timer, poison_damage,"
                + " poison_timer, poison_immunity, fire_immunity,"
                + " skull_timer, skull_type FROM characters WHERE user_id = ?";

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
                ;
                //MagicSpellbook spellbook = MagicSpellbook.valueOf(rs.getString("spellbook"));
                //FightType fight_type = FightType.valueOf(rs.getString("fight_type"));
                boolean retaliate = rs.getBoolean("retaliate");
                boolean xp_locked = rs.getBoolean("xp_locked");
                String clanchat = rs.getString("clanchat");
                boolean venged = rs.getBoolean("venged");
                int last_venge = rs.getInt("last_venge");
                int special_attack = rs.getInt("special_attack");
                int special_timer = rs.getInt("id");
                int poison_damage = rs.getInt("poison_damage");
                int poison_timer = rs.getInt("poison_timer");
                int poison_immunity = rs.getInt("poison_immunity");
                int fire_immunity = rs.getInt("fire_immunity");
                int skull_timer = rs.getInt("skull_timer");
                //SkullType skull_type = SkullType.valueOf(rs.getString("skull_type"));

                System.out.printf(name);
                player.setName(name);
                //player.setClanChatName(clanchat);
                //player.setPosition(new Position(posX, posY, height));
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

                success = true;
            } else {
                System.out.println("Character not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    private boolean loadSkills(SkillComponent skillManager) {
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

        sql += "experience, skillpoints, ";

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

        System.out.println(sql);
        try {
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            rs = statement.executeQuery();

            if (rs.next()) {
                experience = rs.getInt("experience");
                skillpoints = rs.getInt("skillpoints");

                for (int i = 0; i < skillNumber; i++) {
                    maxLevels[i] = rs.getInt(maxLevelLabels[i]);
                }

                for (int i = 0; i < skillNumber; i++) {
                    currentLevels[i] = rs.getInt(currentLevelLabels[i]);
                }

                skillManager.setAllMaxLevels(maxLevels);
                skillManager.setAllCurrentLevels(currentLevels);
                //skillManager.setExperience(experience);
                //skillManager.setSkillpoints(skillpoints);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    private void loadEquipment(EquipmentComponent equipment) {
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

                equipment.addItem(new Item(item, amount));
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

            statement.setInt(1, player.getId());

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

        String sql = "SELECT item_id, amount, slot, quality FROM inventory WHERE user_id = ?";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            rs = statement.executeQuery();

            int item;
            int amount;
            int slot;
            int quality;

            //Inventory inventory = player.getInventory();

            while (rs.next()) {
                item = rs.getInt("item_id");
                amount = rs.getInt("amount");
                slot = rs.getInt("slot");
                quality = rs.getInt("quality");

                //inventory.setItem(slot, new Item(item, amount, quality));
            }
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
}
