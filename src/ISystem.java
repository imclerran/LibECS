/**
 * The interface to be implemented by all concrete Systems.
 * <p>
 * Systems are used to store handle the logic and behavior of entities.
 */
public interface ISystem {
    private int _entityId;
    private String _systemType;

    public getId() {}
    public getType() {}
    public update() {}
}