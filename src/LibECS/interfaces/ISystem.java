package LibECS.interfaces;

/**
 * The interface to be implemented by all concrete Systems.
 * <p>
 * Systems are used to store handle the logic and behavior of entities.</p>
 */
public interface ISystem {
    int getId();
    String getType();
    void update();
}