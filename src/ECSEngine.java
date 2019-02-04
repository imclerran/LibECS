public class ECSEngine {
    private ComponentManager cm;
    private EntityManager em;
    private EventManager evm;
    private SystemManager sm;

    public ECSEngine() {
        cm = ComponentManager.getInstance();
        em = EntityManager.getInstance();
        evm = EventManager.getInstance();
        sm = SystemManager.getInstance();
    }

    public getComponentManager() { return cm; }
    public getEntityManager() { return em; }
    public getEventManager() { return evm; }
    public getSystemManager() { return sm; }

    // TODO: generic implementation of general ECS usecase?
    public void update() {}
}