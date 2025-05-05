package Factory;

import SystemPermissions.Editable;
import SystemPermissions.Permission;
import Observer.Observer;
import Property.Property;
import Property.Status;
import Property.PropertyUpdateRequest;
import SystemPermissions.PropertyFileReader;


import java.util.List;
import java.util.Set;

/**
 * Class representing a real estate broker in the system.
 * Brokers are authorized to view properties and edit them.
 *
 * This class implements both the Observer pattern (to be notified of property deletions by sellers)
 * and the Editable interface (allowing brokers to modify property details).
 */
public class Broker extends User implements Editable, Observer {
    /**
     * Constructs a new broker with the specified attributes.
     *
     * @param userName Username for this broker
     * @param password Password for authentication
     * @param id Unique identifier
     *
     * Sends the data to the parent User class with the BROKER role,
     * and defines the permissions allowed for a broker (property editing).
     */
    public Broker(String userName, String password, int id)
    {
        super(userName, password, id, Set.of(UserRole.BROKER));
        this.permissions = Set.of(Permission.EDIT_PROPERTY);
    }

    /**
     * Displays information about this broker.
     * Overrides the abstract method from the User class.
     */
    @Override
    public void describe()
    {
        System.out.println("Broker: " + getUserName() + " ID: " + id);
    }

    /**
     * Edits a property based on the provided update request.
     * Implements the Editable interface method.
     *
     * @param property The property to edit
     * @param updateRequest The update request containing new values
     */
    @Override
    public void editProperty(Property property, PropertyUpdateRequest updateRequest) {
        if (updateRequest.getNewPricePerSqm() != null) {
            property.setPricePerSqm(updateRequest.getNewPricePerSqm());
            System.out.println("Update price per sqm to: " + updateRequest.getNewPricePerSqm());
        }
        if (updateRequest.getNewSqm() != null) {
            property.setSqm(updateRequest.getNewSqm());
            System.out.println("Update sqm to: " + updateRequest.getNewSqm());
        }
        if (updateRequest.getNewStatus() != null) {
            property.setStatus(updateRequest.getNewStatus());
            System.out.println("Update status to: " + updateRequest.getNewStatus());
        }
        if (updateRequest.getNewAddress() != null) {
            property.setAddress(updateRequest.getNewAddress());
            System.out.println("Update address to: " + updateRequest.getNewAddress());
        }
        if (updateRequest.getNewSubProperties() != null) {
            property.setSubProperties(updateRequest.getNewSubProperties());
            System.out.println("Update sub properties to: " + updateRequest.getNewSubProperties());
        }
    }

    /**
     * Handles notifications when a property is deleted by a seller.
     * Implements the Observer interface method.
     *
     * @param property The deleted property
     */
    @Override
    public void updateOnDelete(Property property) {
        System.out.println("Broker was notified: Property delete -> " + property.getAddress());
        PropertyUpdateRequest update = new PropertyUpdateRequest();
        update.setStatus(Status.SOLD);
        editProperty(property, update);
    }
}