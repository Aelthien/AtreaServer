package atrea.server.game.entities.components;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;

import static atrea.server.game.entities.components.EComponentType.*;

public class BankComponent extends EntityComponent {

    private ItemContainer[] banks;

    @Override public EComponentType getComponentType() {
        return BANK;
    }

    public BankComponent(Entity parent) {
        super(parent);
    }

    @Override public void update() {

    }

    public boolean addItem(Item item, int bankIndex) {
        banks[bankIndex].addItem(item);

        return false;
    }

    public boolean addItemToTab(Item item, int tab) {
        //if (banks[tab].addItem(item))
        //  return true;

        return false;
    }
}