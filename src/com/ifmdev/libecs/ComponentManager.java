package com.ifmdev.libecs;

import com.ifmdev.libecs.interfaces.IComponent;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager {

    private static ComponentManager _cm;
    private HashMap<Integer, HashMap<String,IComponent>> _componentIdPools;
    private HashMap<String, HashMap<Integer, IComponent>> _componentTypePools;

    /**
     * A private constructor for the singleton pattern.
     */
    private ComponentManager() {
        _componentIdPools = new HashMap<Integer, HashMap<String, IComponent>>();
        _componentTypePools = new HashMap<String, HashMap<Integer, IComponent>>();
    }

    /**
     * Singleton getter: Creates the ComponentManager if none exists, then returns it.
     *
     * @return  the singleton ComponentManager.
     */
    public static ComponentManager getInstance() {
        if(null == _cm) {
            _cm = new ComponentManager();
        }
        return _cm;
    }

    /**
     * Get a hashmap of all components belonging to a given entity.
     *
     * @param id  the entity id whose components should be returned.
     * @return  a list of the matching components.
     */
    public HashMap<String, IComponent> getComponents(int id) { return _componentIdPools.get(id); }
    public HashMap<Integer, IComponent> getComponents(String type) { return _componentTypePools.get(type); }

    /**
     * Get a component of specified id and type.
     *
     * @param id  the id of the component to be retrieved.
     * @param type  the type of the component to be retrieved.
     * @return  the requested component.
     */
    public IComponent getComponent(int id, String type) {
        return _componentIdPools.get(id).get(type);
    }

    /**
     * Add a component to the manager.
     *
     * @param c  the component to be added.
     * @return  the added component.
     */
    public IComponent addComponent(IComponent c) {
        int id = c.getId();
        String type = c.getType();


        if(_componentIdPools.containsKey(id)) {
            _componentIdPools.get(id).put(type, c);
        }
        else {
            _componentIdPools.put(id, new HashMap<String, IComponent>());
            _componentIdPools.get(id).put(type, c);
        }
        if(_componentTypePools.containsKey(type)) {
            _componentTypePools.get(type).put(id, c);
        }
        else {
            _componentTypePools.put(type, new HashMap<Integer, IComponent>());
            _componentTypePools.get(type).put(id, c);
        }
        return c;
    }

    /**
     * remove all components with a matching entity id.
     *
     * @param id  the entity id whose components should be removed.
     */
    public boolean removeComponents(int id) {
        HashMap<String, IComponent> flaggedForRemoval = _componentIdPools.remove(id);
        if(null == flaggedForRemoval) {
            return false;
        }
        for (Map.Entry<String,IComponent> e : flaggedForRemoval.entrySet()) {
            String type = e.getValue().getType();
            _componentTypePools.get(type).remove(id);
        }
        return true;
    }

    /**
     * Remove a given component.
     *
     * @param id  the id of the component to remove.
     * @param type  the type of the component to remove.
     * @return  returns true if the component was successfully removed.
     */
    public boolean removeComponent(int id, String type) {
        IComponent c1 = _componentIdPools.get(id).remove(type);
        IComponent c2 = _componentTypePools.get(type).remove(id);
        if(null != c1 || null != c2) {
            return true;
        }
        return false;
    }

    /**
     * Remove a given component.
     *
     * @param c  a component to remove.
     * @return  returns true if the component was successfully removed.
     */
    public boolean removeComponent(IComponent c) {
        int id = c.getId();
        String type = c.getType();
        if(_componentIdPools.containsKey(id)) {
            _componentIdPools.get(id).remove(type);
        }
        if(_componentTypePools.containsKey(type)) {
            _componentTypePools.get(type).remove(id);
            return true;
        }
        return false;
    }
}