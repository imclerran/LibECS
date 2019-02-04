public class ComponentManager {

    private static ComponentManager cm;
    private HashMap<int, IComponent> components;
    private HashMap<String, List<IComponent>> componentPools;

    private ComponentManager() {
        components = new HashMap<Integer, IComponent>();
        componentPools = new HashMap<String, List<IComponent>>();
    }

    /**
     * singleton getter
     */
    public static ComponentManager getInstance() {
        if(null == cm) {
            cm = new ComponentManager();
        }
        return cm;
    }
}