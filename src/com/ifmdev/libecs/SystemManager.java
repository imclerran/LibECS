package com.ifmdev.libecs;

import com.ifmdev.libecs.interfaces.ISystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SystemManager {
    private static SystemManager _sm;

    private EventManager _evm;
    private HashMap<Integer, HashMap<String, ISystem>> _systemIdPools;
    private HashMap<String, HashMap<Integer, ISystem>> _systemTypePools;

    /**
     * A private constructor for the singleton pattern.
     */
    private SystemManager() {
        _evm = EventManager.getInstance();
        _systemIdPools = new HashMap<Integer, HashMap<String, ISystem>>();
        _systemTypePools = new HashMap<String, HashMap<Integer, ISystem>>();
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
    public HashMap<String, ISystem> getSystems(int id) {
        return _systemIdPools.get(id);
    }

    /**
     * Get all systems with a matching system type.
     *
     * @param type the system type to match.
     * @return  a list of matching systems.
     */
    public HashMap<Integer, ISystem> getSystems(String type) {
        return _systemTypePools.get(type);
    }

    public Set<Map.Entry<Integer, ISystem>> getSystemEntries(String type) {
        HashMap<Integer, ISystem> pool = _systemTypePools.get(type);
        if(null != pool) {
            return pool.entrySet();
        }
        return null;
    }

    /**
     * Get the system with the specified id and type.
     * e
     * @param id  the id of the system to get.
     * @param type  the type of the system to get.
     * @return  the system requested.
     */
    public ISystem getSystem(int id, String type) {
        return _systemIdPools.get(id).get(type);
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

        if(_systemIdPools.containsKey(id)) {
            _systemIdPools.get(id).put(type, s);
        }
        else {
            _systemIdPools.put(id, new HashMap<String, ISystem>());
            _systemIdPools.get(id).put(type, s);
        }
        if(_systemTypePools.containsKey(type)) {
            _systemTypePools.get(type).put(id, s);
        }
        else {
            _systemTypePools.put(type, new HashMap<Integer, ISystem>());
            _systemTypePools.get(type).put(id, s);

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
        HashMap<String, ISystem> flaggedForRemoval = _systemIdPools.remove(id);
        if(null == flaggedForRemoval) {
            return false;
        }
        for (Map.Entry<String, ISystem> e : flaggedForRemoval.entrySet()) {
            _systemTypePools.get(e.getValue().getType()).remove(id);
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
        int id = s.getId();
        String type = s.getType();
        if(_systemIdPools.containsKey(id)) {
            _systemIdPools.get(id).remove(type);
        }
        if(_systemTypePools.containsKey(type)) {
            _systemTypePools.get(type).remove(id);
            return true;
        }
        return false;
    }
}