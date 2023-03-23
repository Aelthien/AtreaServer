package atrea.server.game.entities.ecs;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.content.items.EEquipmentSlot;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.data.definition.ItemDefinition;
import lombok.Getter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class EquipmentComponent extends EntityComponent {

    private @Getter ItemContainer equipment;

    @Override public EComponentType getComponentType() {
        return EQUIPMENT;
    }

    public EquipmentComponent(Entity parent) {
        super(parent);
    }

    public void addItem(Item item) {
        EEquipmentSlot slot = DefinitionManager.getItemDefinition(item.getId()).getEquipmentSlot();

        equipment.setItem(item, slot.ordinal());
    }
}