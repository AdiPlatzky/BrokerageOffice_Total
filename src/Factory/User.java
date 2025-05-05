package Factory;
import Property.*;
import SystemPermissions.Permission;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Abstract class representing a general user in the real estate system.
 * Each user contains a username, password, unique identifier, roles, and permissions.
 * This class provides basic user management functionality and defines abstract methods
 * that must be implemented by concrete user types.
 *
 * The class is part of the implementation of the Factory Method pattern,
 * where different types of users (Buyer, Seller, Broker) inherit from this class.
 */
public abstract class User {
    /** Username for login */
    protected String userName;

    /** Password for authentication (private for security) */
    private String password;

    /** Unique identifier for the user */
    protected int id;

    /** Set of roles assigned to this user (can have multiple roles) */
    protected Set<UserRole> roles;

    /** Currently active role for this user */
    private UserRole currentRole;

    /** Set of permissions granted to this user */
    protected Set<Permission> permissions;

    /**
     * Constructs a new user with the specified attributes.
     *
     * @param userName Username for this user
     * @param password Password for authentication
     * @param id Unique identifier
     * @param roles Set of roles assigned to this user
     * @throws IllegalArgumentException If username or password is null or empty,
     *                                  or if the roles set is empty
     */
    public User(String userName, String password, int id, Set<UserRole> roles)
    {
        if(userName == null || userName.isEmpty()){
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (roles.isEmpty()){
            throw new IllegalArgumentException("Each user must have at least one role.");
        }
        this.userName = userName;
        this.password = password;
        this.id = id;
        this.roles = roles;
        this.currentRole = roles.iterator().next();
    }

    /**
     * Gets the unique identifier for this user.
     *
     * @return The user's ID
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the username for this user.
     *
     * @return The username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Allows a user to change their password - only if they provide the correct current password.
     *
     * @param currentPassword The current password for verification
     * @param newPassword The new password to set
     * @throws IllegalArgumentException If the current password is incorrect
     */
    public void setPassword(String currentPassword, String newPassword)
    {
        if(!this.password.equals(currentPassword)){
            throw new IllegalArgumentException("Current password you entered is incorrect, try again");
        }
        this.password = newPassword;
    }

    /**
     * Allows a user to change their username - only if they provide the correct current username.
     *
     * @param currentUserName The current username for verification
     * @param newUserName The new username to set
     * @throws IllegalArgumentException If the current username is incorrect
     */
    public void setUserName(String currentUserName, String newUserName)
    {
        if(!this.userName.equals(currentUserName)){
            throw new IllegalArgumentException("Current user name you entered is incorrect, try again");
        }
        this.userName = newUserName;
    }

    /**
     * Gets the set of roles assigned to this user.
     *
     * @return The set of user roles
     */
    public Set<UserRole> getRoles(){
        return roles;
    }

    /**
     * Gets the currently active role for this user.
     *
     * @return The current active role
     */
    public UserRole getCurrentRole(){
        return currentRole;
    }

    /**
     * Sets a new active role for this user, if the user has that role.
     *
     * @param role The new role to set as active
     * @throws IllegalArgumentException If the user does not have the specified role
     */
    public void setCurrentRole(UserRole role) {
        if(roles.contains(role)){
            this.currentRole = role;
            System.out.println("Current role updated to: " + role);
        }
        else
        {
            throw new IllegalArgumentException("The user does not have permission for this role: \" + role");
        }
    }

    /**
     * Gets the set of permissions granted to this user.
     *
     * @return The set of permissions
     */
    public Set<Permission> getPermissions(){
        return permissions;
    }

    /**
     * Checks if this user has a specific permission.
     *
     * @param permission The permission to check
     * @return true if the user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission){
        return permissions.contains(permission);
    }

    /**
     * Default implementation for viewing properties.
     * This method should be overridden by subclasses that have permission to view properties.
     *
     * @return An empty list of properties
     */
    public List<Property> viewProperties(){
        System.out.println("This user cannot view properties from the file.");
        return Collections.emptyList();
    }

    /**
     * Default implementation for deleting a property.
     * This method should be overridden by subclasses that have permission to delete properties.
     *
     * @param properties The list of properties
     * @param toDelete The property to delete
     */
    public void deleteProperty(List<Property> properties, Property toDelete){
        System.out.println("This user cannot delete properties from the file.");
    }

    /**
     * Default implementation for editing a property.
     * This method should be overridden by subclasses that have permission to edit properties.
     *
     * @param toEdit The property to edit
     * @param propertyUpdateRequest The update request containing new values
     */
    public void editProperty(Property toEdit, PropertyUpdateRequest propertyUpdateRequest){
        System.out.println("This user cannot edit properties from the file.");
    }

    /**
     * Abstract method to display user information.
     * Each user type will implement this method differently.
     */
    public abstract void describe();
}