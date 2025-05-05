package test;

import Property.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit test class for the Property classes, testing both SimpleProperty and
 * CompositeProperty implementations.
 *
 * This class contains unit tests that verify the correctness of various methods
 * in the Property hierarchy, including area calculations, price calculations,
 * property information display, property finding, and add/remove operations.
 *
 * The tests demonstrate the functionality of the Composite design pattern
 * in the property system.
 */
class PropertyTest {

    /**
     * Tests the getSqm() method for both SimpleProperty and CompositeProperty.
     * Verifies that:
     * - Simple properties return their area correctly
     * - CompositeProperties with no sub-properties throw an exception
     * - CompositeProperties calculate their total area correctly from sub-properties
     * - Nested composite properties calculate their area correctly
     */
    @Test
    void getSqm()
    {
        // Test SimpleProperty with valid area
        Address a = new Address(1,1);
        SimpleProperty A = new SimpleProperty(101, a, 5000, 56, Status.FOR_SALE);
        assertEquals(5000, A.getSqm(), 0.01);

        // Test SimpleProperty with invalid area (zero)
        Address b = new Address(2,2);
        SimpleProperty B = new SimpleProperty(200, b, 0,30, Status.FOR_SALE);
        Exception ex = assertThrows(IllegalArgumentException.class, B::getSqm);
        assertEquals( "Square meters must be positive", ex.getMessage());

        // Test empty CompositeProperty (should throw exception)
        Address c = new Address(3,3);
        CompositeProperty C = new CompositeProperty(300, c, Status.FOR_SALE);
        Exception ex1 = assertThrows(IllegalArgumentException.class, C::getSqm);
        assertEquals("Problem!! \n check if: square is positive or the sub properties array does not empty - they must be positive...", ex1.getMessage());

        // Test CompositeProperty with multiple sub-properties
        Address d = new Address(4,4);
        CompositeProperty D = new CompositeProperty(400, d, Status.FOR_SALE);
        D.add(new SimpleProperty(401, d, 7000, 35, Status.FOR_SALE));
        D.add(new SimpleProperty(402, d, 8000, 45, Status.FOR_SALE));
        assertEquals(15000, D.getSqm(), 0.01);

        // Test complex nested composite structure
        Address e = new Address(5,5);
        CompositeProperty E = new CompositeProperty(500, e, Status.FOR_SALE);
        CompositeProperty childE_1 = new CompositeProperty(501, E.getAddress(), Status.FOR_SALE);
        CompositeProperty childE_1_1 = new CompositeProperty(503, childE_1.getAddress(), Status.FOR_SALE);

        childE_1.add(new SimpleProperty(502, childE_1.getAddress(), 8000, 45, Status.FOR_SALE));
        childE_1_1.add(new SimpleProperty(504, childE_1_1.getAddress(), 700, 30, Status.FOR_SALE));
        childE_1.add(childE_1_1);
        E.add(childE_1);
        E.add(new SimpleProperty(505, E.getAddress(), 500, 85, Status.FOR_SALE));
        assertEquals(9200, E.getSqm(), 0.01);
    }

    /**
     * Tests the getTotalPrice() method for both SimpleProperty and CompositeProperty.
     * Verifies that:
     * - Simple properties calculate their price correctly
     * - CompositeProperties with no sub-properties throw an exception
     * - CompositeProperties calculate their total price correctly from sub-properties
     * - Nested composite properties calculate their price correctly
     */
    @Test
    void getTotalPrice()
    {
        // Test SimpleProperty with valid area and price
        Address a = new Address(1,1);
        SimpleProperty A = new SimpleProperty(101, a, 5000, 20, Status.FOR_SALE);
        assertEquals(100000, A.getTotalPrice(), 0.01);

        // Test SimpleProperty with invalid area (zero)
        Address b = new Address(2,2);
        SimpleProperty B = new SimpleProperty(200, b, 0,30, Status.FOR_SALE);
        Exception ex = assertThrows(IllegalArgumentException.class, B::getTotalPrice);
        assertEquals("Square meters and price per square must be positive!", ex.getMessage());

        // Test empty CompositeProperty (should throw exception)
        Address c = new Address(3,3);
        CompositeProperty C = new CompositeProperty(300, c, Status.FOR_SALE);
        Exception ex1 = assertThrows(IllegalArgumentException.class, C::getTotalPrice);
        assertEquals("Problem!! \n chack if: price per square, square or sub properties are positive, they must be...", ex1.getMessage());

        // Test CompositeProperty with multiple sub-properties
        Address d = new Address(4,4);
        CompositeProperty D = new CompositeProperty(400, d, Status.FOR_SALE);
        D.add(new SimpleProperty(401, d, 7000, 35, Status.FOR_SALE));
        D.add(new SimpleProperty(402, d, 8000, 45, Status.FOR_SALE));
        assertEquals(605000, D.getTotalPrice(), 0.01);

        // Test complex nested composite structure
        Address e = new Address(5,5);
        CompositeProperty E = new CompositeProperty(500, e, Status.FOR_SALE);
        CompositeProperty childE_1 = new CompositeProperty(501, E.getAddress(), Status.FOR_SALE);
        CompositeProperty childE_1_1 = new CompositeProperty(503, childE_1.getAddress(), Status.FOR_SALE);

        childE_1.add(new SimpleProperty(502, childE_1.getAddress(), 8000, 45, Status.FOR_SALE));
        childE_1_1.add(new SimpleProperty(504, childE_1_1.getAddress(), 700, 30, Status.FOR_SALE));
        childE_1.add(childE_1_1);
        E.add(childE_1);
        E.add(new SimpleProperty(505, E.getAddress(), 500, 85, Status.FOR_SALE));
        assertEquals(423500, E.getTotalPrice(), 0.01);
    }

