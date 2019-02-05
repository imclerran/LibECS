package LibECS.Interfaces;

import java.util.HashMap;

/**
 * The interface to be implemented by all concrete Events.
 * <p>
 * Events are used to trigger a callback.
 */
public interface IEvent {

    int _entityId = 0;
    String _eventType = null;
    HashMap<String, String> _args = null;

    int getId();
    String getType();
    HashMap<String, String> getArgs();
}