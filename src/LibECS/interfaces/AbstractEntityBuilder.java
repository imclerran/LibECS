package LibECS.interfaces;

import LibECS.EntityManager;

import java.util.ArrayList;

/**
 * An builder interface which facilitates the creation of entities,
 * and their related components and systems.
 * <p>
 * This builder can be passed to ECSEngine to add the full set of
 * entity, components, and systems to their respective managers.</p>
 */
public abstract class AbstractEntityBuilder {
    protected Integer _id;

    public void startBuild() {
        _id = EntityManager.getInstance().acquireEntityId();
    }

    public void finishBuild() {
        _id = null;
    }

    protected void checkIdNotNull() {
        if(null == _id) {
            throw new NullPointerException("_id cannot be null. Was startBuild() called? *Note: ECSEngine.addEntity() does this automatically.");
        }
    }

    public abstract IEntity buildEntity();
    public abstract ArrayList<IComponent> buildComponentList();
    public abstract ArrayList<ISystem> buildSystemList();
}
