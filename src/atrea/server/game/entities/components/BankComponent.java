package atrea.server.game.entity.components;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.entity.Entity;

public class BankComponent extends EntityComponent {

    private ItemContainer[] banks;

    public BankComponent(Entity parent) {
        super(parent);
    }

    public boolean addItem(Item item)
    {
        for (ItemContainer bank : banks)
        {
            if (bank.addItem(item))
                return true;
        }

        return false;
    }

    public boolean addItemToTab(Item item, int tab)
    {
        //if (banks[tab].addItem(item))
          //  return true;

        return false;
    }
}