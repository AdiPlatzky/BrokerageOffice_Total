package SystemPermissions;

/**
 * Enum representing the different types of permissions that can be assigned to users
 * in the real estate system.
 *
 * These permissions control what actions users are allowed to perform on properties,
 * and are assigned to users based on their roles (Buyer, Seller, Broker).
 */
public enum Permission
{
    /**
     * Permission to view property details
     */
    VIEW_PROPERTY,

    /**
     * Permission to delete properties from the system
     */
    DELETE_PROPERTY,

    /**
     * Permission to edit property details
     */
    EDIT_PROPERTY,

    /**
     * Permission to notify brokers about property changes
     */
    NOTIFY_BROKER
}