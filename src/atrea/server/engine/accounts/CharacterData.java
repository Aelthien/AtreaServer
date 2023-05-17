package atrea.server.engine.accounts;

import atrea.server.engine.networking.databases.EGender;
import atrea.server.engine.utilities.Position;
import atrea.server.game.content.items.Item;
import atrea.server.game.content.skills.ESkill;
import atrea.server.game.content.skills.Skill;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static atrea.server.game.content.skills.ESkill.*;

@Getter @Setter
public class CharacterData {
    private int id;
    private CharacterGeneralData generalData;
    private CharacterWorldData worldData;
    private CharacterSkillsData skillsData;
    private CharacterInventoryData inventoryData;
    private CharacterEquipmentData equipmentData;

    public CharacterData(int id, CharacterGeneralData generalData, CharacterWorldData worldData, CharacterSkillsData skillsData, CharacterInventoryData inventoryData, CharacterEquipmentData equipmentData) {
        this.id = id;
        this.generalData = generalData;
        this.worldData = worldData;
        this.skillsData = skillsData;
        this.inventoryData = inventoryData;
        this.equipmentData = equipmentData;
    }

    public CharacterData(CharacterGeneralData generalData, CharacterWorldData worldData, CharacterSkillsData skillsData, CharacterInventoryData inventoryData, CharacterEquipmentData equipmentData) {
        this.generalData = generalData;
        this.worldData = worldData;
        this.skillsData = skillsData;
        this.inventoryData = inventoryData;
        this.equipmentData = equipmentData;
    }

    public static CharacterData create(String name, EGender gender, int age) {
        List<Skill> skills = new ArrayList<>();

        skills.add(new Skill(ANIMANCY, 100, 100, 0));
        skills.add(new Skill(ALCHEMY, 100, 100, 0));
        skills.add(new Skill(ARCHERY, 100, 100, 0));
        skills.add(new Skill(LUMBERING, 100, 100, 0));
        skills.add(new Skill(MINING, 100, 100, 0));
        skills.add(new Skill(MELEE, 100, 100, 0));
        skills.add(new Skill(THAUMAT, 100, 100, 0));
        skills.add(new Skill(CONSTITUTION, 100, 100, 0));

        List<Item> inventory = new ArrayList<>();

        inventory.add(new Item(0, 1, true));

        List<Item> equipment = new ArrayList<>();

        return new CharacterData(
                new CharacterGeneralData(name, gender, age, ELocation.EMERALD_ISLE),
                new CharacterWorldData(new Position(), false),
                new CharacterSkillsData(skills),
                new CharacterInventoryData(inventory),
                new CharacterEquipmentData(equipment));

    }
}