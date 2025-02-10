package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit testing for Truck class.
 *
 * @author Kassie Whitney (kjw0728@uw.edu)
 * @version 02.09.25
 */
public class TruckTest {
     /**
     * The number of times to repeat a test to have a high probability that all random
     * possiblites have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 100;

    /**
     * Fixed x coordinate.
     */
    private static final int FIXTURE_X_1 = 10;

    /**
     * Fixed y coordiante.
     */
    private static final int FIXTURE_Y_1 = 10;

    /**
     * Truck death time value
     */
    private static final int DEATH_TIME = 0;

    /**
     * Empty map used for storing concreate values for testing
     */
    private static final Map<Direction, Terrain> NEIGHBORS = new HashMap<>();

    /**
     * Counter for looping
     */
    private  int myCounter;

    /**
     * Stores boolean if the vehicle has seen this direction
     */
    private  boolean mySeenNorth;

    /**
     * Stores boolean if the vehicle has seen this direction
     */
    private  boolean mySeenEast;

    /**
     * Stores boolean if the vehicle has seen this direction
     */
    private  boolean mySeenSouth;

    /**
     * Stores boolean if the vehicle has seen this direction
     */
    private  boolean mySeenWest;

    /**
     * Instantiation of a new Truck object.
     */
    private  final Truck myTruck = new Truck(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

    /**
     * Creates a map.
     */
    @BeforeAll
    public static void createNewMap() {

        NEIGHBORS.put(Direction.NORTH, Terrain.STREET);
        NEIGHBORS.put(Direction.EAST, Terrain.LIGHT);
        NEIGHBORS.put(Direction.WEST, Terrain.CROSSWALK);
        NEIGHBORS.put(Direction.SOUTH, Terrain.STREET);
    }

    /**
     * Test method for Truck constructor.
     */
    @Test
    public void testTruckConstructor() {

        assertAll("Testing the constructor for the truck class",
                () -> assertEquals(FIXTURE_X_1,
                        myTruck.getX(),
                        "Truck x coordinate was not initialized properly!"),
                () -> assertEquals(FIXTURE_Y_1,
                        myTruck.getY(),
                        "Truck y cooridinate was not initialized properly!"),
                () -> assertEquals(Direction.NORTH,
                        myTruck.getDirection(),
                        "Truck direction was not initialized properly!")
        );
    }

    /**
     * Test getImageFileName() method (ALIVE).
     */
    @Test
    private void testTruckGetImageFileNameAlive() {
        final String alive = "truck.gif";
        assertEquals(alive, myTruck.getImageFileName(),
                "The file name for an alive Truck incorrect!");

        assertNotEquals("truck_dead.gif", myTruck.getImageFileName(),
                "The truck should not have an imageFileName for its death, "
                        + "because the truck cant die!");
    }



    /**
     * Testing getDeathTime().
     */
    @Test
    public void testGetDeathTime() {
        assertEquals(DEATH_TIME, myTruck.getDeathTime(),
                String.format("Death Time is incorrect, should be "
                        + "%d instead of %d!", DEATH_TIME, myTruck.getDeathTime()));
    }


    /**
     * Testing setters.
     */
    @Test
    private void testATVSetters() {
        myTruck.setX(0);
        myTruck.setY(0);
        myTruck.setDirection(Direction.WEST);

        assertAll("Testing the Trucks setters",
                () -> assertNotEquals(FIXTURE_X_1,
                        myTruck.getX(),
                        "SetX() is not setting new X-coordinates properly"),
                () -> assertNotEquals(FIXTURE_Y_1,
                        myTruck.getY(),
                        "SetY() is nott setting new Y-coordinates properly"),
                () -> assertNotEquals(Direction.NORTH,
                        myTruck.getDirection(),
                        "SetDiretion() is not setting the new direction properly")
        );
    }

    /**
     * Test method for Truck collision.
     */
    @Test
    private void testTruckCollisionWithATV() {
        final Atv a = new Atv(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

        myTruck.collide(a);

        assertTrue(myTruck.isAlive(),
                "The truck did not process the collision correctly!");
    }

    /**
     * Test method for isAlive Method.
     */
    @Test
    public void testTruckIsAlive() {
        final Atv a = new Atv(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);
        myTruck.collide(a);

        assertTrue(myTruck.isAlive(), "The Truck should not have died on collision!");

    }

    /**
     * Testing the reset() method.
     */
    @Test
    public void testReset() {
        myTruck.setX(0);
        myTruck.setY(0);
        myTruck.setDirection(Direction.EAST);
        myTruck.reset();

        assertAll("Testing the Trucks reset method",
                () -> assertEquals(FIXTURE_X_1,
                        myTruck.getX(),
                        "The reset method did not default to original x-Coordinate!"),
                () -> assertEquals(FIXTURE_Y_1,
                        myTruck.getY(),
                        "The reset method did not default to original y-coordinate!"),
                () -> assertEquals(Direction.NORTH,
                        myTruck.getDirection(),
                        "The reset method did not default "
                                + "to original starting direction!")
        );
    }

    /**
     * Testing toString() method.
     */
    @Test
    public void testingToString() {
        assertEquals("[Vehicle: TRUCK, Location: (10, 10)]", myTruck.toString(),
                "The toString was not formated properly.");
    }

    /**
     * Test canPass Method.
     */
    @Test
    public void testCanPass() {

        // Truck can only move on LIGHT, STREET, and CROSSWALK terrain.
        // Truck can travel through any LIGHT on any light condition.
        // Truck can travel through any STREET on any light condition.
        // Truck can only travel through GREEN and YELLOW light conditions through CROSSWALKS.

        final List<Terrain> validTerrain = new ArrayList<>();

        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);

        for (final Terrain destinationTerrain : Terrain.values()) {
            // Test each terrain type as a destination.

            for (final Light currentLightCondition : Light.values()) {
                //Test each light condition with each terrain type.

                if (destinationTerrain == Terrain.CROSSWALK) {
                    // Truck can pass CROSSWALK when light is yellow or green
                    if (currentLightCondition == Light.YELLOW
                           || currentLightCondition == Light.GREEN) {
                        assertTrue(myTruck.canPass(destinationTerrain, currentLightCondition),
                               "Truck should be able to pass CROSSWALK "
                                        + "when the light is GREEN or YELLOW!");
                    } else if (currentLightCondition == Light.RED) {
                        assertFalse(myTruck.canPass(destinationTerrain,
                                currentLightCondition),
                                "Truck should have stopped at CROSSWALK "
                                + "when the light is RED");
                    }
                } else if (destinationTerrain == Terrain.STREET) {
                    //Truck can pass STREET under any light condition
                    assertTrue(myTruck.canPass(destinationTerrain, currentLightCondition),
                            "Truck should be able to pass STREET "
                                    + "under any light condition");
                } else if (destinationTerrain == Terrain.LIGHT) {
                    //Truck can pass LIGHT under any light condition
                    assertTrue(myTruck.canPass(destinationTerrain, currentLightCondition),
                            "Truck should be able to pass LIGHT "
                                    + "under any light condition");
                } else if (!validTerrain.contains(destinationTerrain)) {
                    //Truck should not pass wall, grass, or trail under any light condition
                    assertFalse(myTruck.canPass(destinationTerrain, currentLightCondition),
                            String.format("Truck should have been able to pass "
                                    + destinationTerrain + " when the light is "
                                    + currentLightCondition + "!"));
                }
            }
        }
    }

    /**
     * Testing if the Truck reverses at anytime with more than 1 valid direction.
     */
    @Test
    public void testChooseDirectionAndNotReverse() {

        //Initializes the previousDirection to the first direction passed from constructor.
        Direction previousDirection = myTruck.getDirection();

        while (myCounter <= TRIES_FOR_RANDOMNESS) {
            myCounter++;
            final Direction d = myTruck.chooseDirection(NEIGHBORS);
            switch (d) {
                case Direction.NORTH -> mySeenNorth = true;
                case Direction.EAST -> mySeenEast = true;
                case Direction.WEST -> mySeenWest = true;
                case Direction.SOUTH -> mySeenSouth = true;
                default -> throw new IllegalArgumentException("Invalid direction passed!");

            }

            // Checks if the reverse of the previous direction is the current randomly chosen
            // direction (this indicates that the vehicle reversed).
            assertNotEquals(previousDirection.reverse(), d,
                    "Your Truck reversed when other options were available!");

            //Sets the previous direction as the current direction
            previousDirection = d;
        }
        //Checks if the Atv has choosen a direction randomly.
        assertTrue(mySeenNorth && mySeenEast && mySeenWest && mySeenSouth,
                    "Truck is not randomly choosing a direction!");
    }

    /**
     * Testing for reverse when its the only option.
     */
    @Test
    public void testChooseDirectionAndReverse() {
        //Clears the Map for new values
        NEIGHBORS.clear();
        while (myCounter <= TRIES_FOR_RANDOMNESS) {
            myCounter++;

            NEIGHBORS.put(Direction.NORTH, Terrain.WALL);
            NEIGHBORS.put(Direction.EAST, Terrain.GRASS);
            NEIGHBORS.put(Direction.WEST, Terrain.TRAIL);
            NEIGHBORS.put(Direction.SOUTH, Terrain.STREET);

            //Checks if the Truck went south since its the only valid direction.
            assertEquals(Direction.SOUTH, myTruck.chooseDirection(NEIGHBORS),
                    "The Truck did not reverse when it was the only option");
            myTruck.reset();
        }
    }

     /**
     * Testing for only 1 valid direction besides reverse.
     */
    @Test
    public void testChooseOnlyValidDirection() {
        //Clears the Map for new values
        NEIGHBORS.clear();
        NEIGHBORS.put(Direction.NORTH, Terrain.WALL);
        NEIGHBORS.put(Direction.EAST, Terrain.GRASS);
        NEIGHBORS.put(Direction.WEST, Terrain.STREET);
        NEIGHBORS.put(Direction.SOUTH, Terrain.STREET);
        Direction previousDirection = myTruck.getDirection();

        while (myCounter <= TRIES_FOR_RANDOMNESS) {
            myCounter++;

            final Direction d = myTruck.chooseDirection(NEIGHBORS);
            //Checks if the Truck went west since its the only valid direction.
            assertNotEquals(previousDirection.reverse(), d,
                    "The Truck should not have reversed!");

            previousDirection = d;
        }
    }
}
