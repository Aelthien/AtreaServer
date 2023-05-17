package atrea.server.engine.accounts;

import atrea.server.game.content.items.Item;
import atrea.server.game.entities.ecs.equipment.EquipmentComponent;
import lombok.Getter;

import java.util.List;

public class CharacterEquipmentData {
    private @Getter List<Item> equipment;

    public CharacterEquipmentData(EquipmentComponent equipmentComponent) {
        this.equipment = List.of(equipmentComponent.getItemContainer().getItems());
    }

    public CharacterEquipmentData(List<Item> items) {
        this.equipment = items;
    }
}