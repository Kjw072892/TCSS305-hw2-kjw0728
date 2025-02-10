package edu.uw.tcss.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Car class.
 *
 * @author Kassie Whitney (kjw9728@uw.edu)
 * @version 02.09.25
 */
public class CarTest {

    /**
     * The number of times to repeat a test to have a high probability that all random
     * possiblites have been explored.
     */
    private static final int NUMBER_OF_TRIES = 50;

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
    private static final int DEATH_TIME = 15;

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
    private  final Car myCar = new Car(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

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
     * Test method for Car constructor.
     */
    @Test
    public void testCarConstructor() {

        assertAll("Testing the constructor for the Car class",
                () -> assertEquals(FIXTURE_X_1,
                        myCar.getX(),
                        "Car x coordinate was not initialized properly!"),
                () -> assertEquals(FIXTURE_Y_1,
                        myCar.getY(),
                        "Car y cooridinate was not initialized properly!"),
                () -> assertEquals(Direction.NORTH,
                        myCar.getDirection(),
                        "Car direction was not initialized properly!")
        );
    }

    /**
     * Test getImageFileName() method (ALIVE).
     */
    @Test
    private void testCarGetImageFileNameAlive() {
        final String alive = "car.gif";
        assertEquals(alive, myCar.getImageFileName(),
                "The file name for an alive Car incorrect!");

        assertNotEquals("car_dead.gif", myCar.getImageFileName(),
                "The Car should not have an imageFileName for its death, "
                        + "because the truck cant die!");
    }



    /**
     * Testing getDeathTime().
     */
    @Test
    public void testGetDeathTime() {
        assertEquals(DEATH_TIME, myCar.getDeathTime(),
                String.format("Death Time is incorrect, should be "
                        + "%d instead of %d!", DEATH_TIME, myCar.getDeathTime()));
    }


    /**
     * Testing setters.
     */
    @Test
    private void testATVSetters() {
        myCar.setX(0);
        myCar.setY(0);
        myCar.setDirection(Direction.WEST);

        assertAll("Testing the Car setters",
                () -> assertNotEquals(FIXTURE_X_1,
                        myCar.getX(),
                        "SetX() is not setting new X-coordinates properly"),
                () -> assertNotEquals(FIXTURE_Y_1,
                        myCar.getY(),
                        "SetY() is nott setting new Y-coordinates properly"),
                () -> assertNotEquals(Direction.NORTH,
                        myCar.getDirection(),
                        "SetDiretion() is not setting the new direction properly")
        );
    }

    /**
     * Test method for Car collision.
     */
    @Test
    private void testCarCollisionWithTruck() {
        final Truck t = new Truck(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

        myCar.collide(t);

        assertFalse(myCar.isAlive(),
                "The Car did not process the collision correctly!");
    }

    /**
     * Test method for isAlive Method.
     */
    @Test
    public void testCarIsAlive() {
        final Truck t = new Truck(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);
        final Human h = new Human(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

        myCar.collide(h);
        assertTrue(myCar.isAlive(), "The Car should not have died on collision!");

        myCar.collide(t);
        assertFalse(myCar.isAlive(), "The Car should have died on collision!");

    }

    /**
     * Testing the reset() method.
     */
    @Test
    public void testReset() {
        myCar.setX(0);
        myCar.setY(0);
        myCar.setDirection(Direction.EAST);
        myCar.reset();

        assertAll("Testing the Trucks reset method",
                () -> assertEquals(FIXTURE_X_1,
                        myCar.getX(),
                        "The reset method did not default to original x-Coordinate!"),
                () -> assertEquals(FIXTURE_Y_1,
                        myCar.getY(),
                        "The reset method did not default to original y-coordinate!"),
                () -> assertEquals(Direction.NORTH,
                        myCar.getDirection(),
                        "The reset method did not default "
                                + "to original starting direction!")
        );
    }

    /**
     * Testing toString() method.
     */
    @Test
    public void testingToString() {
        assertEquals("[Vehicle: CAR, Location: (10, 10)]", myCar.toString(),
                "The toString was not formated properly.");
    }

    /**
     * Test canPass Method.
     */
    @Test
    public void testCanPass() {

        // Cars can only travel on STREETS and through LIGHTS and CROSSWALKS.
        // Cars perferrs to drive straight ahead on the STREET if it can.
        // Cars perfers to turn left if cant go straight.
        // Cars perfers to turn right if cant go straight or left.
        // Cars will reverse if cant go straight, left, or right.
        // Cars will stop at RED LIGHTS if its ahead and does not move.
        // Does not turn around to avoid LIGHTS.
        // When LIGHT is GREEN Cars will continue straight if it can.
        // Cars ignores YELLOW and GREEN lights.
        // Cars stops for RED and YELLOW CROSSWALK lights.
        // Cars drive through GREEN CROSSWALK lights

        final List<Terrain> validTerrain = new ArrayList<>();

        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);

        for (final Terrain destinationTerrain : Terrain.values()) {
            // Test each terrain type as a destination.

            for (final Light currentLightCondition : Light.values()) {
                //Test each light condition with each terrain type.

                if (destinationTerrain == Terrain.STREET) {
                    assertTrue(myCar.canPass(destinationTerrain, currentLightCondition),
                            "Car should pass street with any light color!");
                } else if (destinationTerrain == Terrain.LIGHT) {
                    if (currentLightCondition != Light.RED) {
                        assertTrue(myCar.canPass(destinationTerrain, currentLightCondition),
                                "Car should pass LIGHT on YELLOW or GREEN!");
                    } else {
                        assertFalse(myCar.canPass(destinationTerrain, currentLightCondition),
                                "Car should have stopped at RED lights when at LIGHTS!");
                    }
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    if (currentLightCondition == Light.GREEN) {
                        assertTrue(myCar.canPass(destinationTerrain, currentLightCondition),
                            "Car should pass CROSSWALK on GREEN light!");
                    } else {
                        assertFalse(myCar.canPass(destinationTerrain, currentLightCondition),
                                "Car should have stopped at YELLOW or RED lights "
                                        + "when at CROSSWALK!");
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse(myCar.canPass(destinationTerrain, currentLightCondition),
                            "Car should not have passed " + destinationTerrain + " on "
                            + "light condition " + currentLightCondition);
                }

            }
        }
    }

    /**
     * Testing if the Car reverses at anytime with more than 1 valid direction.
     */
    @Test
    public void testChooseDirectionAndNotReverse() {

        //Initializes the previousDirection to the first direction passed from constructor.
        Direction previousDirection = myCar.getDirection();

        final int tenCounts = 10;
        final int thirtyCounts = 30;


        while (myCounter <= NUMBER_OF_TRIES) {

            myCounter++;

            // Checks if the reverse of the previous direction is the current randomly chosen
            // direction (this indicates that the vehicle reversed).
            assertNotEquals(previousDirection.reverse(), myCar.chooseDirection(NEIGHBORS),
                    "Your Car reversed when other options were available!");


            if (myCounter < tenCounts) {
                assertEquals(Direction.NORTH, myCar.chooseDirection(NEIGHBORS),
                        "Your car should have gone straight!");

            } else if (myCounter == tenCounts) {
                NEIGHBORS.replace(Direction.NORTH, Terrain.GRASS);
                assertEquals(Direction.WEST, myCar.chooseDirection(NEIGHBORS),
                        "Your car should have turned LEFT!");

            } else if (myCounter == thirtyCounts) {
                NEIGHBORS.replace(Direction.WEST, Terrain.TRAIL);
                assertEquals(Direction.EAST, myCar.chooseDirection(NEIGHBORS),
                      "Your car should have turned RIGHT going EAST!");

            }
             //Sets the previous direction as the current direction
            previousDirection = myCar.chooseDirection(NEIGHBORS);

        }

    }

    /**
     * Testing for reverse when its the only option.
     */
    @Test
    public void testChooseDirectionAndReverse() {
        //Clears the Map for new values
        NEIGHBORS.clear();
        while (myCounter <= NUMBER_OF_TRIES) {
            myCounter++;

            NEIGHBORS.put(Direction.NORTH, Terrain.WALL);
            NEIGHBORS.put(Direction.EAST, Terrain.GRASS);
            NEIGHBORS.put(Direction.WEST, Terrain.TRAIL);
            NEIGHBORS.put(Direction.SOUTH, Terrain.STREET);

            //Checks if the Truck went south since its the only valid direction.
            assertEquals(Direction.SOUTH, myCar.chooseDirection(NEIGHBORS),
                    "The Truck did not reverse when it was the only option");
            myCar.reset();
        }
    }
}
