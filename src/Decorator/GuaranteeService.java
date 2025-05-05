package Decorator;

/**
 * Concrete decorator that adds guarantee services to a real estate deal.
 * This class extends the DealDecorator abstract class to add guarantor services,
 * which provide a guarantee that someone will pay the debt if the client is
 * unable to meet their mortgage payment obligations.
 *
 * The decorator adds a fixed cost for guarantee services to the base deal
 * and extends the description to indicate the inclusion of these services.
 */
public class GuaranteeService extends DealDecorator {
    /** The fixed cost for guarantee services in currency units */
    private static final double GUARANTEE_COST = 500;

    /**
     * Constructs a new decorator that adds guarantee services to the specified deal.
     *
     * @param deal The deal to which guarantee services will be added
     */
    public GuaranteeService(Deal deal){
        super(deal);
    }

    /**
     * Gets the description of the decorated deal, enhanced with information
     * about the included guarantee services.
     *
     * @return The enhanced description
     */
    @Override
    public String getDescription(){
        return deal.getDescription() + "Including guarantee services";
    }

    /**
     * Gets the cost of the decorated deal, including the cost of guarantee services.
     *
     * @return The enhanced cost
     */
    @Override
    public double getCost(){
        return deal.getCost() + GUARANTEE_COST;
    }
}