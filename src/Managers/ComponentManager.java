public class ComponentManager {

    private static ComponentManager _cm;
    private HashMap<Integer, List<IComponent>> _components;
    private HashMap<String, List<IComponent>> _componentPools;

    private ComponentManager() {
        _components = new HashMap<Integer, List<IComponent>>();
        _componentPools = new HashMap<String, List<IComponent>>();
    }

    /**
     * singleton getter
     */
    public static ComponentManager getInstance() {
        if(null == _cm) {
            _cm = new ComponentManager();
        }
        return _cm;
    }

    public List<IComponent> getComponents(int id) {
        return _components.get(id);
    }
    public List<IComponent> getComponents(String type) {
        return _componentPools.get(type);
    }

    public int addComponent(IComponent c) {
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

    public boolean removeComponent(IComponent c) {
        if(_components.containsKey(c.getId())) {
            _components.get(c.getId().remove(c);
        }
        if(_componentPools.containsKey(c.getType())) {
            return _componentPools.get(c.getType()).remove(c);
        }
        return false;
    }
}