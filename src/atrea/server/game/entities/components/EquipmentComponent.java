package atrea.server.game.entities.components;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.content.items.EEquipmentSlot;
import atrea.server.game.data.definition.ItemDefinition;
import lombok.Getter;

import static atrea.server.game.entities.components.EComponentType.*;

public class EquipmentComponent extends EntityComponent {

    private @Getter ItemContainer equipment;

    @Override public EComponentType getComponentType() {
        return EQUIPMENT;
    }

    public EquipmentComponent(Entity parent) {
        super(parent);
    }

    @Override public void update() {

    }

    public void addItem(Item item) {
        EEquipmentSlot slot = ItemDefinition.getDefinition(item.getId()).getEquipmentSlot();

        equipment.setItem(item, slot.ordinal());
    }
}