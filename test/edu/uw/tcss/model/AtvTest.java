package edu.uw.tcss.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;





/**
 * Unit testing for ATV class.
 *
 * @author Kassie Whitney (kjw0728@uw.edu)
 * @version 02.09.25
 */
public class AtvTest {

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
    private static final int DEATH_TIME = 25;

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
    private  final Atv myAtv = new Atv(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);


    /**
     * Creates a map.
     */
    @BeforeAll
    public static void createNewMap() {

        NEIGHBORS.put(Direction.NORTH, Terrain.GRASS);
        NEIGHBORS.put(Direction.EAST, Terrain.LIGHT);
        NEIGHBORS.put(Direction.WEST, Terrain.TRAIL);
        NEIGHBORS.put(Direction.SOUTH, Terrain.STREET);
    }

    /**
     * Test method for ATV constructor.
     */
    @Test
    public void testATVConstructor() {

        assertAll("Testing the constructor for the truck class",
                () -> assertEquals(FIXTURE_X_1,
                        myAtv.getX(),
                        "Atv x coordinate was not initialized properly!"),
                () -> assertEquals(FIXTURE_Y_1,
                        myAtv.getY(),
                        "Atv y cooridinate was not initialized properly!"),
                () -> assertEquals(Direction.NORTH,
                        myAtv.getDirection(),
                        "Atv direction was not initialized properly!")
        );
    }

    /**
     * Testing getDeathTime().
     */
    @Test
    public void testGetDeathTime() {
        assertEquals(DEATH_TIME, myAtv.getDeathTime(),
                String.format("Death Time is incorrect, should be "
                        + "%d instead of %d!", DEATH_TIME, myAtv.getDeathTime()));
    }

    /**
     * Test getImageFileName() method (ALIVE).
     */
    @Test
    public void testAtvGetImageFileNameAlive() {
        final String alive = "atv.gif";
        assertEquals(alive, myAtv.getImageFileName(),
                "The file name for an alive Atv incorrect!");
    }

    /**
     * Test getImageFileName() method (DEAD).
     */
    @Test
    public void testGetImageFileNameDead() {
        final Truck t = new Truck(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);
        myAtv.collide(t);
        final String dead = "atv_dead.gif";

        assertEquals(dead, myAtv.getImageFileName(),
                "The file name for a dead Atv is incorrect!");
    }

    /**
     * Testing setters.
     */
    @Test
    public void testATVSetters() {
        myAtv.setX(0);
        myAtv.setY(0);
        myAtv.setDirection(Direction.WEST);

        assertAll("Testing the Trucks setters",
                () -> assertNotEquals(FIXTURE_X_1,
                        myAtv.getX(),
                        "SetX() is not setting new X-coordinates properly"),
                () -> assertNotEquals(FIXTURE_Y_1,
                        myAtv.getY(),
                        "SetY() is nott setting new Y-coordinates properly"),
                () -> assertNotEquals(Direction.NORTH,
                        myAtv.getDirection(),
                        "SetDiretion() is not setting the new direction properly")
        );
    }

