package Decorator;

/**
 * Concrete decorator that adds haulage services to a real estate deal.
 * This class extends the DealDecorator abstract class to add moving
 * and transportation services for the property buyer.
 *
 * The decorator adds a fixed cost for haulage services to the base deal
 * and extends the description to indicate the inclusion of these services.
 */
public class Haulage extends DealDecorator
{
    /** The fixed cost for haulage services in currency units */
    private static final double HAULAGE_COST = 1200;

    /**
     * Constructs a new decorator that adds haulage services to the specified deal.
     *
     * @param deal The deal to which haulage services will be added
     */
    public Haulage(Deal deal){
        super(deal);
    }

    /**
     * Gets the description of the decorated deal, enhanced with information
     * about the included haulage services.
     *
     * @return The enhanced description
     */
    @Override
    public String getDescription(){
        return deal.getDescription() + ", Including haulage services";
    }

    /**
     * Gets the cost of the decorated deal, including the cost of haulage services.
     *
     * @return The enhanced cost
     */
    @Override
    public double getCost(){
        return deal.getCost() + HAULAGE_COST;
    }
}