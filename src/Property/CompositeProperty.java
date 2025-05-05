package Property;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a composite property - a property that contains sub-properties.
 *
 * This class implements the Composite pattern, allowing it to contain a list of Property objects
 * that can themselves be either simple or composite (enabling recursion).
 *
 * The total area and price of a composite property are calculated by summing the values
 * of all its sub-properties.
 */
public class CompositeProperty extends Property
{
    /** List of sub-properties contained within this composite property */
    private List<Property> subProperties;

    /**
     * Constructs a new composite property with the specified attributes.
     * The size and price are initially set to 0 and will be calculated based on sub-properties.
     *
     * @param id Unique identifier for the property
     * @param address Address of the property
     * @param status Current market status
     */
    public CompositeProperty(int id, Address address, Status status)
    {
        super(id, address, 0, 0, status);
        this.subProperties = new ArrayList<>();
    }

    /**
     * Calculates and returns the total size of this composite property by summing
     * the sizes of all its sub-properties.
     *
     * @return The total size in square meters
     * @throws IllegalArgumentException If there are no sub-properties or the calculated area is not positive
     */
    @Override
    public double getSqm()
    {
        if (!subProperties.isEmpty()){
            double totalSqm = 0;

            for(Property p : subProperties){
                totalSqm += p.getSqm();
            }
            this.sqm = totalSqm;
            return totalSqm;
        }
        else
        {
            throw new IllegalArgumentException("Problem!! \n check if: square is positive or the sub properties array does not empty - they must be positive...");
        }
    }

    /**
     * Calculates and returns the total price of this composite property by summing
     * the prices of all its sub-properties.
     *
     * @return The total price
     * @throws IllegalArgumentException If there are no sub-properties or the calculated price is not positive
     */
    @Override
    public double getTotalPrice()
    {
        double totalPrice = 0;
        if (!subProperties.isEmpty())
        {
            for(Property p : subProperties)
            {
                totalPrice += p.getTotalPrice();
            }
            this.price = totalPrice;
            return totalPrice;
        }
        else {
            throw new IllegalArgumentException("Problem!! \n chack if: price per square, square or sub properties are positive, they must be...");
        }
    }

    /**
     * Gets a copy of the list of sub-properties for this composite property.
     *
     * @return A new list containing all sub-properties
     */
    @Override
    public List<Property> getSubProperties()
    {
        return new ArrayList<>(subProperties);
    }

    /**
     * Sets new sub-properties for this composite property.
     *
     * @param newSubProperties The new list of sub-properties, or null to clear the list
     */
    @Override
    public void setSubProperties(List<Property> newSubProperties)
    {
        if (newSubProperties == null){
            this.subProperties = new ArrayList<>();
        }
        else {
            this.subProperties = new ArrayList<>(newSubProperties);
        }
    }

    /**
     * Sets a new price per square meter for this composite property and all its sub-properties.
     *
     * @param newPricePerSqm The new price per square meter
     * @throws IllegalArgumentException If the new price per square meter is not positive
     */
    @Override
    public void setPricePerSqm(double newPricePerSqm){
        if (newPricePerSqm <= 0){
            throw new IllegalArgumentException("Price per square must be positive");
        }

        this.pricePerSqm = newPricePerSqm;
        for (Property p : subProperties){
            p.setPricePerSqm(newPricePerSqm);
        }
        getTotalPrice();
    }

    /**
     * Setting the size directly is not supported for composite properties since
     * their size is calculated from their sub-properties.
     *
     * @param newSqm The new size in square meters
     * @throws UnsupportedOperationException Always, since composite property size is calculated automatically
     */
    @Override
    public void setSqm(double newSqm){
        throw new UnsupportedOperationException("Cannot manually set sqm for a composite property. It is calculated automatically from sub-properties.");
    }

