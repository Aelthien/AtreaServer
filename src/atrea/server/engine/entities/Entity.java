package atrea.server.game.entity;

import lombok.Getter;
import lombok.Setter;

public class Entity {
    private @Getter @Setter int id;
    private @Getter @Setter int uid;
    private @Getter @Setter int entityId;
    private @Getter @Setter EEntityType entityType;
    private @Getter @Setter int state;
    private @Getter @Setter String name = "n";
    private @Getter @Setter boolean registered;
    private Delegate onAddDelegate;
    private Delegate onRemoveDelegate;

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

    public void setOnAddDelegate(Delegate delegate) {
        if (onAddDelegate == null)
            onAddDelegate = delegate;
    }
    public void setOnRemoveDelegate(Delegate delegate) {
        if (onRemoveDelegate == null)
            onRemoveDelegate = delegate;
    }
}