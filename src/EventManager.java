package LibECS;

import LibECS.Interfaces.IEvent;
import LibECS.Interfaces.IEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private static EventManager _evm;

    private ArrayList<IEvent> _events;
    private HashMap<Integer, ArrayList<IEventListener>> _idEventListeners;
    private HashMap<String, ArrayList<IEventListener>> _typeEventListeners;

    /**
     * A private constructor for the singleton pattern.
     */
    private EventManager() {
        _events = new ArrayList<IEvent>();
        _idEventListeners = new HashMap<Integer, ArrayList<IEventListener>>();
        _typeEventListeners = new HashMap<String, ArrayList<IEventListener>>();
    }

    /**
     * Singleton getter: Creates the EventManager if none exists, then returns it.
     *
     * @return  the singleton EventManager.
     */
    public static EventManager getInstance() {
        if(null == _evm) {
            _evm = new EventManager();
        }
        return _evm;
    }

    /**
     * Add an event.
     *
     * @param e  an event to add.
     * @return   the added event.
     */
    public IEvent addAddEvent(IEvent e) {
        _events.add(e);
        return e;
    }

    /**
     * Add an event listener.
     *
     * @param el  an event listener to add.
     * @return    the added event listener.
     */
    public IEventListener addEventListener(IEventListener el) {
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
        return el;
    }

    /**
     * dispatch all current events to all listeners that can handle them,
     * then clears the events list.
     */
    public void dispatchEvents() {
        for (IEvent e : _events) {
            Integer id = e.getId();
            String type = e.getType();

            if(null != id) {
                ArrayList<IEventListener> elList = _idEventListeners.get(id);
                for (IEventListener el : elList) {
                    if(el.canHandle(id, type)) {
                        el.handleEvent(e.getArgs());
                    }
                }
            }
            else if(null != type) {
                ArrayList<IEventListener> elList = _typeEventListeners.get(type);
                for (IEventListener el : elList) {
                    if(el.canHandle(id, type)) {
                        el.handleEvent(e.getArgs());
                    }
                }
            }
        }
        _events.clear();
    }

    /**
     * Remove all event listeners with a matching id.
     *
     * @param id  the entity id to be matched for listener removal.
     * @return  true if at least one event listener was removed.
     */
    public boolean removeEventListeners(int id) {
        ArrayList<IEvent> flaggedForRemoval = new ArrayList<IEvent>();
        for (IEvent e : _events) {
            if(e.getId() == id) {
                flaggedForRemoval.add(e);
            }
        }
        for (IEvent e : flaggedForRemoval) {
            _events.remove(e);
        }
        return flaggedForRemoval.size() > 0;
    }

    /**
     * Remove all event listeners with a matching type.
     *
     * @param type  the event type to be matched for listener removal.
     * @return  true if at least one event listener was removed.
     */
    public boolean removeEventListeners(String type) {
        ArrayList<IEvent> flaggedForRemoval = new ArrayList<IEvent>();
        for (IEvent e : _events) {
            if(e.getType() == type) {
                flaggedForRemoval.add(e);
            }
        }
        for (IEvent e : flaggedForRemoval) {
            _events.remove(e);
        }
        return flaggedForRemoval.size() > 0;
    }

    /**
     * Remove all event listeners with a matching id and type.
     *
     * @param id  the entity id to be matched for listener removal.
     * @param type  the event type to be matched for listener removal.
     * @return  true if at least one event listener was removed.
     */
    public boolean removeEventListener(int id, String type) {
        ArrayList<IEvent> flaggedForRemoval = new ArrayList<IEvent>();
        for (IEvent e : _events) {
            if(e.getId() == id && e.getType() == type) {
                flaggedForRemoval.add(e);
            }
        }
        for (IEvent e : flaggedForRemoval) {
            _events.remove(e);
        }
        return flaggedForRemoval.size() > 0;
    }
}