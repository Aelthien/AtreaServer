package atrea.net.database;

import atrea.game.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlayerSaving
{
    private Player player;
    private PreparedStatement statement;
    private Connection connection;
    private int userId;

    public PlayerSaving()
    {
    }

    public void save(Connection connection, Player player)
    {
        this.player = player;
        this.connection = connection;
        saveCharacter();
        saveSkills();
    }

    private boolean saveCharacter()
    {
        String sql = "UPDATE characters SET name = ?, clan = ?, posx = ?, posy = ?,"
                + " height = ?,  energy = ?, running = ?, spellbook = ?, prayerbook = ?,"
                + " retaliate = ?, xp_locked = ?, clanchat = ?, venged = ?, last_venge = ?,"
                + " special_attack = ?, special_timer = ?, poison_damage = ?,"
                + " poison_timer = ?, poison_immunity = ?, fire_immunity = ?,"
                + " skull_timer = ?, skull_type = ? WHERE id = ?";

        String name = player.getName();
        int clan = 0;
        int posX = player.getTransform().getPosition().getX();
        int posY = player.getTransform().getPosition().getY();
        /*int height = player.getPosition().getZ();
        int energy = player.getStatus().getEnergy();
        boolean running = player.isRunning();
        String spellbook = player.getSpellbook().name();
        String prayerbook = "NULL";
        boolean retaliate = player.getCombat().autoRetaliate();
        boolean xp_locked = player.experienceLocked();
        String clanchat = player.getClanChatName();
        boolean venged = player.hasVengeance();
        int last_venge = player.getVengeanceTimer().secondsRemaining();
        int special_attack = player.getSpecialPercentage();
        int special_timer = player.getSpecialAttackTimer().secondsRemaining();
        int poison_damage = player.getPoisonDamage();
        int poison_timer = 0;
        int poison_immunity = player.getCombat().getPoisonImmunityTimer().secondsRemaining();
        int fire_immunity = player.getCombat().getFireImmunityTimer().secondsRemaining();
        int skull_timer = player.getSkullTimer();
        String skull_type = player.getSkullType().name();

        try {
            statement = connection.prepareStatement(sql);

            statement.setString(1, name);
            statement.setInt(2, clan);
            statement.setInt(3, posX);
            statement.setInt(4, posY);
            statement.setInt(5, height);
            statement.setInt(6, energy);
            statement.setBoolean(7, running);
            statement.setString(8, spellbook);
            statement.setString(9, prayerbook);
            statement.setBoolean(10, retaliate);
            statement.setBoolean(11, xp_locked);
            statement.setString(12, clanchat);
            statement.setBoolean(13, venged);
            statement.setInt(14, last_venge);
            statement.setInt(15, special_attack);
            statement.setInt(16, special_timer);
            statement.setInt(17, poison_damage);
            statement.setInt(18, poison_timer);
            statement.setInt(19, poison_immunity);
            statement.setInt(20, fire_immunity);
            statement.setInt(21, skull_timer);
            statement.setString(22, skull_type);
            statement.setInt(23, userId);

            int response = statement.executeUpdate();

            if (response == 0)
                return false;

        } catch (SQLException e) {
            return false;
        }
*/
        return true;
    }

    private boolean saveSkills()
    {
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

    /*public static boolean playerExists(String p) {
        p = Misc.formatPlayerName(p.toLowerCase());
        return new File("./data/saves/characters/"+p+".json").exists();
    }*/
}
