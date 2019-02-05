package LibECS;

import LibECS.Interfaces.ISystem;

import java.util.ArrayList;

public class ECSEngine {
    private ComponentManager _cm;
    private EntityManager _em;
    private EventManager _evm;
    private SystemManager _sm;

    private ArrayList<String> _orderedSystemTypes;

    /**
     * Engine constructor.
     *
     * @param orderedSystemTypes  a list of system types in the order in which they should be processed.
     */
    public ECSEngine(ArrayList<String> orderedSystemTypes) {
        _orderedSystemTypes = orderedSystemTypes;
        _cm = ComponentManager.getInstance();
        _em = EntityManager.getInstance();
        _evm = EventManager.getInstance();
        _sm = SystemManager.getInstance();
    }

    /**
     * Getters for manager classes.
     */
    public ComponentManager getComponentManager() { return _cm; }
    public EntityManager getEntityManager() { return _em; }
    public EventManager getEventManager() { return _evm; }
    public SystemManager getSystemManager() { return _sm; }

    /**
     * Getter and setter for _orderedSystemTypes list.
     */
    public ArrayList<String> getOrderedSystemTypes() { return _orderedSystemTypes; }
    public void setOrderedSystemTypes(ArrayList<String> types) { _orderedSystemTypes = types; }

    /**
     * Called each tick. This calls the update method for each syst_em in the order defined,
     * then dispatches all events created during this tick cycle.
     */
    public void update() {
        for (String sType : _orderedSystemTypes) {
            ArrayList<ISystem> systems = _sm.getSystems(sType);
            for (ISystem s : systems) {
                s.update();
            }
        }
        _evm.dispatchEvents();
    }
}