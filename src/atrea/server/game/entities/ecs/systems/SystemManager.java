package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.EntityManager;
import atrea.server.game.entities.ecs.bank.BankSystem;
import atrea.server.game.entities.ecs.chat.ChatSystem;
import atrea.server.game.entities.ecs.combat.CombatSystem;
import atrea.server.game.entities.ecs.command.ScriptSystem;
import atrea.server.game.entities.ecs.equipment.EquipmentSystem;
import atrea.server.game.entities.ecs.guild.GuildSystem;
import atrea.server.game.entities.ecs.interaction.InteractionSystem;
import atrea.server.game.entities.ecs.inventory.InventorySystem;
import atrea.server.game.entities.ecs.movement.MovementSystem;
import atrea.server.game.entities.ecs.skill.SkillsSystem;
import atrea.server.game.entities.ecs.status.StatusSystem;
import atrea.server.game.entities.ecs.transform.TransformSystem;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class SystemManager {

    private @Getter final MovementSystem movementSystem;
    private @Getter final TransformSystem transformSystem;
    private @Getter final SpeechSystem speechSystem;
    private @Getter final InventorySystem inventorySystem;
    private @Getter final BankSystem bankSystem;
    private @Getter final EquipmentSystem equipmentSystem;
    private @Getter final SkillsSystem skillsSystem;
    private @Getter final ItemCreationSystem itemCreationSystem;
    private @Getter final StatusSystem statusSystem;
    private @Getter final ChatSystem chatSystem;
    private @Getter final CombatSystem combatSystem;
    private @Getter final GuildSystem guildSystem;
    private @Getter final InteractionSystem interactionSystem;
    private @Getter final ScriptSystem scriptSystem;

    public @Getter int MAX_COMPONENTS = EntityManager.MAX_ENTITY_COUNT;

    private final Map<EComponentType, ComponentSystem<?>> componentSystems = new HashMap<>();

    public SystemManager() {
        componentSystems.put(TRANSFORM, transformSystem = new TransformSystem());
        componentSystems.put(MOVEMENT, movementSystem = new MovementSystem());
        componentSystems.put(INVENTORY, inventorySystem = new InventorySystem());
        componentSystems.put(BANK, bankSystem = new BankSystem());
        componentSystems.put(EQUIPMENT, equipmentSystem = new EquipmentSystem());
        componentSystems.put(SKILLS, skillsSystem = new SkillsSystem());
        componentSystems.put(ITEM_CREATION, itemCreationSystem = new ItemCreationSystem());
        componentSystems.put(STATUS, statusSystem = new StatusSystem());
        componentSystems.put(CHAT, chatSystem = new ChatSystem());
        componentSystems.put(COMBAT, combatSystem = new CombatSystem());
        componentSystems.put(GUILD, guildSystem = new GuildSystem());
        componentSystems.put(INTERACTION, interactionSystem = new InteractionSystem());
        componentSystems.put(SPEECH, speechSystem = new SpeechSystem());
        componentSystems.put(COMMAND, scriptSystem = new ScriptSystem());

        movementSystem.initialize(transformSystem, statusSystem);
        inventorySystem.initialize(bankSystem, equipmentSystem);
        equipmentSystem.initialize(inventorySystem, bankSystem, skillsSystem, statusSystem);
        bankSystem.initialize(inventorySystem, equipmentSystem);
        itemCreationSystem.initialize(inventorySystem, skillsSystem);
        statusSystem.initialize(combatSystem);
        combatSystem.initialize(statusSystem);
    }

    public void update() {
        movementSystem.update();
    }

    public void addComponent(ComponentDefinition definition, Entity entity) {
        componentSystems.get(definition.getType()).addComponent(definition, entity);
        entity.addUpdateFlag(definition.getType());
    }

    public void resetSystems() {
        for (ComponentSystem<?> system : componentSystems.values()) {
            system.reset();
        }
    }
}