    /**
     * Test method for atv collision.
     */
    @Test
    public void testATVCollisionWithTruck() {
        final Truck t = new Truck(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

        myAtv.collide(t);

        assertFalse(myAtv.isAlive(),
                    "The atv did not process the collision correctly");
    }


    /**
     * Test method for isAlive Method.
     */
    @Test
    public void testAtvIsAlive() {
        final Truck t = new Truck(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);
        final Human h = new Human(FIXTURE_X_1, FIXTURE_Y_1, Direction.NORTH);

        myAtv.collide(h);
        assertTrue(myAtv.isAlive(), "The ATV should not have died on collision!");

        myAtv.collide(t);
        assertFalse(myAtv.isAlive(), "The ATV did not die on collision");

    }

    /**
     * Testing the reset() method.
     */
    @Test
    public void testReset() {
        myAtv.setX(0);
        myAtv.setY(0);
        myAtv.setDirection(Direction.EAST);
        myAtv.reset();

        assertAll("Testing the Atv's reset method",
                () -> assertEquals(FIXTURE_X_1,
                        myAtv.getX(),
                        "The reset method did not default to original x-Coordinate!"),
                () -> assertEquals(FIXTURE_Y_1,
                        myAtv.getY(),
                        "The reset method did not default to original y-coordinate!"),
                () -> assertEquals(Direction.NORTH,
                        myAtv.getDirection(),
                        "The reset method did not default "
                                + "to original starting direction!")
        );
    }

    /**
     * Testing toString() method.
     */
    @Test
    public void testingToString() {
        assertEquals("[Vehicle: ATV, Location: (10, 10)]", myAtv.toString(),
                "The toString was not formated properly.");
    }

    /**
     * Test canPass Method.
     */
    @Test
    public void testCanPass() {

        // ATV can move anywhere on the map except the walls
        // ATV does not have any stopping requirements in terms of light colors
        // ATV also should reverse if no other options are available

        final List<Terrain> validTerrain = new ArrayList<>();

        validTerrain.add(Terrain.GRASS);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.TRAIL);

        for (final Terrain destinationTerrain : Terrain.values()) {
            // test each terrain type as a destination
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.CROSSWALK) {
                    // ATV can pass CROSSWALK under any light condition
                    assertTrue(myAtv.canPass(destinationTerrain, currentLightCondition),
                            "ATV should be able to pass "
                                    + "CROSSWALK with any light condition");
                } else if (destinationTerrain == Terrain.GRASS) {
                    // ATV can pass GRASS under any light condition
                    assertTrue(myAtv.canPass(destinationTerrain, currentLightCondition),
                            "ATV should be able to pass "
                                    + "GRASS under any light condition");
                } else if (destinationTerrain == Terrain.STREET) {
                    //ATV can pass STREET under any light condition
                    assertTrue(myAtv.canPass(destinationTerrain, currentLightCondition),
                            "ATV should be able to pass STREET "
                                    + "under any light condition");
                } else if (destinationTerrain == Terrain.LIGHT) {
                    //ATV can pass LIGHT under any light condition
                    assertTrue(myAtv.canPass(destinationTerrain, currentLightCondition),
                            "ATV should be able to pass LIGHT "
                                    + "under any light condition");
                } else if (destinationTerrain == Terrain.TRAIL) {
                    //ATV can pass TRAIL under any light condition
                    assertTrue(myAtv.canPass(destinationTerrain, currentLightCondition),
                            "ATV should be able to pass TRAIL "
                                    + "under any light condition");
                } else if (!validTerrain.contains(destinationTerrain)) {
                    //ATV should not pass wall under any light condition
                    assertFalse(myAtv.canPass(destinationTerrain, currentLightCondition),
                            "ATV should NOT be able to pass WALL "
                                    + "under any light condition");
                }
            }
        }
    }

    /**
     * Testing if the ATV reverses at anytime with more than 1 valid direction.
     */
    @Test
    public void testChooseDirectionAndNotReverse() {

        //Initializes the previousDirection to the first direction passed from constructor.
        Direction previousDirection = myAtv.getDirection();

        while (myCounter <= TRIES_FOR_RANDOMNESS) {
            myCounter++;
            final Direction d = myAtv.chooseDirection(NEIGHBORS);
            switch (d) {
                case Direction.NORTH -> mySeenNorth = true;
                case Direction.EAST -> mySeenEast = true;
                case Direction.WEST -> mySeenWest = true;
                case Direction.SOUTH -> mySeenSouth = true;
                default -> throw new IllegalArgumentException("Invalid direction passed!");
                //Default is not necessary.
            }

            // Checks if the reverse of the previous direction is the current random
            // direction (this indicates that the vehicle reversed).
            assertNotEquals(previousDirection.reverse(), d,
                    "Your Atv reversed when other valid options were available!");

            //Sets the previous direction as the current direction
            previousDirection = d;
        }

        //Checks if the Atv has choosen a direction randomly.
        assertTrue(mySeenNorth && mySeenEast && mySeenWest && mySeenSouth,
                    "Atv is not randomly choosing a direction!");
    }

    /**
     * Testing for reverse when its the only option.
     */
    @Test
    public void testChooseDirectionAndReverse() {
        NEIGHBORS.clear();
        while (myCounter <= TRIES_FOR_RANDOMNESS) {
            myCounter++;

            NEIGHBORS.put(Direction.NORTH, Terrain.WALL);
            NEIGHBORS.put(Direction.EAST, Terrain.WALL);
            NEIGHBORS.put(Direction.WEST, Terrain.WALL);
            NEIGHBORS.put(Direction.SOUTH, Terrain.STREET);

            assertEquals(Direction.SOUTH, myAtv.chooseDirection(NEIGHBORS),
                    "The ATV did not reverse when it was the only option");
            myAtv.reset();
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

        Direction previousDirection = myAtv.getDirection();

        while (myCounter <= TRIES_FOR_RANDOMNESS) {
            myCounter++;

            final Direction d = myAtv.chooseDirection(NEIGHBORS);

            //Checks if the Truck went west since its the only valid direction.
            assertNotEquals(previousDirection.reverse(), d,
                    "The Truck should not have reversed!");

            //Sets the previous direction to the current direction.
            previousDirection = d;
        }
    }

}
