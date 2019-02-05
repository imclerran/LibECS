package LibECS;

import LibECS.Interfaces.IComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class ComponentManager {

    private static ComponentManager _cm;
    private HashMap<Integer, ArrayList<IComponent>> _components;
    private HashMap<String, ArrayList<IComponent>> _componentPools;

    /**
     * A private constructor for the singleton pattern.
     */
    private ComponentManager() {
        _components = new HashMap<Integer, ArrayList<IComponent>>();
        _componentPools = new HashMap<String, ArrayList<IComponent>>();
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
     * Get a list of all components belonging to a given entity.
     *
     * @param id  the entity id whose components should be returned.
     * @return  a list of the matching components.
     */
    public ArrayList<IComponent> getComponents(int id) {
        return _components.get(id);
    }
    public ArrayList<IComponent> getComponents(String type) {
        return _componentPools.get(type);
    }

    /**
     * Get a list of all components of a specific type.
     *
     * @param type  the type of component to be retrieved.
     * @return  a list of the matching components.
     */
    public IComponent addComponent(IComponent c) {
        int id = c.getId();
        String type = c.getType();

        if(_components.containsKey(id)) {
            _components.get(id).add(c);
        }
        else {
            _components.put(id, new ArrayList<IComponent>());
            _components.get(id).add(c);
        }
        if(_componentPools.containsKey(type)) {
            _componentPools.get(type).add(c);
        }
        else {
            _componentPools.put(type, new ArrayList<IComponent>());
            _componentPools.get(type).add(c);

        }
        return c;
    }

    /**
     * remove all components with a matching entity id.
     *
     * @param id  the entity id whose components should be removed.
     */
    public boolean removeComponents(int id) {
        ArrayList<IComponent> flaggedForRemoval = _components.remove(id);
        if(null == flaggedForRemoval) {
            return false;
        }
        for (IComponent c : flaggedForRemoval) {
            _componentPools.get(c.getType()).remove(c);
        }
        return true;
    }

    /**
     * Remove a given component.
     *
     * @param c  a component to remove.
     * @return  returns true if the component was successfully removed.
     */
    public boolean removeComponent(IComponent c) {
        if(_components.containsKey(c.getId())) {
            _components.get(c.getId()).remove(c);
        }
        if(_componentPools.containsKey(c.getType())) {
            return _componentPools.get(c.getType()).remove(c);
        }
        return false;
    }
}