package Factory;

import Property.Property;
import Property.PropertyUpdateRequest;
import SystemPermissions.*;

import java.util.List;
import java.util.Set;

/**
 * Class representing a buyer in the real estate system.
 * Buyers have permission to view property details but cannot
 * edit or delete properties.
 *
 * This class is a concrete implementation of the User abstract class
 * as part of the Factory Method pattern.
 */
public class Buyer extends User implements Viewable
{
    /**
     * Constructs a new buyer with the specified attributes.
     *
     * @param userName Username for this buyer
     * @param password Password for authentication
     * @param id Unique identifier
     *
     * Sends the data to the parent User class,
     * defines that this user is of type BUYER,
     * and assigns only one permission: viewing property details.
     */
    public Buyer(String userName, String password, int id) {
        super(userName, password, id, Set.of(UserRole.BUYER));
        this.permissions = Set.of(Permission.VIEW_PROPERTY);
    }

    /**
     * Displays information about this buyer.
     * Overrides the abstract method from the User class.
     */
    @Override
    public void describe()
    {
        System.out.println("Buyer: " + getUserName() + " ID: " + id);
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
}