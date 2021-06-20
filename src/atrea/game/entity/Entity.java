package atrea.game.entity;

import atrea.game.entity.components.EntityComponent;
import atrea.game.entity.components.impl.TransformComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private @Getter @Setter int id;
    private @Getter @Setter int index;
    private @Getter @Setter int uid;
    private @Getter @Setter EEntityType entityType;
    private @Getter @Setter
    TransformComponent transform;
    private @Getter @Setter String name = "n";
    private @Getter @Setter boolean registered;
    private Delegate onAddDelegate;
    private Delegate onRemoveDelegate;

    protected @Getter List<EntityComponent> components = new ArrayList<>();

    public void onAdd() {
        if (onAddDelegate != null) {
            onAddDelegate.execute();
        }
    }

    public void onRemove() {
        if (onRemoveDelegate != null) {
            onRemoveDelegate.execute();
        }
    }

    public Entity() {
        this.transform = new TransformComponent(this);
    }

    public <T extends EntityComponent> T getComponent(Class<T> type)
    {
        for (EntityComponent component : components)
        {
            if (component.getClass() == type)
                return (T) component;
        }

        return null;
    }

    public boolean hasComponent(String name)
    {
        for (EntityComponent component : components)
        {
            if (component.getClass().getName() == name)
                return true;
        }

        return false;
    }

    public boolean addComponent(EntityComponent component) {
        if (hasComponent(component.getClass().getName()))
            return false;

        components.add(component);
        component.setParent(this);

        return true;
    }

    public void sequence() {

    }

    public void setOnAddDelegate(Delegate delegate) {
        if (onAddDelegate == null)
            onAddDelegate = delegate;
    }
    public void setOnRemoveDelegate(Delegate delegate) {
        if (onRemoveDelegate == null)
            onRemoveDelegate = delegate;
    }
}