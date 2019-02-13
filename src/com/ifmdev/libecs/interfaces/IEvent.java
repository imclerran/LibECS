package com.ifmdev.libecs.interfaces;

import java.util.HashMap;

/**
 * The interface to be implemented by all concrete Events.
 * <p>
 * Events are used to trigger a callback.
 */
public interface IEvent {
    int getId();
    String getType();
    HashMap<String, String> getArgs();
}