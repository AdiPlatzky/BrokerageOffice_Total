package Property;
import java.util.List;
import java.util.ArrayList;

/**
 * A class simulating a simple database that stores all properties in the system.
 * All properties (both simple and composite) are stored in a single list.
 * This class provides methods to add properties, remove them, print them all, or reset the database.
 *
 * The database is implemented using the Singleton pattern with static methods and a static collection,
 * ensuring there's only one instance of the property database throughout the application.
 */
public class PropertyDatabase
{
    /** The central collection storing all properties in the system */
    private static final List<Property> properties = new ArrayList<>();

    /**
     * Adds a property to the database.
     *
     * @param property The property to add
     * @throws IllegalArgumentException If the property is null
     */
    public static void addProperty(Property property){
        if(property == null){
            throw new IllegalArgumentException("Cannot add null property.");
        }
        properties.add(property);
        System.out.println("Added: " + property.getId());
    }

    /**
     * Removes a property from the database.
     *
     * @param property The property to remove
     */
    public static void removeProperty(Property property){
        properties.remove(property);
        System.out.println("Removed: " + property.getId());
    }

    /**
     * Returns a new list containing all properties in the database.
     * A new list is returned to prevent accidental modification of the original list.
     *
     * @return A copy of the list of all properties
     */
    public static List<Property> getAllProperties()
    {
        return new ArrayList<>(properties);
    }

    /**
     * Prints information about all properties in the database.
     * Calls the displayPropertyInfo method on each property.
     */
    public static void printAllProperties(){
        for (Property p : properties){
            p.displayPropertyInfo();
        }
    }

    /**
     * Clears all properties from the database.
     */
    public static void clearAll(){
        properties.clear();
        System.out.println("All properties cleared");
    }
}