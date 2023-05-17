package atrea.server.game.entities.ecs.equipment;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.content.items.EEquipmentSlot;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class EquipmentComponent extends EntityComponent {

    private @Getter ItemContainer itemContainer;

    @Override public EComponentType getComponentType() {
        return EQUIPMENT;
    }

    public EquipmentComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }

    public void addItem(Item item) {
        EEquipmentSlot slot = DefinitionManager.getItemDefinition(item.getId()).getEquipmentSlot();

        itemContainer.setItem(item, slot.ordinal());
    }

    public void equipItem(Item item, EEquipmentSlot slot) {
        itemContainer.setItem(item, slot.ordinal());
    }

    public void setItems(Item[] items) {
        for (int i = 0; i < items.length; i++) {
            itemContainer.setItem(items[i]);
        }
    }
}