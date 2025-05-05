package Factory;

/**
 * Factory class for creating users in the system.
 * This class implements the Factory Method design pattern, which allows
 * creating instances of a user according to the selected role,
 * without needing to know the specific class name in advance.
 *
 * The factory centralizes user creation logic and provides a clean,
 * encapsulated way to instantiate different user types based on the requested role.
 */
public class UserFactory
{
    /**
     * Creates and returns a new user of the specified type.
     * This is the Factory Method that instantiates the appropriate concrete User subclass
     * based on the requested user role.
     *
     * @param userName Username for the new user
     * @param password Password for authentication
     * @param id Unique identifier for the user
     * @param type The role determining which type of user to create
     * @return A new User object of the appropriate subclass
     * @throws IllegalArgumentException If an unknown user type is requested
     */
    public static User createUser(String userName, String password, int id, UserRole type){
        return switch (type)
        {
            case BUYER -> new Buyer(userName, password, id);
            case SELLER -> new Seller(userName, password, id);
            case BROKER -> new Broker(userName, password, id);
            default -> throw new IllegalArgumentException("Unknown user type: " + type);
        };
    }
}