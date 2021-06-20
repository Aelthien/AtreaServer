package atrea.game.data.definition;

import java.util.ArrayList;
import java.util.List;

public class ItemDefinition
{
    private static List<ItemDefinition> itemDefinitions = new ArrayList<>();

    private int id;
    private String name;
    private int maxCondition;
    private int maxCharges;
    private int weight;

    private boolean stackable;
    private int maxStack;
    private int maxStorageStack;

    private EEquipmentSlot eEquipmentSlot;

    public static ItemDefinition getDefinition(int id)
    {
        return itemDefinitions.get(id);
    }

    public boolean isStackable() {
        return stackable;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public EEquipmentSlot geteEquipmentSlot() {
        return eEquipmentSlot;
    }
}