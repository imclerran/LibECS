public class EventManager {
    private EventManager _evm;

    private ArrayList<IEvent> _events;
    private HashMap<Integer, ArrayList<IEventListener>> _idEventListeners;
    private HashMap<String, ArrayList<IEventListener>> _typeEventListeners;

    private EventManager() {}

    public EventManager getInstance() {
        if(null == _evm) {
            _evm = new EventManager();
        }
        return _evm;
    }

    public void addAddEvent(IEvent e) {
        _events.add(e);
    }

    public void addEventListener(IEventListener el) {
        Integer id = el.getId();
        String type = el.getType();
        if(null != id) {
            if(_idEventListeners.containsKey(id)) {
                _idEventListeners.get(id).add(el);
            }
            else {
                _idEventListeners.put(id, new ArrayList<IEventListener>());
                _idEventListeners.get(id).add(el);
            }
        }
        else if(null != type) {
            if(_typeEventListeners.containsKey(type)) {
                _typeEventListeners.get(type).add(el);
            }
            else {
                _typeEventListeners.put(type, new ArrayList<IEventListener>());
                _idEventListeners.get(type).add(el);
            }
        }
    }

    public void dispatchEvents() {
        for (IEvent e : _events.entrySet()) {
            Integer id = e.getId();
            String type = e.getType();

            if(null != id) {
                ArrayList<IEventListener> elList = _idEventListeners.get(id);
                for (IEventListener el : elList) {
                    if(el.canHandle(id, type)) {
                        e.handleEvent(e.getArgs());
                    }
                }
            }
            else if(null != type) {
                ArrayList<IEventListener> elList = _typeEventListeners.get(type);
                for (IEventListener el : elList) {
                    if(el.canHandle(id, type)) {
                        e.handleEvent(e.getArgs());
                    }
                }
            }
        }
        _idEventListeners.clear();
        _typeEventListeners.clear();
    }
}