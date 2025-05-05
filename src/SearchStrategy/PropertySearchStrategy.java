package SearchStrategy;

import Property.Property;

import java.util.List;

/**
 * Interface representing the Strategy component in the Strategy design pattern.
 * This interface defines a common contract for various property search strategies.
 *
 * Classes implementing this interface provide different algorithms for filtering
 * properties based on various criteria, such as status, price, or location.
 * This allows clients to switch between different search algorithms at runtime.
 */
public interface PropertySearchStrategy {
    /**
     * Executes the search algorithm on a list of properties.
     *
     * @param properties The list of properties to search
     * @return A filtered list of properties matching the search criteria
     */
    List<Property> search(List<Property> properties);

    /**
     * Provides a human-readable description of the search strategy.
     *
     * @return A string describing the search strategy
     */
    String getDescription();
}