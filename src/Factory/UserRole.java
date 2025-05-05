package Factory;
/**
 * Enum representing the types of user roles in the system.
 * Each role defines the "essence" of the user: whether they are a buyer, seller, or broker.
 * Roles are used to determine user permissions and functionality in the system.
 *
 * This enum is used as part of the Factory Method pattern implementation,
 * where the role determines which concrete User subclass will be instantiated.
 */
public enum UserRole {
    /**
     * Buyer role - users who can view properties
     */
    BUYER("Buyer"),

    /**
     * Seller role - users who can publish and delete properties
     */
    SELLER("Seller"),

    /**
     * Broker role - intermediaries between buyers and sellers
     */
    BROKER("Broker");

    /** Display text for user-friendly representation */
    private final String displayText;

    /**
     * Constructs a UserRole enum with the specified display text.
     *
     * @param displayText The user-friendly display text for this role
     */
    UserRole(String displayText){
        this.displayText = displayText;
    }

    /**
     * Returns the display text representation of this role.
     * This overrides the default toString() method to provide a more user-friendly
     * representation of the role.
     *
     * @return The display text for this role
     */
    @Override
    public String toString(){
        return displayText;
    }
}