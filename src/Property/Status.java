package Property;

/**
 * Enum representing the status of a property in the real estate system.
 * Each property can have a status such as "For Sale" or "Sold".
 * Each enum value has an associated display text for better readability in output.
 *
 * This enum is used throughout the system to track and filter properties based on their
 * current market status.
 */
public enum Status
{
    /**
     * Indicates that a property is currently available for sale
     */
    FOR_SALE("For sale"),

    /**
     * Indicates that a property has been sold and is no longer available
     */
    SOLD("Sold");

    /** Display text for user-friendly representation */
    private final String displayText;

    /**
     * Constructs a Status enum with the specified display text.
     *
     * @param displayText The user-friendly display text for this status
     */
    Status(String displayText){
        this.displayText = displayText;
    }

    /**
     * Returns the display text representation of this status.
     * This overrides the default toString() method to provide a more user-friendly
     * representation of the status.
     *
     * @return The display text for this status
     */
    @Override
    public String toString(){
        return displayText;
    }
}