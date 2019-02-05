package LibECS.Interfaces;

/**
 * The interface to be implemented by all concrete Components.
 * <p>
 * Components are used to store information about entities.
 */
public interface IComponent {
    int _entityId = 0;
    String _componentType = null;

    public int getId();
    public String getType();
}