package atrea.server.game.entities.components.systems;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.entities.components.EComponentType;
import atrea.server.game.data.definition.ComponentDefinition;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static atrea.server.game.entities.components.EComponentType.*;

public class SystemManager {

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

    public @Getter int MAX_COMPONENTS = 5000;

    private final Map<EComponentType, ComponentSystem<?>> componentSystems = new HashMap<>();

    public SystemManager() {
        componentSystems.put(TRANSFORM, transformSystem = new TransformSystem());
        componentSystems.put(MOVEMENT, movementSystem = new MovementSystem());
        componentSystems.put(INVENTORY, inventorySystem = new InventorySystem());
        componentSystems.put(BANK, bankSystem = new BankSystem());
        componentSystems.put(EQUIPMENT, equipmentSystem = new EquipmentSystem());
        componentSystems.put(SKILL, skillSystem = new SkillSystem());
        componentSystems.put(ITEM_CREATION, itemCreationSystem = new ItemCreationSystem());
        componentSystems.put(STATUS, statusSystem = new StatusSystem());
        componentSystems.put(CHAT, chatSystem = new ChatSystem());
        componentSystems.put(COMBAT, combatSystem = new CombatSystem());
        componentSystems.put(GUILD, guildSystem = new GuildSystem());

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
        entity.addUpdateFlag(definition.getType());
    }
}