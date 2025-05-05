package Decorator;

/**
 * Concrete decorator that adds design services to a real estate deal.
 * This class extends the DealDecorator abstract class to add interior
 * design services to a property deal.
 *
 * The decorator adds a fixed cost for design services to the base deal
 * and extends the description to indicate the inclusion of these services.
 */
public class Design extends DealDecorator
{
    /** The fixed cost for design services in currency units */
    private static final double DESIGN_COST = 2000;

    /**
     * Constructs a new decorator that adds design services to the specified deal.
     *
     * @param deal The deal to which design services will be added
     */
    public Design(Deal deal){
        super(deal);
    }

    /**
     * Gets the description of the decorated deal, enhanced with information
     * about the included design services.
     *
     * @return The enhanced description
     */
    @Override
    public String getDescription(){
        return deal.getDescription() + "Including design services";
    }

    /**
     * Gets the cost of the decorated deal, including the cost of design services.
     *
     * @return The enhanced cost
     */
    @Override
    public double getCost(){
        return deal.getCost() + DESIGN_COST;
    }
}