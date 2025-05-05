package Decorator;

/**
 * Abstract class providing a base implementation for decorators in the Decorator pattern.
 * This class serves as the abstract decorator for the Deal component, allowing
 * new functionality to be added to Deal objects dynamically.
 *
 * The class implements the Deal interface and contains a reference to a Deal object,
 * which allows for nesting of decorators. By default, it delegates all method calls
 * to the wrapped Deal object. Concrete decorators can override these methods to
 * add new behavior before or after delegating to the wrapped object.
 */
public abstract class DealDecorator implements Deal
{
    /** The Deal object being decorated */
    protected Deal deal;

    /**
     * Constructs a new decorator around the specified Deal object.
     *
     * @param deal The Deal object to decorate
     */
    public DealDecorator(Deal deal){
        this.deal = deal;
    }

    /**
     * Gets the description of the decorated deal.
     * By default, delegates to the wrapped Deal object.
     *
     * @return The description from the wrapped Deal
     */
    @Override
    public String getDescription(){
        return deal.getDescription();
    }

    /**
     * Gets the cost of the decorated deal.
     * By default, delegates to the wrapped Deal object.
     *
     * @return The cost from the wrapped Deal
     */
    @Override
    public double getCost(){
        return deal.getCost();
    }
}