    /**
     * Tests the displayPropertyInfo() method for both SimpleProperty and CompositeProperty.
     * Verifies that the returned string contains all expected property information.
     */
    @Test
    void displayPropertyInfo()
    {
        // Test SimpleProperty info display
        Address a = new Address("2 3");
        SimpleProperty A = new SimpleProperty(123, a, 7000, 30, Status.FOR_SALE);

        String info = A.displayPropertyInfo();

        assertTrue(info.contains("123"));       // id
        assertTrue(info.contains("(2,3)"));     // address
        assertTrue(info.contains("For sale"));  // status
        assertTrue(info.contains("30"));        // pricePerSqm
        assertTrue(info.contains("7000"));      // sqm

        // Test CompositeProperty info display
        Address b = new Address("1 1");
        CompositeProperty B = new CompositeProperty(321, b, Status.FOR_SALE);
        B.add(new SimpleProperty(322, new Address("1 1 1"), 3000, 20, Status.FOR_SALE));
        B.add(new SimpleProperty(323, new Address("1 1 2"), 4000, 25, Status.FOR_SALE));

        String info1 = B.displayPropertyInfo();

        assertTrue(info1.contains("321"));             // shared id
        assertTrue(info1.contains("(1,1)"));           // main address
        assertTrue(info1.contains("For sale"));        // status
        assertTrue(info1.contains("7000"));            // totalSqm
        assertTrue(info1.contains("160000"));          // totalPrice
        assertTrue(info1.contains("(1,1,1)"));         // subProperties
        assertTrue(info1.contains("(1,1,2)"));
    }

    /**
     * Tests the findPropertyByAddress() method.
     * Verifies that:
     * - Properties can be found by their address in a composite structure
     * - Null is returned when a property is not found
     */
    @Test
    void findPropertyByAddress()
    {
        Address root = new Address(1,1);
        Address child1 = new Address("1 1 1");
        Address child2 = new Address("1 1 2");

        CompositeProperty A = new CompositeProperty(101, root, Status.FOR_SALE);
        SimpleProperty A_1 = new SimpleProperty(102, child1, 1000, 20, Status.FOR_SALE);
        SimpleProperty A_2 = new SimpleProperty(103, child2, 2000, 20, Status.FOR_SALE);

        A.add(A_1);
        A.add(A_2);

        Property result = A.findPropertyByAddress(String.valueOf(child2));
        assertEquals(A_2, result);

        Address a = new Address("9 9");
        CompositeProperty composite = new CompositeProperty(1100, a, Status.FOR_SALE);

        Property result1 = composite.findPropertyByAddress(String.valueOf(new Address("1 1 1")));
        assertNull(result1); // not found
    }

    /**
     * Tests the add() and remove() methods of CompositeProperty.
     * Verifies that:
     * - Sub-properties can be added to a composite property
     * - Sub-properties can be removed from a composite property
     * - The size of the sub-properties list is updated correctly
     */
    @Test
    void addAndRemove()
    {
        Address a = new Address(6,6);
        CompositeProperty A = new CompositeProperty(600, a, Status.FOR_SALE);
        SimpleProperty childA_1 = new SimpleProperty(601, A.getAddress(), 1000, 20, Status.FOR_SALE);

        //before adding
        assertEquals(0, A.getSubProperties().size());

        //add
        A.add(childA_1);
        assertEquals(1, A.getSubProperties().size());

        //remove
        A.remove(childA_1);
        assertEquals(0, A.getSubProperties().size());

        Address b = new Address(7,7);
        CompositeProperty B = new CompositeProperty(700, b, Status.FOR_SALE);
        SimpleProperty childB_1 = new SimpleProperty(701, B.getAddress(), 1000,20, Status.FOR_SALE);
        SimpleProperty childB_2 = new SimpleProperty(702, B.getAddress(), 2000,30, Status.FOR_SALE);

        B.add(childB_1);
        B.remove(childB_2);
        assertEquals(1, B.getSubProperties().size());

        B.add(childB_2);
        B.remove(childB_1);
        assertEquals(1, B.getSubProperties().size());
    }

    /**
     * Tests the toString() method of the Address class.
     * Verifies that the string representation is formatted correctly.
     */
    @Test
    void testToString()
    {
        Address a = new Address("5 6 7");
        assertEquals("(5,6,7)", a.toString());
    }
}