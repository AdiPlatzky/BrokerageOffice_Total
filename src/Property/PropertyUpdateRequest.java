package Property;
import java.util.List;

/**
 * A class representing a request to update property attributes.
 * This class follows the Data Transfer Object (DTO) pattern, encapsulating the data
 * required for updating a property.
 *
 * The class uses nullable types (Double instead of double) to indicate which
 * attributes should be updated - only non-null values will be used during the update.
 */
public class PropertyUpdateRequest
{
    /** New price per square meter (nullable) */
    private Double newPricePerSqm;

    /** New size in square meters (nullable) */
    private Double newSqm;

    /** New market status (nullable) */
    private Status newStatus;

    /** New address (nullable) */
    private Address newAddress;

    /** New sub-properties list (nullable) */
    private List<Property> newSubProperties;

    /**
     * Sets a new price per square meter for the property.
     *
     * @param newPricePerSqm The new price per square meter
     */
    public void setPricePerSqm(Double newPricePerSqm){
        this.newPricePerSqm = newPricePerSqm;
    }

    /**
     * Sets a new size in square meters for the property.
     *
     * @param newSqm The new size in square meters
     */
    public void setSqm(Double newSqm){
        this.newSqm = newSqm;
    }

    /**
     * Sets a new market status for the property.
     *
     * @param newStatus The new status
     */
    public void setStatus(Status newStatus){
        this.newStatus = newStatus;
    }

    /**
     * Sets a new address for the property.
     *
     * @param newAddress The new address
     */
    public void setAddress(Address newAddress){
        this.newAddress = newAddress;
    }

    /**
     * Sets new sub-properties for the property.
     *
     * @param newSubProperties The new list of sub-properties
     */
    public void setSubProperties(List<Property> newSubProperties){
        this.newSubProperties = newSubProperties;
    }

    /**
     * Gets the new price per square meter.
     *
     * @return The new price per square meter, or null if not set
     */
    public Double getNewPricePerSqm(){
        return newPricePerSqm;
    }

    /**
     * Gets the new size in square meters.
     *
     * @return The new size in square meters, or null if not set
     */
    public Double getNewSqm() {
        return newSqm;
    }

    /**
     * Gets the new market status.
     *
     * @return The new status, or null if not set
     */
    public Status getNewStatus() {
        return newStatus;
    }

    /**
     * Gets the new address.
     *
     * @return The new address, or null if not set
     */
    public Address getNewAddress() {
        return newAddress;
    }

    /**
     * Gets the new list of sub-properties.
     *
     * @return The new list of sub-properties, or null if not set
     */
    public List<Property> getNewSubProperties() {
        return newSubProperties;
    }
}