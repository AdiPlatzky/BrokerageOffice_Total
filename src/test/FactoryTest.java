package test;
import Factory.*;
import Property.Property;
import Property.PropertyUpdateRequest;
import SystemPermissions.Permission;
import SystemPermissions.PropertyFileReader;


import java.util.List;

/**
 * Test class demonstrating the Factory Method design pattern implementation
 * and the permission system in the real estate application.
 *
 * This class creates different types of users (buyer, seller, broker) using the
 * UserFactory, and tests their respective capabilities and permissions, including:
 * - Viewing properties
 * - Deleting properties
 * - Editing properties
 */
public class FactoryTest
{
    /**
     * Main method demonstrating the Factory Method pattern and user permissions.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        // Create different user types using the Factory Method pattern
        User buyer = UserFactory.createUser("adi", "1234", 1, UserRole.BUYER);
        User seller = UserFactory.createUser("mevaser", "5678",2,UserRole.SELLER);
        User broker = UserFactory.createUser("agent","9876",3,UserRole.BROKER);

        // Display information about each user
        buyer.describe();
        seller.describe();
        broker.describe();

        // Test viewing properties with different user types
        System.out.println("=== Buyer view properties ===");
        viewPropertiesTest(buyer);
        System.out.println("=== Seller view properties ===");
        viewPropertiesTest(seller);
        System.out.println("=== Broker view properties ===");
        viewPropertiesTest(broker);

        // Test deleting properties with different user types
        System.out.println("=== Buyer delete properties ===");
        deletePropertiesTest(buyer);
        System.out.println("=== Seller delete properties ===");
        deletePropertiesTest(seller);
        System.out.println("=== Broker delete properties ===");
        deletePropertiesTest(broker);

        // Test editing properties with different user types
        System.out.println("=== Buyer edit properties ===");
        editPropertiesTest(buyer);
        System.out.println("=== Seller edit properties ===");
        editPropertiesTest(seller);
        System.out.println("=== Broker edit properties ===");
        editPropertiesTest(broker);

        // Display user information again
        buyer.describe();
        seller.describe();
        broker.describe();

        // Display permissions for each user type
        System.out.println("Buyer can view? " + buyer.hasPermission(Permission.VIEW_PROPERTY));
        System.out.println("Seller can view? " + seller.hasPermission(Permission.VIEW_PROPERTY));
        System.out.println("Broker can view? " + broker.hasPermission(Permission.VIEW_PROPERTY));

        System.out.println("Buyer can delete? " + buyer.hasPermission(Permission.DELETE_PROPERTY));
        System.out.println("Seller can delete? " + seller.hasPermission(Permission.DELETE_PROPERTY));
        System.out.println("Broker can delete? " + buyer.hasPermission(Permission.DELETE_PROPERTY));

        System.out.println("Buyer can edit? " + buyer.hasPermission(Permission.EDIT_PROPERTY));
        System.out.println("Seller can edit? " + seller.hasPermission(Permission.EDIT_PROPERTY));
        System.out.println("Broker can edit? " + broker.hasPermission(Permission.EDIT_PROPERTY));

        System.out.println("Buyer can notify broker? " + buyer.hasPermission(Permission.NOTIFY_BROKER));
        System.out.println("Seller can notify broker? " + seller.hasPermission(Permission.NOTIFY_BROKER));
        System.out.println("Broker can notify broker? " + broker.hasPermission(Permission.NOTIFY_BROKER));
    }

    /**
     * Tests the viewProperties method of a user.
     *
     * @param user The user to test
     */
    private static void viewPropertiesTest(User user)
    {
        if (!user.getRoles().equals(UserRole.BROKER))
        {
            List<Property> properties = user.viewProperties();
            System.out.println("==== Properties loaded from file ====");
            for (Property property : properties){
                System.out.println(property);
                property.displayPropertyInfo();
            }
        }
        System.out.println();
    }

    /**
     * Tests the deleteProperty method of a user.
     * Only executes if the user has the EDIT_PROPERTY permission.
     *
     * @param user The user to test
     */
    private static void deletePropertiesTest(User user)
    {
        List<Property> properties = PropertyFileReader.readPropertiesFromCSV();
        if (!properties.isEmpty() && user.hasPermission(Permission.EDIT_PROPERTY))
        {
            Property toDelete = properties.get(0);
            user.deleteProperty(properties, toDelete);
            System.out.println("Delete property ID: " + toDelete.getId());
        }
        else {
            System.out.println("No properties to delete.");
        }
        System.out.println();
    }

    /**
     * Tests the editProperty method of a user.
     * Only executes if the user has the EDIT_PROPERTY permission.
     *
     * @param user The user to test
     */
    private static void editPropertiesTest(User user)
    {
        List<Property> properties = PropertyFileReader.readPropertiesFromCSV();
        if (!properties.isEmpty() && user.hasPermission(Permission.EDIT_PROPERTY)){
            Property toEdit = properties.get(0);
            PropertyUpdateRequest propertyUpdateRequest = new PropertyUpdateRequest();
            user.editProperty(toEdit, propertyUpdateRequest);
            System.out.println("Edit property ID: " + toEdit.getId());
        }
        else {
            System.out.println("No properties to edit.");
        }
        System.out.println();
    }
}