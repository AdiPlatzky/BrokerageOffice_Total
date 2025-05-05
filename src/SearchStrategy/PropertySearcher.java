package SearchStrategy;

import Property.Property;

import java.util.List;

/**
 * Class that integrates different search strategies in the Strategy design pattern.
 * This class serves as the Context in the Strategy pattern, maintaining a reference
 * to a concrete search strategy and delegating search operations to it.
 *
 * The PropertySearcher allows clients to change the search strategy at runtime
 * and execute searches using the currently selected strategy without needing to
 * know the details of the strategy's implementation.
 */
public class PropertySearcher
{
    /** The current search strategy being used */
    private PropertySearchStrategy strategy;

    /**
     * Constructs a PropertySearcher with the specified initial strategy.
     *
     * @param strategy The initial search strategy to use
     */
    public PropertySearcher(PropertySearchStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * Changes the current search strategy.
     * This allows switching between different search algorithms at runtime.
     *
     * @param strategy The new search strategy to use
     */
    public void setStrategy(PropertySearchStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * Executes a search using the current strategy.
     *
     * @param properties The list of properties to search
     * @return A filtered list of properties matching the search criteria
     */
    public List<Property> executeSearch(List<Property> properties){
        return strategy.search(properties);
    }

    /**
     * Gets a description of the current search strategy.
     *
     * @return A string describing the current search strategy
     */
    public String getStrategyDescription(){
        return strategy.getDescription();
    }
}