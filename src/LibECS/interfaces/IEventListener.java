package LibECS.interfaces;

import java.util.HashMap;

/**
 * The interface to be implemented by all concrete EventListeners.
 * <p>
 * EventListeners contain a callback to be processed when triggered by an event.</p>
 */
public interface IEventListener {
    Integer getId();
    String getType();
    boolean canHandle(Integer id, String type);
    void handleEvent(HashMap<String, String> args);
}