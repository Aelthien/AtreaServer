package atrea.server.engine.entities.systems;

import atrea.server.engine.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.components.systems.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ComponentSystemManager {

    private @Getter final MovementSystem movementSystem;
    private @Getter final TransformSystem transformSystem;
    private @Getter final InventorySystem inventorySystem;
    private @Getter final BankSystem bankSystem;
    private @Getter final EquipmentSystem equipmentSystem;
    private @Getter final SkillSystem skillSystem;
    private @Getter final ItemCreationSystem itemCreationSystem;
    private @Getter final StatusSystem statusSystem;
    private @Getter final ChatSystem chatSystem;
    private @Getter final CombatSystem combatSystem;
    private @Getter final GuildSystem guildSystem;

    private final Map<String, ComponentSystem<?>> componentSystems = new HashMap<>();

    public ComponentSystemManager() {
        componentSystems.put("transform", transformSystem = new TransformSystem());
        componentSystems.put("movement", movementSystem = new MovementSystem());
        componentSystems.put("inventory", inventorySystem = new InventorySystem());
        componentSystems.put("bank", bankSystem = new BankSystem());
        componentSystems.put("equipment", equipmentSystem = new EquipmentSystem());
        componentSystems.put("skill", skillSystem = new SkillSystem());
        componentSystems.put("itemCreation", itemCreationSystem = new ItemCreationSystem());
        componentSystems.put("status", statusSystem = new StatusSystem());
        componentSystems.put("chat", chatSystem = new ChatSystem());
        componentSystems.put("combat", combatSystem = new CombatSystem());
        componentSystems.put("guild", guildSystem = new GuildSystem());

        movementSystem.initialize(transformSystem);
        inventorySystem.initialize(bankSystem, equipmentSystem);
        equipmentSystem.initialize(inventorySystem, bankSystem, skillSystem, statusSystem);
        bankSystem.initialize(inventorySystem, equipmentSystem);
        itemCreationSystem.initialize(inventorySystem, skillSystem);
        statusSystem.initialize(combatSystem);
        combatSystem.initialize(statusSystem);
    }

    public void update() {
        componentSystems.values().forEach(ComponentSystem::update);
    }

    public void addComponent(ComponentDefinition definition, Entity entity) {
        componentSystems.get(definition.getType()).addComponent(definition, entity);
    }
}