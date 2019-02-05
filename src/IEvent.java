/**
 * The interface to be implemented by all concrete Events.
 * <p>
 * Events are used to trigger a callback.
 */
public interface IEvent {

    private Integer _entityId;
    private String _eventType;
    private HashMap<String, String> _args;

    public int getId() {}
    public String getType() {}
    public HashMap<String, String> getArgs() {}
}