package LibECS;

import LibECS.interfaces.IEntity;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private static EntityManager em;

    private int nextId;
    private HashMap<Integer, IEntity> _entities;
    private HashMap<String, HashMap<Integer, IEntity>> _entityTypePools;

    /**
     * A private constructor for the singleton pattern.
     */
    private EntityManager() {
        nextId = 0;
        _entities = new HashMap<Integer, IEntity>();
        _entityTypePools = new HashMap<String, HashMap<Integer, IEntity>>();
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
    public HashMap<Integer, IEntity> getEntities(String type) {
        return _entityTypePools.get(type);
    }

    /**
     * Get an available entity id to be used for new entity creation.
     *
     * @return  an available entity id.
     */
    public int acquireEntityId() {
        return nextId++;
    }

    /**
     * Add a new entity to the entity pool.
     *
     * @param e  the entity to be added to the pool.
     * @return  the added entity.
     */
    public IEntity addEntity(IEntity e) {
        _entities.put(e.getId(), e);
        String type = e.getType();
        int id = e.getId();
        if(_entityTypePools.containsKey(type)) {
            _entityTypePools.get(type).put(id, e);
        }
        else {
            _entityTypePools.put(type, new HashMap<Integer, IEntity>());
            _entityTypePools.get(e.getType()).put(id, e);
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
        HashMap<Integer, IEntity> flaggedForRemoval = _entityTypePools.remove(type);
        if(null != flaggedForRemoval) {
            for (Map.Entry<Integer, IEntity> e : flaggedForRemoval.entrySet()) {
                _entities.remove(e.getValue().getId());
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
        if(_entities.containsKey(id)) {
            IEntity e = _entities.remove(id);
            _entityTypePools.get(e.getType()).remove(id);
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
        if(_entities.containsKey(e.getId())) {
            _entityTypePools.get(e.getType()).remove(e);
            _entities.remove(e.getId());
            return true;
        }
        return false;
    }
}