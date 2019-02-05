/**
 * The interface to be implemented by all concrete Entities.
 * <p>
 * Entities represent any in game object. They are composed of Components and Systems.
 */
public interface IEntity {
    private int _id;
    private String _type;

    public int getId() {}
    public String getType() {}
}