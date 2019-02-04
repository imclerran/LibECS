public interface IEventListener {
    private Integer _listenToId;
    private String _eventType;
    private void _eventHanlder(HashMap<String, String> args);

    public int getId() {}
    public String getType() {}
    public boolean canHandle(Integer id, String type) {}
    public void handleEvent(HashMap<String, String> args) {
    }
}