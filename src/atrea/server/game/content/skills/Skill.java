package atrea.game.content.skills;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Skill {

    private final @Getter ESkill skill;
    private final @Getter boolean minorSkill;

    private @Getter @Setter int maxLevel;
    private @Getter @Setter int currentLevel;
    private @Getter @Setter int experience;
    private @Getter @Setter boolean experienceLocked;

    public Skill(ESkill skill, boolean minorSkill) {
        this.skill = skill;
        this.maxLevel = 1;
        this.currentLevel = 1;
        this.minorSkill = minorSkill;
    }

    public void addExperience(int amount) {
        this.experience += amount;
    }

    public void subtractExperience(int amount) {
        int newAmount = experience - amount;

        if (newAmount < 0) {
            experience = 0;
            return;
        }

        experience = newAmount;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill1 = (Skill) o;
        return currentLevel == skill1.currentLevel && skill == skill1.skill;
    }

    @Override public int hashCode() {
        return Objects.hash(skill, currentLevel);
    }
}