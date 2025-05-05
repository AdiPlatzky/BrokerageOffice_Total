package test;

import Decorator.*;

/**
 * Test class demonstrating the Decorator design pattern implementation
 * with the real estate deal system.
 *
 * This class creates a basic deal and then progressively enhances it with
 * various decorators such as cleaning, haulage, design, and guarantee services,
 * showing how the description and cost are modified by each decorator.
 */
public class DecoratorTest
{
    /**
     * Main method demonstrating the Decorator pattern with real estate deals.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        // Create a basic deal
        Deal basic = new BasicDeal(750000, "Name of the transaction operator is: Herzel | ");
        System.out.println("Basic Deal: " + "\n" + basic.getDescription() + ", Price: " + basic.getCost() + " $");

        // Add cleaning services
        Deal withCleaning = new Cleaning(basic);
        System.out.println("Cleaning Deal: " + "\n" + withCleaning.getDescription() + "Price: " + withCleaning.getCost() + " $");

        // Add haulage services on top of cleaning
        Deal withCleaningAndHaulage = new Haulage(withCleaning);
        System.out.println("Cleaning and Haulage Deal: " + "\n" + withCleaningAndHaulage.getDescription() + "Price: " + withCleaningAndHaulage.getCost() + " $");

        // Create a full deal with all services
        Deal fullDeal = new GuaranteeService(new Design(new Haulage(new Cleaning(basic))));
        System.out.println("Full Deal: " + "\n" + fullDeal.getDescription() + "Total Price: " + fullDeal.getCost() + " $");
    }
}