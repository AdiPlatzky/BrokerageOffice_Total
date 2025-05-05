package Property;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents the address of a property in the real estate system.
 * The purpose of an address is to locate all apartments or properties on the real estate map.
 * An address can be a main address (street, avenue) or a sub-address with additional components
 * for specifying apartments within a building or subdivided properties.
 *
 * The address is stored as a list of integers, where:
 * - The first integer (parts[0]) typically represents the street
 * - The second integer (parts[1]) typically represents the avenue
 * - Additional integers (if any) represent subdivisions within the property
 */
public class Address
{
    private final List<Integer> parts;

    /**
     * Constructs an address with the specified street and avenue.
     * This creates a main address with two components.
     *
     * @param street The street number
     * @param avenue The avenue number
     */
    public Address(int street, int avenue){
        this.parts = new ArrayList<>();
        parts.add(street);
        parts.add(avenue);
    }

    /**
     * Constructs an address from a string representation.
     * The string should contain space-separated integers representing the address components.
     *
     * @param addressStr The string representation of the address (e.g., "2 3" or "2 3 1")
     * @throws IllegalArgumentException If the address format is invalid or empty
     */
    public Address(String addressStr)
    {
        this.parts = new ArrayList<>();
        String[] tokens = addressStr.trim().split(" ");
        for (String token : tokens){
            try{
                parts.add(Integer.parseInt(token));
            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Invalid address format: " + addressStr);
            }
        }
        if (parts.isEmpty()){
            throw new IllegalArgumentException("Address must contain at least one part");
        }
    }

    /**
     * Returns a copy of the address components.
     *
     * @return A new list containing all address components
     */
    public List<Integer> getParts() {
        return new ArrayList<>(parts);
    }

    /**
     * Gets the street component of the address.
     *
     * @return The street number, or 0 if not available
     */
    public int getStreet()
    {
        return parts.size() > 0 ? parts.get(0) : 0;
    }

    /**
     * Gets the avenue component of the address.
     *
     * @return The avenue number, or 0 if not available
     */
    public int getAvenue()
    {
        return parts.size() > 1 ? parts.get(1) : 0;
    }

    /**
     * Determines if this is a sub-address (has more than 2 components).
     *
     * @return true if this is a sub-address, false otherwise
     */
    public boolean isSubAddress(){
        return parts.size() > 2;
    }

    /**
     * Gets the main address (first two components) of this address.
     *
     * @return The main address, or this address if it has 2 or fewer components
     */
    public Address getMainAddress(){
        if (parts.size() <= 2){
            return this;
        }
        return new Address(parts.get(0), parts.get(1));
    }

    /**
     * Gets the parent address by removing the last component.
     * For example, if the address is (2,3,1,2), the parent address is (2,3,1).
     *
     * @return The parent address, or null if this is a main address
     */
    public Address getParentAddress(){
        if (parts.size() <= 2){
            return null;
        }
        List<Integer> parentParts = new ArrayList<>(parts);
        parentParts.remove(parentParts.size() - 1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parentParts.size(); i++)
        {
            sb.append(parentParts.get(i));
            if (i < parentParts.size() - 1)
            {
                sb.append(" ");
            }
        }
        return new Address(sb.toString());
    }

    /**
     * Returns a string representation of the address in the format "(x,y,z)".
     *
     * @return A string representation of the address
     */
    @Override
    public String toString(){
        return "(" + parts.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
    }

    /**
     * Returns a string representation of the address suitable for storage in a file,
     * with components separated by spaces.
     *
     * @return A space-separated string representation of the address
     */
    public String toFileString(){
        return parts.stream().map(String::valueOf).collect(Collectors.joining(" "));
    }

    /**
     * Compares this address with another object for equality.
     * Two addresses are considered equal if they have the same components.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof Address)){
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(parts, address.parts);
    }

    /**
     * Returns a hash code for this address based on its components.
     * Used for comparing addresses in collections like Set or Map.
     *
     * @return A hash code value for this address
     */
    @Override
    public int hashCode(){
        return Objects.hash(parts);
    }
}