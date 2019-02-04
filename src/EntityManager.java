public class EntityManager {
    private static EntityManager em;

    private int nextId;
    private HashMap<int, IEntity> entities;
    private HashMap<String, ArrayList<IEntity>> entityPools;

    private EntityManager() {
        nextId = 0;
        entities = new HashMap<Integer, IEntity>();
        entityPools = new HashMap<String, ArrayList<IEntity>>();
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

    public List<IEntity> getEntities(String type) {
        return entityPools.get(type);
    }

    public int aquireEntityId() {
        return nextId++;
    }

    public int addEntity(IEntity e) {
        if(entityPools.containsKey(e.getType())) {
            entities.put(e.getId(), e);
            entityPools.get(e.getType()).add(e);
        }
        else {
            entities.put(e.getId(), e);
            entityPools.put(e.getType(), new ArrayList<IEntity>());
            entityPools.get(e.getType()).add(e);
        }
        return id;
    }

    public boolean removeEntity(IEntity e) {
        if(entities.containsKey(e.getId())) {
            entityPools.get(e.getType().remove(e);
            entities.remove(e.getId());
            return true;
        }
        return false;
    }
}