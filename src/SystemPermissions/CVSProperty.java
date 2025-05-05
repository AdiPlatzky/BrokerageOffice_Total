package SystemPermissions;

/**
 * Class representing a simplified property model used when reading/writing
 * properties from/to CSV files.
 *
 * This class contains only the essential property attributes (area, price, status, address)
 * and is used as an intermediate representation during file I/O operations.
 */
public class CVSProperty
{
    /** Size of the property in square meters */
    private double area;

    /** Total price of the property */
    private double price;

    /** Status of the property (e.g., "FOR_SALE", "SOLD") */
    private String status;

    /** Address of the property as a string */
    private String address;

    /**
     * Constructs a new CVSProperty with the specified attributes.
     *
     * @param area Size of the property in square meters
     * @param price Total price of the property
     * @param status Status of the property
     * @param address Address of the property
     */
    public CVSProperty(double area, double price, String status, String address){
        this.area = area;
        this.price = price;
        this.status = status;
        this.address = address;
    }

    /**
     * Gets the size of the property in square meters.
     *
     * @return The property's area
     */
    public double getArea() {
        return area;
    }

    /**
     * Gets the total price of the property.
     *
     * @return The property's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the status of the property.
     *
     * @return The property's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the address of the property.
     *
     * @return The property's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns a string representation of this property.
     *
     * @return A string containing the property's address, area, price, and status
     */
    @Override
    public String toString() {
        return "Property: " + getAddress() + " | " + getArea() + " sqm | " + getPrice() + " $ | " + getStatus();
    }
}