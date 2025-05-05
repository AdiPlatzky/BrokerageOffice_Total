package SearchStrategy;

import Property.Property;
import Property.Status;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Strategy class in the Strategy design pattern.
 * This class implements a property search strategy that filters properties
 * based on their status (FOR_SALE or SOLD).
 *
 * The strategy uses Java Streams to efficiently filter properties
 * that match the specified target status.
 */
public class ByStatus implements PropertySearchStrategy
{
    /** The status to search for (FOR_SALE or SOLD) */
    private Status targetStatus;

    /**
     * Constructs a search strategy with the specified target status.
     *
     * @param status The status to search for
     */
    public ByStatus(Status status){
        this.targetStatus = status;
    }

    /**
     * Executes the search algorithm, filtering properties by status.
     *
     * @param properties The list of properties to search
     * @return A filtered list of properties matching the target status
     */
    @Override
    public List<Property> search(List<Property> properties){
        return properties.stream()
                .filter(property -> property.getStatus() == targetStatus)
                .collect(Collectors.toList());
    }

    /**
     * Provides a human-readable description of the search strategy.
     *
     * @return A string describing the search strategy
     */
    @Override
    public String getDescription(){
        return "Search properties by status: " + targetStatus;
    }
}