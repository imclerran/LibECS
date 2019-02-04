public class EntityFactory {
    private EntityManager em;
    private ComponentManager cm;

    public EntityFactory() {
        em = EntityManager.getInstance();
        cm = ComponentManager.getInstance();
    }

    public static IEntity createEntity(String type, ArrayList<String> args) {
        IEntity e = null;
        switch(type) {
            case "typeA":
                e = createEnityTypeA(args);
                break;
            default:
                break;
        }
        return e;
    }

    private static IEntity createEntityTypeA(ArrayList<String> args) {
        int id = em.acquireEntityId();
        IEntity e = new ConcreteEntityTypeA(id);
        //cm.add( component1 );
        //cm.add( component2 );
        return e;
    }
}