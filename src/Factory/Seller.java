package Factory;

import Observer.*;
import Property.Property;
import Property.PropertyUpdateRequest;
import SystemPermissions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class representing a property seller in the real estate system.
 * Sellers have permissions to upload properties and manage them.
 * They can view properties, delete their own properties,
 * and send notifications to brokers after deletion.
 *
 * This class implements the Observer pattern as an Observable,
 * allowing brokers to observe changes (specifically, property deletions).
 */
public class Seller extends User implements Viewable, Deletable, Observable
{
    /** List of observers (typically brokers) watching this seller's actions */
    private List<Observer> observers;

    /**
     * Constructs a new seller with the specified attributes.
     *
     * @param userName Username for this seller
     * @param password Password for authentication
     * @param id Unique identifier
     *
     * Sends the data to the parent User class with the SELLER role,
     * assigns appropriate permissions for a seller:
     * viewing, deletion, and broker notification.
     */
    public Seller(String userName, String password, int id) {
        super(userName, password, id, Set.of(UserRole.SELLER));
        this.permissions = Set.of(Permission.VIEW_PROPERTY, Permission.DELETE_PROPERTY, Permission.NOTIFY_BROKER);
        this.observers = new ArrayList<>();
    }

    /**
     * Displays information about this seller.
     * Overrides the abstract method from the User class.
     */
    @Override
    public void describe() {
        System.out.println("Seller: " + getUserName() + " ID: " + id);
    }

    /**
     * Retrieves the list of all properties from the CSV file.
     * Implements the Viewable interface method.
     *
     * @return A list of all properties in the system
     */
    @Override
    public List<Property> viewProperties(){
        return PropertyFileReader.readPropertiesFromCSV();
    }

    /**
     * Deletes a property from the list and notifies observers (brokers).
     * Implements the Deletable interface method.
     *
     * @param properties The list of properties
     * @param property The property to delete
     * @throws IllegalArgumentException If the property is not found in the list
     */
    @Override
    public void deleteProperty(List<Property> properties, Property property)
    {
        if (properties.contains(property)){
            properties.remove(property);
            System.out.println("Seller deleted property successfully: " +property.getAddress());
            notifyObserversOnDelete(property);
        }
        else {
            throw new IllegalArgumentException("Property nt found in the list.");
        }
    }

    /**
     * Registers an observer (typically a broker) to be notified of property deletions.
     * Implements the Observable interface method.
     *
     * @param observer The observer to register
     */
    @Override
    public void registerObserver(Observer observer){
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     * Implements the Observable interface method.
     *
     * @param observer The observer to remove
     */
    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers that a property has been deleted.
     * Implements the Observable interface method.
     *
     * @param property The deleted property
     */
    @Override
    public void notifyObserversOnDelete(Property property)
    {
        for (Observer observer : observers){
            observer.updateOnDelete(property);
        }
    }
}