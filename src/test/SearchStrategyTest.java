package test;

import Property.Property;
import Property.Status;
import SearchStrategy.ByStatus;
import SearchStrategy.MeanPriceSqm;
import SearchStrategy.PriceBySqm;
import SearchStrategy.PropertySearcher;
import SystemPermissions.PropertyFileReader;

import java.util.List;

/**
 * Test class demonstrating the Strategy design pattern implementation
 * in the property search system.
 *
 * This class shows how different search strategies can be used with the
 * PropertySearcher to filter properties based on various criteria:
 * - By status (FOR_SALE or SOLD)
 * - By price per square meter (less than a threshold)
 * - By relation to average price (below average)
 */
public class SearchStrategyTest
{
    /**
     * Main method demonstrating the Strategy pattern with property searches.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        // Load all properties from the CSV file
        List<Property> allProperties = PropertyFileReader.readPropertiesFromCSV();
        System.out.println("Total properties at the system: " + allProperties.size());

        // Create a searcher with the ByStatus strategy (FOR_SALE)
        PropertySearcher searcher = new PropertySearcher(new ByStatus(Status.FOR_SALE));

        // Execute search with the FOR_SALE strategy
        List<Property> forSaleProperties = searcher.executeSearch(allProperties);
        System.out.println("/n=== " + searcher.getStrategyDescription() + " ===");
        displaySearchResults(forSaleProperties);

        // Change strategy to search for SOLD properties
        searcher.setStrategy(new ByStatus(Status.SOLD));
        List<Property> soldProperties = searcher.executeSearch(allProperties);
        System.out.println("/n=== " + searcher.getStrategyDescription() + " ===");
        displaySearchResults(soldProperties);

        // Change strategy to search for properties below a price threshold
        searcher.setStrategy(new PriceBySqm(80000, PriceBySqm.ComparisonType.LESS_THAN));
        List<Property> cheapProperties = searcher.executeSearch(allProperties);
        System.out.println("/n=== " + searcher.getStrategyDescription() + " ===");
        displaySearchResults(cheapProperties);

        // Change strategy to search for properties below the average price in their area
        searcher.setStrategy(new MeanPriceSqm(MeanPriceSqm.ComparisonToAverage.BELLOW_AVERAGE));
        List<Property> belowAverageProperties = searcher.executeSearch(allProperties);
        System.out.println("/n=== " + searcher.getStrategyDescription() + " ===");
        displaySearchResults(belowAverageProperties);
    }

    /**
     * Helper method to display the results of a property search.
     *
     * @param results The list of properties that matched the search criteria
     */
    private static void displaySearchResults(List<Property> results){
        if (results.isEmpty()){
            System.out.println("No properties were found that match the search.");
            return;
        }
        System.out.println("Found " + results.size() + " properties: ");
        for (Property property : results){
            System.out.println("- " + property);
        }
    }
}