package Decorator;

/**
 * Concrete decorator that adds cleaning services to a real estate deal.
 * This class extends the DealDecorator abstract class to add cleaning
 * services before moving into a property.
 *
 * The decorator adds a fixed cost for cleaning services to the base deal
 * and extends the description to indicate the inclusion of these services.
 */
public class Cleaning extends DealDecorator
{
    /** The fixed cost for cleaning services in currency units */
    private static final double CLEANING_COST = 300;

    /**
     * Constructs a new decorator that adds cleaning services to the specified deal.
     *
     * @param deal The deal to which cleaning services will be added
     */
    public Cleaning(Deal deal){
        super(deal);
    }

    /**
     * Gets the description of the decorated deal, enhanced with information
     * about the included cleaning services.
     *
     * @return The enhanced description
     */
    @Override
    public String getDescription(){
        return deal.getDescription() + "Including cleaning services";
    }

    /**
     * Gets the cost of the decorated deal, including the cost of cleaning services.
     *
     * @return The enhanced cost
     */
    @Override
    public double getCost(){
        return deal.getCost() + CLEANING_COST;
    }
}