    /**
     * Sets a new market status for this composite property and all its sub-properties.
     *
     * @param newStatus The new status
     */
    @Override
    public void setStatus(Status newStatus){
        super.setStatus(newStatus);

        for (Property p :  subProperties){
            p.setStatus(newStatus);
        }
    }

    /**
     * Displays detailed information about this composite property and its sub-properties.
     *
     * @return A string containing property information
     */
    @Override
    public String displayPropertyInfo()
    {
        String compositeP = "# Composite Property: " + id +
                " at " + address +
                " | Area: " + getSqm() + " sqm" +
                " | " + getTotalPrice() + " $" +
                " | Status: " + status;

        if (!subProperties.isEmpty())
        {
            compositeP += "   \n Sub properties: ";
            for (int i = 0; i < subProperties.size(); i++)
            {
                Property subProperty = subProperties.get(i);
                compositeP += " " + (i + 1) + ". " +
                        "# " + subProperty.getId() + " at " +
                        subProperty.getAddress() +
                        " | " + subProperty.getSqm() + " sqm" +
                        " | " + subProperty.getTotalPrice() + " $" +
                        " | " + subProperty.getStatus();
            }
        }
        return compositeP;
    }

    /**
     * Finds a property by its address within this composite property hierarchy.
     * Searches both this property and recursively through all sub-properties.
     *
     * @param address The address to search for
     * @return The property with the specified address, or null if not found
     */
    @Override
    public Property findPropertyByAddress(String address)
    {
        if(this.address.toString().equals(address)){
            return this;
        }
        for (Property sub : subProperties){
            Property found = sub.findPropertyByAddress(address);
            if (found != null){
                return found;
            }
        }
        return null;
    }

    /**
     * Adds a sub-property to this composite property.
     *
     * @param property The sub-property to add
     * @throws IllegalArgumentException If the property is null or already exists in this composite
     */
    @Override
    public void add(Property property)
    {
        if (property == null){
            throw new IllegalArgumentException("Cannot add null property.");
        }
        if (subProperties.contains(property)){
            throw new IllegalArgumentException("Problem!! \n This property already exists in the system!");
        }
        subProperties.add(property);
    }

    /**
     * Removes a sub-property from this composite property.
     *
     * @param property The sub-property to remove
     * @throws IllegalArgumentException If the property does not exist in this composite
     */
    @Override
    public void remove(Property property)
    {
        try
        {
            subProperties.remove(property);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Problem!! \n This property does not exists in the system!");
        }
    }

    /**
     * Displays detailed hierarchical information about this composite property
     * and all its sub-properties.
     */
    public void displayDetailedInfo(){
        String info = buildInfoTree(this, 0);
        System.out.println(info);
    }

    /**
     * Recursively builds a hierarchical string representation of a property and its sub-properties.
     *
     * @param property The property to display
     * @param depth The current depth in the property hierarchy
     * @return A string representation of the property hierarchy
     */
    private String buildInfoTree(Property property, int depth)
    {
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(depth);

        if (property instanceof CompositeProperty)
        {
            CompositeProperty composite = (CompositeProperty) property;
            sb.append(indent)
                    .append("+ Composite Property: # ").append(composite.getId())
                    .append(" at ").append(composite.getAddress())
                    .append(" | Total Area: ").append(composite.getSqm()).append(" sqm")
                    .append("| Total Price: ").append(composite.getTotalPrice())
                    .append(" $ | Status: ").append(composite.getStatus())
                    .append("\n");

            List<Property> children = composite.getSubProperties();
            for (Property child : children){
                sb.append(buildInfoTree(child, depth +1));
            }
        }
        else
        {
            SimpleProperty simple = (SimpleProperty) property;
            sb.append(indent)
                    .append("- Simple Property: #").append(simple.getId())
                    .append(" at ").append(simple.getAddress())
                    .append(" | ").append(simple.getSqm()).append(" sqm")
                    .append(" | ").append(simple.getTotalPrice())
                    .append(" $ | ").append(simple.getStatus())
                    .append("\n");
        }
        return sb.toString();
    }
}