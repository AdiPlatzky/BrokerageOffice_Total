package Decorator;

/**
 * Class representing a basic real estate deal in the system.
 * This class serves as the concrete component in the Decorator pattern,
 * providing the core implementation of the Deal interface.
 *
 * A basic deal consists of a base price and a description, without any
 * additional services or features. Other services can be added to a basic
 * deal using decorators.
 */
public class BasicDeal implements Deal
{
    /** The base price of the deal in currency units */
    private double basePrice;

    /** Description of the deal */
    private String description;

    /**
     * Constructs a new basic deal with the specified price and description.
     *
     * @param basePrice The base price of the deal
     * @param description A description of the deal
     */
    public BasicDeal(double basePrice, String description){
        this.basePrice = basePrice;
        this.description = description;
    }

    /**
     * Gets the description of this deal.
     *
     * @return The deal description
     */
    @Override
    public String getDescription(){
        return description;
    }

    /**
     * Gets the cost of this deal, which is simply the base price.
     *
     * @return The base price
     */
    @Override
    public double getCost(){
        return basePrice;
    }
}