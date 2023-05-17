package atrea.server.game.entities.ecs.bank;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class BankComponent extends EntityComponent {

    private ItemContainer[] banks;

    @Override public EComponentType getComponentType() {
        return BANK;
    }

    public BankComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

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