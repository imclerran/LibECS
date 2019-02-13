package com.ifmdev.libecs.interfaces;

/**
 * The interface to be implemented by all concrete Entities.
 * <p>
 * Entities represent any in game object. They are composed of Components and Systems.
 */
public interface IEntity {
    public int getId();
    public String getType();
}