package Observer;

import Property.Property;

/**
 * Interface representing the Observer component in the Observer design pattern.
 * Objects implementing this interface (typically Brokers) can observe and
 * receive notifications from Observable objects (typically Sellers) when
 * properties are deleted.
 *
 * This interface provides a method for receiving property deletion notifications,
 * allowing for a decoupled communication between the Observable and its Observers.
 */
public interface Observer
{
    /**
     * Called when a property is deleted by an Observable object.
     * Allows the Observer to react to the deletion event.
     *
     * @param property The deleted property
     */
    void updateOnDelete(Property property);
}