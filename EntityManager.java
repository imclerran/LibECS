public class EntityManager {
    private static EntityManager em;

    private int nextId;
    private HashMap<int, IEntity> entities;
    private HashMap<String, ArrayList<IEntity>> entityPools;
    private ComponentManager cm;

    private EntityManager() {
        nextId = 0;
        entities = new HashMap<Integer, IEntity>();
        entityPools = new HashMap<String, ArrayList<IEntity>>();
        cm = ComponentManager.getInstance();
    }

    /**
     * singleton getter
     */
    public static EntityManager getInstance() {
        if(null == em) {
            em = new EntityManager();
        }
        return em;
    }

    public List<IEntity> getEntityPool(String type) {
        return entityPools.get(type);
    }

    public int aquireEntityId() {
        return nextId++;
    }

    public int addEntity(IEntity e) {
        if(entityPools.containsKey(e.type)) {
            entities.put(e.id, e);
            entityPools.get(type).add(e);
        }
        else {
            entities.put(e.id, e);
            entityPools.put(e.type, new ArrayList<IEntity>());
            entityPools.get(type).add(e);
        }
        return id;
    }

    public boolean removeEntity(IEntity e) {
        if(entities.containsKey(e.id)) {
            entityPools.get(e.type).remove(e);
            entities.remove(e.id);
            return true;
        }
        return false;
    }
}