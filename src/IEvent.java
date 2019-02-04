public interface IEvent {

    private int _entityId;
    private String _eventType;
    private HashMap<String, String> _args;

    public int getId() { return _entityId; }
    public String getType() { return _eventType; }
    public HashMap<String, String> getArgs() { return _args; }
}