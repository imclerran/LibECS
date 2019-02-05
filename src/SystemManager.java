package LibECS;

import LibECS.Interfaces.ISystem;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemManager {
    private static SystemManager _sm;

    private EventManager _evm;
    private HashMap<Integer, ArrayList<ISystem>> _systems;
    private HashMap<String, ArrayList<ISystem>> _systemPools;

    /**
     * A private constructor for the singleton pattern.
     */
    private SystemManager() {
        _evm = EventManager.getInstance();
        _systems = new HashMap<Integer, ArrayList<ISystem>>();
        _systemPools = new HashMap<String, ArrayList<ISystem>>();
    }

    /**
     * Singleton getter: Creates the SystemManager if none exists, then returns it.
     *
     * @return  the singleton SystemManager.
     */
    public static SystemManager getInstance() {
        if(null == _sm) {
            _sm = new SystemManager();
        }
        return _sm;
    }

    /**
     * Get all systems with a matching entity id.
     *
     * @param id  the entity id to match.
     * @return  a list of matching systems.
     */
    public ArrayList<ISystem> getSystems(int id) {
        return _systems.get(id);
    }

    /**
     * Get all systems with a matching system type.
     *
     * @param type the system type to match.
     * @return  a list of matching systems.
     */
    public ArrayList<ISystem> getSystems(String type) {
        return _systemPools.get(type);
    }

    /**
     * Add a system to the manager.
     *
     * @param s  the system to add.
     * @return  the added system.
     */
    public ISystem addSystem(ISystem s) {
        int id = s.getId();
        String type = s.getType();

        if(_systems.containsKey(id)) {
            _systems.get(id).add(s);
        }
        else {
            _systems.put(id, new ArrayList<ISystem>());
            _systems.get(id).add(s);
        }
        if(_systemPools.containsKey(type)) {
            _systemPools.get(type).add(s);
        }
        else {
            _systemPools.put(type, new ArrayList<ISystem>());
            _systemPools.get(type).add(s);

        }
        return s;
    }

    /**
     * remove all systems with a matching entity id.
     *
     * @param id  the entity id whose systems should be removed.
     * @return  true if systems were removed.
     */
    public boolean removeSystems(int id) {
        ArrayList<ISystem> flaggedForRemoval = _systems.remove(id);
        if(null == flaggedForRemoval) {
            return false;
        }
        for (ISystem s : flaggedForRemoval) {
            _systemPools.get(s.getType()).remove(s);
        }
        return true;
    }

    /**
     * Remove a system from the manager.
     *
     * @param s  the system to remove.
     * @return  true if successfully removed.
     */
    public boolean removeSystem(ISystem s) {
        if(_systems.containsKey(s.getId())) {
            _systems.get(s.getId()).remove(s);
        }
        if(_systemPools.containsKey(s.getType())) {
            return _systemPools.get(s.getType()).remove(s);
        }
        return false;
    }
}