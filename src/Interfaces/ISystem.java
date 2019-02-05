package LibECS.Interfaces;

/**
 * The interface to be implemented by all concrete Systems.
 * <p>
 * Systems are used to store handle the logic and behavior of entities.
 */
public interface ISystem {
    int _entityId = 0;
    String _systemType = null;

    int getId();
    String getType();
    void update();
}