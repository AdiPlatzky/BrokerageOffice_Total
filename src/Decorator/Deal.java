package Decorator;

/**
 * Interface representing a real estate deal in the system.
 * This interface is part of the Decorator pattern implementation,
 * defining the core functionality that all deals, both basic and decorated,
 * must provide.
 *
 * The interface encapsulates two key aspects of a real estate deal:
 * 1. A description of the deal
 * 2. The total cost of the deal
 */
public interface Deal
{
    /**
     * Gets a description of this deal.
     *
     * @return A string describing the deal
     */
    String getDescription();

    /**
     * Gets the total cost of this deal.
     *
     * @return The cost in currency units
     */
    double getCost();
}