public interface ISystem {
    private int _entityId;
    private String _systemType;

    public getId() { return _entityId; }
    public getType() { return _systemType; }
}