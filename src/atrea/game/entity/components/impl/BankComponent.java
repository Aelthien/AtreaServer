package atrea.game.entity.components.impl;

import atrea.game.content.items.Item;
import atrea.game.content.items.ItemContainer;
import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;

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