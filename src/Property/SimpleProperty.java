package Property;

import java.util.Collections;
import java.util.List;

/**
 * Class representing a simple, undivided property in the real estate system.
 * A simple property has an address, price per square meter, size, and status (For Sale/Sold).
 * It allows calculation of the total price based on: price per square meter multiplied by size.
 *
 * Simple properties cannot contain sub-properties. Attempts to add sub-properties will throw an exception.
 * This class is part of the Composite pattern implementation in the property hierarchy.
 */
public class SimpleProperty extends Property
{
    /**
     * Constructs a new simple property with the specified attributes.
     *
     * @param id Unique identifier for the property
     * @param address Address of the property
     * @param sqm Size of the property in square meters
     * @param pricePerSqm Price per square meter
     * @param status Current market status
     */
    public SimpleProperty(int id, Address address, double sqm, double pricePerSqm, Status status) {
        super(id, address, sqm, pricePerSqm, status);
    }

    /**
     * Gets the size of this property in square meters.
     *
     * @return The property's size in square meters
     * @throws IllegalArgumentException If the square meters value is zero
     */
    @Override
    public double getSqm()
    {
        if (sqm == 0){
            throw new IllegalArgumentException("Square meters must be positive");
        }
        return sqm;
    }

    /**
     * Calculates the total price of this property based on its size and price per square meter.
     *
     * @return The calculated total price
     * @throws IllegalArgumentException If either square meters or price per square meter is zero
     */
    @Override
    public double getTotalPrice()
    {
        if (sqm == 0 || pricePerSqm == 0){
            throw new IllegalArgumentException("Square meters and price per square must be positive!");
        }
        return pricePerSqm * sqm;
    }

    /**
     * Gets the list of sub-properties for this property.
     * For simple properties, this list is always empty.
     *
     * @return An empty list
     */
    @Override
    public List<Property> getSubProperties(){
        return Collections.emptyList();
    }

    /**
     * Sets a new price per square meter for this property.
     *
     * @param newPricePerSqm The new price per square meter
     * @throws IllegalArgumentException If the new price per square meter is not positive
     */
    @Override
    public void setPricePerSqm(double newPricePerSqm)
    {
        if (newPricePerSqm <= 0){
            throw new IllegalArgumentException("Price per square must be positive");
        }
        this.pricePerSqm = newPricePerSqm;
        this.price = calculatePrice();
    }

    /**
     * Sets a new size in square meters for this property.
     *
     * @param newSqm The new size in square meters
     * @throws IllegalArgumentException If the new size is not positive
     */
    @Override
    public void setSqm(double newSqm){
        if (newSqm <= 0){
            throw new IllegalArgumentException("Square meters must be positive");
        }
        this.sqm = newSqm;
        this.price = calculatePrice();
    }

    /**
     * Displays detailed information about this property.
     *
     * @return A string containing property information
     */
    @Override
    public String displayPropertyInfo() {
        return "# simple Property: " + id + " at " + address + " | " + sqm + " sqm | " + pricePerSqm + " $ | " + "Status: " + status;
    }

    /**
     * Finds a property by its address.
     * For simple properties, this either returns this property (if addresses match) or null.
     *
     * @param address The address to search for
     * @return This property if addresses match, null otherwise
     */
    @Override
    public Property findPropertyByAddress(String address){
        if (this.address.toString().equals(address)){
            return this;
        }
        return null;
    }

    /**
     * Adding sub-properties is not supported for simple properties.
     *
     * @param new_property The property to add
     * @throws UnsupportedOperationException Always, since simple properties cannot contain sub-properties
     */
    @Override
    public void add(Property new_property){
        throw new UnsupportedOperationException("Simple property cannot contain sub-properties.");
    }

    /**
     * Removing sub-properties is not supported for simple properties.
     *
     * @param property The property to remove
     * @throws UnsupportedOperationException Always, since simple properties cannot contain sub-properties
     */
    @Override
    public void remove(Property property){
        throw new UnsupportedOperationException("Simple property cannot contain sub-properties.");
    }
}