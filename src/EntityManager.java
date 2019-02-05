package LibECS;

import LibECS.Interfaces.IEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {
    private static EntityManager em;

    private int nextId;
    private HashMap<Integer, IEntity> entities;
    private HashMap<String, ArrayList<IEntity>> entityPools;

    /**
     * A private constructor for the singleton pattern.
     */
    private EntityManager() {
        nextId = 0;
        entities = new HashMap<Integer, IEntity>();
        entityPools = new HashMap<String, ArrayList<IEntity>>();
    }

    /**
     * Singleton getter: Creates the EntityManager if none exists, then returns it.
     *
     * @return  the singleton EntityManager.
     */
    public static EntityManager getInstance() {
        if(null == em) {
            em = new EntityManager();
        }
        return em;
    }

    /**
     * Get all entities of a specified type.
     *
     * @param type  the type of entities to be retrieved.
     * @return  a list of all entities of the specified type.
     */
    public ArrayList<IEntity> getEntities(String type) {
        return entityPools.get(type);
    }

    /**
     * Get an available entity id to be used for new entity creation.
     *
     * @return  an available entity id.
     */
    public int aquireEntityId() {
        return nextId++;
    }

    /**
     * Add a new entity to the entity pool.
     *
     * @param e  the entity to be added to the pool.
     * @return  the added entity.
     */
    public IEntity addEntity(IEntity e) {
        if(entityPools.containsKey(e.getType())) {
            entities.put(e.getId(), e);
            entityPools.get(e.getType()).add(e);
        }
        else {
            entities.put(e.getId(), e);
            entityPools.put(e.getType(), new ArrayList<IEntity>());
            entityPools.get(e.getType()).add(e);
        }
        return e;
    }

    /**
     * Remove all entities of a given type.
     *
     * @param type  the type of entity to remove.
     * @return  true if the entities were removed.
     */
    public boolean removeEntities(String type) {
        ArrayList<IEntity> flaggedForRemoval = entityPools.remove(type);
        if(null != flaggedForRemoval) {
            for (IEntity e : flaggedForRemoval) {
                entities.remove(e.getId());
            }
        }
        return flaggedForRemoval.size() > 0;
    }

    /**
     * Remove an entity of a given id.
     *
     * @param id  the id of the entity to remove.
     * @return  true if the entity was removed.
     */
    public boolean removeEntity(int id) {
        if(entities.containsKey(id)) {
            IEntity e = entities.remove(id);
            entityPools.get(e.getType()).remove(e);
            return true;
        }
        return false;
    }

    /**
     * Remove a given entity.
     *
     * @param e  the entity to remove.
     * @return  true if the the entity was removed.
     */
    public boolean removeEntity(IEntity e) {
        if(entities.containsKey(e.getId())) {
            entityPools.get(e.getType()).remove(e);
            entities.remove(e.getId());
            return true;
        }
        return false;
    }
}