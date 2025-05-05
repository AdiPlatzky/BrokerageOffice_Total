package test;

import Property.Property;
import SystemPermissions.PropertyFileReader;

import java.util.List;

/**
 * Test class demonstrating how to read properties from a CSV file.
 * This class serves as a simple example of using the PropertyFileReader
 * to load property data and display it.
 */
public class TestPropertyRead
{
    /**
     * Main method that demonstrates reading properties from a CSV file.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        // Path to the CSV file
        String path = "src/Data/property.csv";

        // Read properties from the CSV file
        List<Property> properties = PropertyFileReader.readPropertiesFromCSV();

        // Display all properties
        System.out.println("==== Properties loaded from file ====");
        for (Property property : properties){
            System.out.println(property);
        }
    }
}