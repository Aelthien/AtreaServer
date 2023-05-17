package atrea.server.game.entities.ecs.summon;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.utilities.Position;
import atrea.server.game.content.skills.ExperienceAward;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.data.definition.SummonDefinition;
import atrea.server.game.entities.EInteractableType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.follower.FollowerComponent;
import atrea.server.game.entities.ecs.follower.FollowerSystem;
import atrea.server.game.entities.ecs.skill.SkillsComponent;
import atrea.server.game.entities.ecs.skill.SkillsSystem;
import atrea.server.game.entities.ecs.systems.ComponentSystem;
import atrea.server.game.entities.ecs.transform.TransformSystem;

public class SummonSystem extends ComponentSystem<SummonComponent> {

    private FollowerSystem followerSystem;
    private SkillsSystem skillsSystem;
    private TransformSystem transformSystem;

    public SummonSystem(SkillsSystem skillsSystem, FollowerSystem followerSystem, TransformSystem transformSystem) {
        componentsArray = new SummonComponent[5000];

        this.followerSystem = followerSystem;
        this.skillsSystem = skillsSystem;
        this.transformSystem = transformSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new SummonComponent(definition, entity);
    }

    @Override public void update() {

    }

    public void summon(int entityId, int summonId) {
        FollowerComponent followerComponent = followerSystem.getComponent(entityId);

        if (followerComponent.hasMaxFollowerCount())
            return;

        SkillsComponent skillsComponent = skillsSystem.getComponent(entityId);

        SummonDefinition summonDefinition = DefinitionManager.getSummonDefinition(summonId);

        if (!skillsComponent.hasSkills(summonDefinition.getRequiredSkills()))
            return;

        Entity summonedEntity = GameManager.getEntityManager().createEntity(EInteractableType.NPC, summonDefinition.getEntityDefinitionId());
        followerComponent.addFollower(summonedEntity.getEntityId());

        Position position = transformSystem.getRandomPositionInRange(entityId, 8);

        transformSystem.setTransform(summonedEntity.getEntityId(), position, true, false, false, true);

        for (ExperienceAward experienceAward : summonDefinition.getExperienceAwards()) {
            skillsComponent.grantExperience(experienceAward);
        }
    }
}