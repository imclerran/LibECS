package LibECS.Interfaces;

/**
 * The interface to be implemented by all concrete Entities.
 * <p>
 * Entities represent any in game object. They are composed of Components and Systems.
 */
public interface IEntity {
    int _id = 0;
    String _type = null;

    public int getId();
    public String getType();
}