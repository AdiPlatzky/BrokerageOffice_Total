package SearchStrategy;

import Property.Property;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Strategy class in the Strategy design pattern.
 * This class implements a property search strategy that filters properties
 * based on their price per square meter.
 *
 * The strategy offers several comparison types (less than, greater than, equals, any)
 * and uses Java Streams to efficiently filter properties that match the criteria.
 */
public class PriceBySqm implements PropertySearchStrategy
{
    /**
     * Enum defining the possible comparison types for the price search.
     */
    public enum ComparisonType{
        /** Find properties with price less than the target */
        LESS_THAN,
        /** Find properties with price greater than the target */
        GREATER_THEN,
        /** Find properties with price approximately equal to the target (within tolerance) */
        EQUALS,
        /** Find all properties regardless of price */
        ANY
    }

    /** The target price per square meter to compare against */
    private double targetPrice;

    /** The type of price comparison to perform */
    private ComparisonType comparisonType;

    /** Tolerance for price equality comparisons (in currency units) */
    private static final double PRICE_TOLERANCE = 100;

    /**
     * Constructs a search strategy with the specified target price and comparison type.
     *
     * @param price The target price per square meter
     * @param comparisonType The type of comparison to perform
     * @throws IllegalArgumentException If the specified price is not positive
     */
    public PriceBySqm(double price, ComparisonType comparisonType){
        if (price <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }

        this.targetPrice = price;
        this.comparisonType = comparisonType;
    }

    /**
     * Executes the search algorithm, filtering properties by price per square meter.
     *
     * @param properties The list of properties to search
     * @return A filtered list of properties matching the price criteria
     */
    @Override
    public List<Property> search(List<Property> properties){
        return properties.stream()
                .filter(property -> matchesPrice(property.getPricePerSqm()))
                .collect(Collectors.toList());
    }

    /**
     * Determines if a given price matches the search criteria.
     *
     * @param price The price to check
     * @return true if the price matches the criteria, false otherwise
     */
    private boolean matchesPrice(double price)
    {
        return switch (comparisonType) {
            case LESS_THAN -> price < targetPrice;
            case GREATER_THEN -> price > targetPrice;
            case EQUALS -> Math.abs(price - targetPrice) <= PRICE_TOLERANCE;
            case ANY -> true;
            default -> false;
        };
    }

    /**
     * Provides a human-readable description of the search strategy.
     *
     * @return A string describing the search strategy
     */
    @Override
    public String getDescription(){
        return switch (comparisonType) {
            case LESS_THAN -> "Search for properties with a price per square meter lower than - " + targetPrice + " $";
            case GREATER_THEN ->
                    "Search for properties with a price per square meter higher than - " + targetPrice + " $";
            case EQUALS -> "Search for properties with a price per square meter equals - " + targetPrice + " $";
            case ANY -> "Search for all properties without price limit";
            default -> "Search for properties by price per square";
        };
    }
}