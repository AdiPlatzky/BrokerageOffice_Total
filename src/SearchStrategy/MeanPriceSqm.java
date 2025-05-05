package SearchStrategy;

import Property.Property;
import Property.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete Strategy class in the Strategy design pattern.
 * This class implements a property search strategy that filters properties
 * based on their price per square meter compared to the average price in their area.
 *
 * The strategy calculates the average price per square meter for each area (defined by street and avenue)
 * and then filters properties based on whether their price is below, above, or close to that average.
 */
public class MeanPriceSqm implements PropertySearchStrategy
{
    /**
     * Enum defining the possible comparison types relative to the area average.
     */
    public enum ComparisonToAverage{
        /** Find properties with price below the area average */
        BELLOW_AVERAGE,
        /** Find properties with price above the area average */
        ABOVE_AVERAGE,
        /** Find properties with price close to the area average (within tolerance) */
        CLOSE_TO_AVERAGE,
        /** Find all properties regardless of price relative to average */
        ANY
    }

    /** The type of comparison to perform relative to the area average */
    private ComparisonToAverage comparisonType;

    /** Percentage tolerance for "close to average" comparisons */
    private static final double CLOSE_TOLERANCE_PERCENT = 5.0;

    /**
     * Constructs a search strategy with the specified comparison type.
     *
     * @param comparisonToAverage The type of comparison to perform
     */
    public MeanPriceSqm(ComparisonToAverage comparisonToAverage){
        this.comparisonType = comparisonToAverage;
    }

    /**
     * Executes the search algorithm, filtering properties by their price
     * relative to the average price in their area.
     *
     * @param properties The list of properties to search
     * @return A filtered list of properties matching the criteria
     */
    @Override
    public List<Property> search(List<Property> properties){
        Map<String, Double> averageByArea = calculateAverageByArea(properties);

        return properties.stream()
                .filter(property -> {
                    String area = getAreaKey(property.getAddress());

                    if (!averageByArea.containsKey(area)){
                        return true;
                    }

                    double areaAverage = averageByArea.get(area);
                    double propertyPrice = property.getPricePerSqm();

                    return matchesAverageComparison(propertyPrice, areaAverage);
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculates the average price per square meter for each area.
     *
     * @param properties The list of properties to analyze
     * @return A map from area key to average price per square meter
     */
    private Map<String, Double> calculateAverageByArea(List<Property> properties){
        Map<String, List<Double>> priceByArea = new HashMap<>();

        // Group prices by area
        for (Property property : properties){
            String area = getAreaKey(property.getAddress());

            if (!priceByArea.containsKey(area)){
                priceByArea.put(area, new ArrayList<>());
            }

            priceByArea.get(area).add(property.getPricePerSqm());
        }

        // Calculate average for each area
        Map<String,Double> aveageByArea = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : priceByArea.entrySet()){
            double sum = 0;
            for (Double price : entry.getValue()){
                sum += price;
            }
            aveageByArea.put(entry.getKey(), sum/entry.getValue().size());
        }
        return aveageByArea;
    }

    /**
     * Creates a unique key for an area based on its street and avenue.
     *
     * @param address The address of the property
     * @return A string key representing the area
     */
    private String getAreaKey(Address address){
        return address.getStreet() + "," + address.getAvenue();
    }

    /**
     * Determines if a given price matches the comparison criteria relative to the area average.
     *
     * @param price The price to check
     * @param average The average price in the area
     * @return true if the price matches the criteria, false otherwise
     */
    private boolean matchesAverageComparison(double price, double average)
    {
        switch (comparisonType){
            case BELLOW_AVERAGE:
                return price < average;
            case ABOVE_AVERAGE:
                return price > average;
            case CLOSE_TO_AVERAGE:
                double percentDiff = Math.abs(price - average) / average * 100;
                return percentDiff <= CLOSE_TOLERANCE_PERCENT;
            case ANY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Provides a human-readable description of the search strategy.
     *
     * @return A string describing the search strategy
     */
    @Override
    public String getDescription(){
        switch (comparisonType){
            case BELLOW_AVERAGE:
                return "Search for properties with a price per square meter below average";
            case ABOVE_AVERAGE:
                return "Search for properties with a price per square meter above average" + CLOSE_TOLERANCE_PERCENT + "%";
            case ANY:
                return "Search all the properties";
            default:
                return "Search for properties by comparison to average prices in the area";
        }
    }
}