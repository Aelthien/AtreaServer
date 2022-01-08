package atrea.game.data.definition;

import atrea.game.content.items.EEquipmentSlot;
import atrea.game.content.items.EItemType;
import atrea.game.entity.EntityStatusEffect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ItemDefinition {

    private static List<ItemDefinition> itemDefinitions = new ArrayList<>();

    private @Getter int id;
    private @Getter String name;
    private @Getter
    EItemType type;
    private @Getter int maxCondition;
    private @Getter int maxCharges;
    private @Getter int weight;
    private @Getter int[] compatibleMods;
    private @Getter int modSlots;
    private @Getter EntityStatusEffect[] effects;
    private @Getter boolean stackable;
    private @Getter int maxStack;
    private @Getter int maxStorageStack;
    private @Getter
    EEquipmentSlot equipmentSlot;

    public static ItemDefinition getDefinition(int id) {
        return itemDefinitions.get(id);
    }
}