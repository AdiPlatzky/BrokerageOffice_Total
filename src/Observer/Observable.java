package Observer;

import Property.Property;

/**
 * Interface representing the Observable component in the Observer design pattern.
 * Objects implementing this interface (typically Sellers) can be observed by
 * other objects (typically Brokers) that implement the Observer interface.
 *
 * This interface provides methods for registering, removing, and notifying
 * observers when a property is deleted, allowing for a decoupled communication
 * between the Observable and its Observers.
 */
public interface Observable
{
    /**
     * Registers an observer to receive notifications from this observable.
     *
     * @param observer The observer to register
     */
    void registerObserver(Observer observer);

    /**
     * Removes an observer from the notification list.
     *
     * @param observer The observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers that a property has been deleted.
     *
     * @param property The deleted property
     */
    void notifyObserversOnDelete(Property property);
}