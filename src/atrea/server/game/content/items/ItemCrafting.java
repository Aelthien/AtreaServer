package atrea.game.content.items;

import atrea.game.entity.Entity;

public class ItemCrafting {
    
    public boolean craftItem(int recipeId, Entity player) {
        /*RecipeDefinition definition = RecipeDefinition.getDefinition(recipeId);

        InventoryComponent inventory = player.getComponent(InventoryComponent.class);
        SkillComponent skills = player.getComponent(SkillComponent.class);

        Item primary = new Item(definition.getPrimaryItem().getId(), definition.getPrimaryItem().getAmount());
        Item secondary = new Item(definition.getSecondaryItem().getId(), definition.getSecondaryItem().getAmount());

        for (RequiredSkill requiredSkill : definition.getRequiredSkills()) {
            if (!skills.hasSkill(requiredSkill.getSkill(), requiredSkill.getLevel()))
                return false;
        }

        if (!inventory.getInventory().hasItem(primary))
            return false;

        if (inventory.getInventory().hasItem(secondary))
            return false;

        inventory.getInventory().remove(primary);
        inventory.getInventory().remove(secondary);

        inventory.addItem(definition.getProducedItem(), true);
*/
        return true;
    }
}