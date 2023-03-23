package atrea.server.game.data.definition;

import atrea.server.game.content.items.EEquipmentSlot;
import atrea.server.game.content.items.EItemType;
import atrea.server.game.entities.EntityStatusEffect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ItemDefinition {

    private static List<ItemDefinition> itemDefinitions = new ArrayList<>();

    private @Getter int id;
    private @Getter String name;
    private @Getter EItemType type;
    private @Getter int maxCondition;
    private @Getter int maxCharges;
    private @Getter float weight;
    private @Getter int[] compatibleMods;
    private @Getter int modSlots;
    private @Getter EntityStatusEffect[] effects;
    private @Getter boolean stackable;
    private @Getter int maxStack;
    private @Getter int maxStorageStack;
    private @Getter
    EEquipmentSlot equipmentSlot;
}