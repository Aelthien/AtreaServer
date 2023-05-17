package atrea.server.game.entities.ecs.skill;

import atrea.server.engine.accounts.CharacterSkillsData;
import atrea.server.game.content.skills.Skill;
import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

public class SkillsSystem extends ComponentSystem<SkillsComponent> {

    public SkillsSystem() {
        componentsArray = new SkillsComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new SkillsComponent(entity);
    }

    @Override public void update() {

    }

    public void setSkills(int entityId, Skill[] skills) {
        SkillsComponent skillsComponent = getComponent(entityId);

        skillsComponent.setSkills(skills);
    }

    public void setSkills(int entityId, CharacterSkillsData skillsData) {
        setSkills(entityId, skillsData.getSkills().toArray(new Skill[0]));
    }
}