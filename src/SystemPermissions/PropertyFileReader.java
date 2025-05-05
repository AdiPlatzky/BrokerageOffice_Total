package SystemPermissions;
import Property.*;
import java.util.*;
import java.io.*;

/**
 * Utility class for reading properties from and writing properties to CSV files.
 * This class handles the conversion between the file representation and the
 * object model of properties, including composite properties with sub-properties.
 *
 * The class provides static methods for reading all properties from a CSV file
 * and for writing a collection of properties back to a CSV file.
 */
public class PropertyFileReader
{
    /** Path to the CSV file containing property data */
    private static final String FILE_PATH = "src/Data/property.csv";

    /**
     * Reads all properties from the CSV file.
     * This method parses the CSV file and constructs Property objects,
     * including handling composite properties with sub-properties.
     *
     * @return A list of all properties read from the file
     */
    public static List<Property> readPropertiesFromCSV()
    {
        List<Property> properties = new ArrayList<>();
        Map<String, CompositeProperty> compositeMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH)))
        {
            // Skip header line
            String line = reader.readLine();

            while ((line = reader.readLine()) != null)
            {
                try
                {
                    String[] parts = line.split(",");
                    if (parts.length < 4)
                    {
                        System.out.println("Invalid line format: " + line);
                        continue;
                    }

                    // Parse property data
                    double area = Double.parseDouble(parts[0].trim());
                    double price = Double.parseDouble(parts[1].trim());
                    Status status = Status.valueOf(parts[2].trim());
                    String addressStr = parts[3].trim();

                    Address address = new Address(addressStr);
                    double pricePerSqm = price / area;
                    int id = generateId(addressStr);

                    // Handle sub-properties (apartments within a building)
                    if (address.isSubAddress())
                    {
                        processSubProperty(properties, compositeMap, address, area, pricePerSqm, status, id);
                    }
                    else
                    {
                        // Create simple property
                        SimpleProperty simple = new SimpleProperty(id, address, area, pricePerSqm, status);
                        properties.add(simple);

                        // Create corresponding composite property if it doesn't exist yet
                        if (!compositeMap.containsKey(addressStr))
                        {
                            CompositeProperty composite = new CompositeProperty(id, address, status);
                            compositeMap.put(addressStr, composite);
                        }
                    }
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Error processing line: " + line + " - " + e.getMessage());
                }
            }

            // Filter out simple properties that are already part of a composite
            List<Property> result = new ArrayList<>();
            for (Property property : properties)
            {
                if (!(property instanceof SimpleProperty) || !isPartsOfComposite(property, properties))
                {
                    result.add(property);
                }
            }
            return result;
        }
        catch (IOException e)
        {
            System.out.println("Error reading file: " + e.getMessage());
            return properties;
        }
    }

    /**
     * Processes a sub-property (e.g., an apartment within a building).
     * This method creates a simple property for the sub-address and adds it to the
     * appropriate composite property in the hierarchy.
     *
     * @param properties The list of all properties
     * @param compositeMap Map from address strings to composite properties
     * @param address The address of the sub-property
     * @param area The area of the sub-property
     * @param pricePerSqm The price per square meter
     * @param status The status of the property
     * @param id The unique identifier
     */
    private static void processSubProperty(List<Property> properties, Map<String , CompositeProperty> compositeMap, Address address, double area, double pricePerSqm, Status status, int id){
        SimpleProperty simple = new SimpleProperty(id, address, area, pricePerSqm, status);

        Address parentAddress = address.getParentAddress();
        String parentAddressStr = parentAddress.toFileString();

        CompositeProperty parent = compositeMap.get(parentAddressStr);

        // Create parent composite if it doesn't exist yet
        if (parent == null){
            parent = new CompositeProperty(generateId(parentAddressStr), parentAddress, status);
            properties.add(parent);
            compositeMap.put(parentAddressStr, parent);

            // Process parent recursively if it's also a sub-address
            if (parentAddress.isSubAddress()){
                processSubProperty(properties, compositeMap, parentAddress, 0, 0, status, parent.getId());
            }
        }

        // Add this simple property to its parent
        parent.add(simple);
    }

    /**
     * Checks if a property is already part of a composite property.
     * Used to filter out duplicate properties.
     *
     * @param property The property to check
     * @param allProperties The list of all properties
     * @return true if the property is part of a composite, false otherwise
     */
    private static boolean isPartsOfComposite(Property property, List<Property> allProperties){
        String address = property.getAddress().toFileString();

        for (Property p : allProperties){
            if (p instanceof CompositeProperty){
                CompositeProperty composite = (CompositeProperty) p;
                for (Property sub : composite.getSubProperties()){
                    if (sub.getAddress().toFileString().equals(address)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Writes a collection of properties to the CSV file.
     * This method handles both simple and composite properties,
     * flattening the property hierarchy for storage.
     *
     * @param properties The properties to write
     */
    public static void writePropertiesToCsv(List<Property> properties)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            // Write header
            writer.write("Area,Price,Status,Address");
            writer.newLine();

            // Write each property
            for(Property property : properties)
            {
                writePropertyRecursive(property, writer);
            }
            System.out.println("Properties successfully writen to " + FILE_PATH);
        }
        catch (IOException e)
        {
            System.out.println("Error writing properties to file: " + e.getMessage());
        }
    }

    /**
     * Recursively writes a property and its sub-properties to the CSV file.
     *
     * @param property The property to write
     * @param writer The writer to use
     * @throws IOException If an I/O error occurs
     */
    private static void writePropertyRecursive(Property property, BufferedWriter writer) throws IOException
    {
        // Write this property if it's a simple property or has no sub-properties
        if (property instanceof SimpleProperty || property.getSubProperties().isEmpty()){
            writer.write(String.format("%.1f,%.1f,%s,%s",
                    property.getSqm(),
                    property.getTotalPrice(),
                    property.getStatus(),
                    property.getAddress().toFileString()));
            writer.newLine();
        }

        // Recursively write sub-properties if this is a composite property
        if (property instanceof CompositeProperty)
        {
            List<Property> subProperties = property.getSubProperties();
            for (Property sub : subProperties)
            {
                writePropertyRecursive(sub, writer);
            }
        }
    }

    /**
     * Generates a unique identifier for a property based on its address.
     *
     * @param addressStr The string representation of the address
     * @return A hash code derived from the address
     */
    private static int generateId(String addressStr)
    {
        return Math.abs(addressStr.hashCode());
    }
}