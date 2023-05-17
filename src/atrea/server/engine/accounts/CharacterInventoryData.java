package atrea.server.engine.accounts;

import atrea.server.game.content.items.Item;
import atrea.server.game.entities.ecs.inventory.InventoryComponent;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class CharacterInventoryData {
    private @Getter List<Item> inventory;

    public CharacterInventoryData(InventoryComponent inventoryComponent) {
        this.inventory = Arrays.asList(inventoryComponent.getItemContainer().getItems());
    }

    public CharacterInventoryData(List<Item> items) {
        this.inventory = items;
    }
}