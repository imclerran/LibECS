package LibECS;

import LibECS.interfaces.IComponent;
import LibECS.interfaces.IEntity;
import LibECS.interfaces.AbstractEntityBuilder;
import LibECS.interfaces.ISystem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Description goes here...
 * @author Ian McLerran
 */
public class ECSEngine {
    private static ECSEngine _engine;

    private ComponentManager _cm;
    private EntityManager _em;
    private EventManager _evm;
    private SystemManager _sm;

    private String[] _systemUpdateOrder;
    private ArrayList<Integer> _flaggedForRemoval;

    /**
     * A private constructor for the singleton pattern.
     *
     * @param systemUpdateOrder  an array of system types in the order in which they should be processed.
     */
    private ECSEngine(String[] systemUpdateOrder) {
        _systemUpdateOrder = systemUpdateOrder;
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
        _evm = EventManager.getInstance();
        _sm = SystemManager.getInstance();
        _flaggedForRemoval = new ArrayList<Integer>();
    }

    /**
     * Removes all entities that have been flagged for removal,
     * as well as their associated components and systems.
     */
    private void removeFlagged() {
        for(Integer id : _flaggedForRemoval) {
            removeEntity(id);
        }
        _flaggedForRemoval.clear();
    }

    /**
     * Singleton getter: Creates the ComponentManager if none exists, then returns it.
     *
     * @param systemUpdateOrder  an array of system types in the order in which they should be processed.
     * @return  the singleton ECSEngine.
     */
    public static ECSEngine getInstance(String[] systemUpdateOrder) {
        if(null == _engine) {
            _engine = new ECSEngine(systemUpdateOrder);
        }
        return _engine;
    }

    /**
     * Getters for manager classes.
     */
    public ComponentManager getComponentManager() { return _cm; }
    public EntityManager getEntityManager() { return _em; }
    public EventManager getEventManager() { return _evm; }
    public SystemManager getSystemManager() { return _sm; }

    /**
     * Getter and setter for _systemUpdateOrder list.
     */
    public String[] getOrderedSystemTypes() { return _systemUpdateOrder; }
    public void setOrderedSystemTypes(String[] types) { _systemUpdateOrder = types; }

    /**
     * Get an available entity id from the entity manager.
     *
     * @return  an available entity id.
     */
    public int acquireEntityId() {
        return _em.acquireEntityId();
    }

    /**
     * Add an entity and its associated components and systems to the managers.
     *
     * @param e  the entity to add.
     * @param cl  a list of components to add.
     * @param sl  a list of systems to add.
     */
    public void addEntity(IEntity e, ArrayList<IComponent> cl, ArrayList<ISystem> sl) {
        _em.addEntity(e);
        for (IComponent c : cl) {
            _cm.addComponent(c);
        }
        for (ISystem s : sl) {
            _sm.addSystem(s);
        }
    }

    /**
     * Build an entity and its associated components and systems, and add them to their respective managers.
     *
     * @param builder  a builder which generates the entity, components, and systems.
     */
    public void addEntity(AbstractEntityBuilder builder) {
        builder.startBuild();
        _em.addEntity(builder.buildEntity());
        for (IComponent c : builder.buildComponentList()) {
            _cm.addComponent(c);
        }
        for (ISystem s : builder.buildSystemList()) {
            _sm.addSystem(s);
        }
        builder.finishBuild();
    }

    /**
     * Remove an entity, and all of its components and systems.
     *
     * @param id  the id of the entity to remove.
     * @return  true if successfully removed.
     */
    public boolean removeEntity(int id) {
        boolean removed = _em.removeEntity(id);
        _cm.removeComponents(id);
        _sm.removeSystems(id);
        return removed;
    }

    /**
     * flag an entity for removal at the end of the current update cycle.
     * @param id  the id of the entity to remove.
     */
    public void flagEntityForRemoval(int id) {
        _flaggedForRemoval.add(id);
    }

    /**
     * Called each tick. This calls the update method for each syst_em in the order defined,
     * then dispatches all events created during this tick cycle.
     */
    public void update() {
        for (String sType : _systemUpdateOrder) {
            Set<Map.Entry<Integer, ISystem>> systems = _sm.getSystemEntries(sType);
            if(null != systems) {
                for (Map.Entry<Integer, ISystem> e : systems) {
                    e.getValue().update();
                }
            }
        }
        _evm.dispatchEvents();
        removeFlagged();
    }
}