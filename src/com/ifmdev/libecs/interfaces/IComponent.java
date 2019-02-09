package com.ifmdev.libecs.interfaces;

/**
 * The interface to be implemented by all concrete Components.
 * <p>
 * Components are used to store information about entities.
 */
public interface IComponent {
    public int getId();
    public String getType();
}