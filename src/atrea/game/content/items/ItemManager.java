package atrea.game.content.items;

import atrea.game.entity.Player;

public class ItemManager
{
    private Player player;

    public ItemManager(Player player)
    {
        this.player = player;
    }

    public void swap(ItemContainer from, int fromSlot, ItemContainer to, int toSlot)
    {

    }

    public void sendInventory() {
        //player.getPlayerSession().getMessageSender().sendItemContainer(player.getComponent(Inventory.class));
    }
}