public interface IEvent {

    private Integer _entityId;
    private String _eventType;
    private HashMap<String, String> _args;

    public int getId() {}
    public String getType() {}
    public HashMap<String, String> getArgs() {}
}