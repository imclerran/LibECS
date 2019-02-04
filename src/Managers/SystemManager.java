public class SystemManager {
    private static SystemManager _sm;

    private EventManager _evm;
    private HashMap<Integer, List<ISystem>> _systems;
    private HashMap<String, List<ISystem>> _systemPools;

    private SystemManager() {
        _systems = new HashMap<Integer, List<ISystem>>();
        _systemPools = new HashMap<String, List<ISystem>>();
    }

    /**
     * singleton getter
     */
    public static ComponentManager getInstance() {
        if(null == _sm) {
            _sm = new ComponentManager();
        }
        return _sm;
    }

    public List<ISystem> getSystems(int id) {
        return _systems.get(id);
    }
    public List<ISystem> getSystems(String type) {
        return _systemPools.get(type);
    }

    public int addSystem(ISystem s) {
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

    public boolean removeComponent(ISystem s) {
        if(_systems.containsKey(s.getId())) {
            _systems.get(s.getId().remove(s);
        }
        if(_systemPools.containsKey(s.getType())) {
            return _systemPools.get(s.getType()).remove(s);
        }
        return false;
    }
}