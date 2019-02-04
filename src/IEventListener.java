public interface IEventListener {
    private int _entityId;
    private String _eventType;
    private void handleEvent(HashMap<String, String> args);
}