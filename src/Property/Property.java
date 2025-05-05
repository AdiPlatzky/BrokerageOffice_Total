package Property;

import java.util.List;

/**
 * Abstract class representing any real estate property in the system.
 * This can be either a simple property or a composite property containing sub-properties.
 * Any class that extends this abstract class must implement all methods defined here.
 *
 * The design follows the Composite pattern, allowing uniform treatment of both simple
 * properties and property groups.
 *
 */
public abstract class Property
{
    /** Unique identifier for the property */
    protected int id;

    /** Current market status of the property (For Sale or Sold) */
    protected Status status;

    /** Address of the property */
    protected Address address;

    /** Total price of the property */
    protected double price;

    /** Size of the property in square meters */
    protected double sqm;

    /** Price per square meter for the property */
    protected double pricePerSqm;

    /**
     * Constructs a new property with the specified attributes.
     *
     * @param id Unique identifier for the property
     * @param address Address of the property
     * @param sqm Size of the property in square meters
     * @param pricePerSqm Price per square meter
     * @param status Current market status
     * @throws IllegalArgumentException If address is null, sqm is negative, or pricePerSqm is negative
     */
    public Property(int id, Address address, double sqm, double pricePerSqm, Status status)
    {
        if (address == null)
        {
            throw new IllegalArgumentException("Address cannot be null.");
        }
        if (sqm < 0){
            throw new IllegalArgumentException("Square meters must be positive");
        }
        if (pricePerSqm < 0){
            throw new IllegalArgumentException("Price must be positive.");
        }

        this.id = id;
        this.address = address;
        this.pricePerSqm = pricePerSqm;
        this.sqm = sqm;
        this.status = status;
        this.price = calculatePrice();
    }

    /**
     * Gets the unique identifier of this property.
     *
     * @return The property's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the address of this property.
     *
     * @return The property's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets the current market status of this property.
     *
     * @return The property's status (For Sale or Sold)
     */
    public Status getStatus(){
        return status;
    }

    /**
     * Gets the price per square meter of this property.
     *
     * @return The price per square meter
     */
    public double getPricePerSqm(){
        return pricePerSqm;
    }

    /**
     * Sets a new address for this property.
     *
     * @param newAddress The new address
     */
    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }

    /**
     * Sets a new market status for this property.
     *
     * @param newStatus The new status
     */
    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    /**
     * Sets new sub-properties for this property.
     * This method is intended to be overridden by composite properties.
     *
     * @param newSubProperties The new list of sub-properties
     * @throws UnsupportedOperationException If called on a non-composite property
     */
    public void setSubProperties(List<Property> newSubProperties){
        throw new UnsupportedOperationException("This operation needs to be overridden in a composite property.");
    }

    /**
     * Calculates the total price of this property based on its size and price per square meter.
     *
     * @return The calculated total price
     */
    protected double calculatePrice(){
        return sqm * pricePerSqm;
    }

    /**
     * Returns a string representation of this property.
     *
     * @return A string containing the property's ID, address, size, total price, and status
     */
    @Override
    public String toString(){
        return "Property " + id + " at " + address + ", " + getSqm() + " sqm, " + getTotalPrice() + " $, Status: " + status;
    }

    /**
     * Gets the size of this property in square meters.
     *
     * @return The property's size in square meters
     */
    public abstract double getSqm();

    /**
     * Gets the total price of this property.
     *
     * @return The property's total price
     */
    public abstract double getTotalPrice();

    /**
     * Gets the list of sub-properties for this property.
     * For simple properties, this list will be empty.
     *
     * @return The list of sub-properties
     */
    public abstract List<Property> getSubProperties();

    /**
     * Sets a new price per square meter for this property.
     *
     * @param newPricePerSqm The new price per square meter
     */
    public abstract void setPricePerSqm(double newPricePerSqm);

    /**
     * Sets a new size in square meters for this property.
     *
     * @param newSqm The new size in square meters
     */
    public abstract void setSqm(double newSqm);

    /**
     * Displays detailed information about this property.
     *
     * @return A string containing property information
     */
    public abstract String displayPropertyInfo();

    /**
     * Finds a property by its address.
     *
     * @param location The address to search for
     * @return The property with the specified address, or null if not found
     */
    public abstract Property findPropertyByAddress(String location);

    /**
     * Adds a sub-property to this property.
     *
     * @param property The sub-property to add
     * @throws UnsupportedOperationException If this is a simple property
     */
    public abstract void add(Property property);

    /**
     * Removes a sub-property from this property.
     *
     * @param property The sub-property to remove
     * @throws UnsupportedOperationException If this is a simple property
     */
    public abstract void remove(Property property);
}