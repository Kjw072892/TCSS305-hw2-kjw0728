/*
 * TCSS 305 - Road Rage
 */

package edu.uw.tcss.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for class Human.
 *
 * @author Charles Bryan (cfb3@uw.edu)
 * @author Alan Fowler (acfowler@uw.edu)
 * @version Autumn 2023
 */
public class HumanTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    private static final int FIXTURE_X = 10;

    private static final int FIXTURE_Y = 10;

    private static final int FIXTURE_DEATH_TIME = 45;

    /** Test method for Human constructor. */
    @Test
    public void testHumanConstructor() {
        final Human h = new Human(FIXTURE_X, FIXTURE_Y, Direction.NORTH);

        assertEquals(FIXTURE_X, h.getX(),
                "Human x coordinate not initialized correctly!");
        assertEquals(FIXTURE_Y, h.getY(),
                "Human y coordinate not initialized correctly!");
        assertEquals(Direction.NORTH, h.getDirection(),
                "Human direction not initialized correctly!");
        assertEquals(FIXTURE_DEATH_TIME, h.getDeathTime(),
                "Human death time not initialized correctly!");
        assertTrue(h.isAlive(),
                "Human isAlive() fails initially!");
    }

    /** Test method for Human setters. */
    @Test
    public void testHumanSetters() {
        final Human h = new Human(FIXTURE_X, FIXTURE_Y, Direction.NORTH);

        final int changedX = 12;
        final int changedY = 13;

        h.setX(changedX);
        assertEquals(changedX, h.getX(),
                "Human setX failed!");

        h.setY(changedY);
        assertEquals(changedY, h.getY(),
                "Human setY failed!");

        h.setDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, h.getDirection(),
                "Human setDirection failed!");
    }

    /**
     * Test method for {@link Human#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {

        // Humans can move to GRASS or to CROSSWALKS
        // so we need to test both of those conditions

        // Humans should NOT choose to move to other terrain types
        // so we need to test that Humans never move to other terrain types

        // Humans should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also

        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.GRASS);
        validTerrain.add(Terrain.CROSSWALK);

        final Human human = new Human(0, 0, Direction.NORTH);
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.GRASS) {

                    // humans can pass GRASS under any light condition
                    assertTrue(human.canPass(destinationTerrain, currentLightCondition),
                            "Human should be able to pass GRASS"
                                    + ", with light " + currentLightCondition);
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    // humans can pass CROSSWALK
                    // if the light is YELLOW or RED but not GREEN

                    if (currentLightCondition == Light.GREEN) {
                        assertFalse(human.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Human should NOT be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    } else { // light is yellow or red
                        assertTrue(human.canPass(destinationTerrain,
                                        currentLightCondition),
                                "Human should be able to pass " + destinationTerrain
                                        + ", with light " + currentLightCondition);
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {

                    assertFalse(human.canPass(destinationTerrain, currentLightCondition),
                            "Human should NOT be able to pass " + destinationTerrain
                                    + ", with light " + currentLightCondition);
                }
            }
        }
    }

    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionSurroundedByGrass() {
        final Map<Direction, Terrain> neighbors = new HashMap<>();
        neighbors.put(Direction.WEST, Terrain.GRASS);
        neighbors.put(Direction.NORTH, Terrain.GRASS);
        neighbors.put(Direction.EAST, Terrain.GRASS);
        neighbors.put(Direction.SOUTH, Terrain.GRASS);

        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;

        final Human human = new Human(0, 0, Direction.NORTH);

        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {

            final Direction d = human.chooseDirection(neighbors);

            seenNorth = seenNorth || d == Direction.NORTH;
            seenSouth = seenSouth || d == Direction.SOUTH;
            seenWest = seenWest || d == Direction.WEST;
            seenEast = seenEast || d == Direction.EAST;

        }

        assertTrue(seenWest && seenNorth && seenEast,
                "Human chooseDirection() fails to select randomly "
                        + "among all possible valid choices!");

        assertFalse(seenSouth,
                "Human chooseDirection() reversed direction when not necessary!");
    }


    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnGrassMustReverse() {

        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.GRASS && t != Terrain.CROSSWALK) {

                final Map<Direction, Terrain> neighbors = new HashMap<>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.GRASS);

                final Human human = new Human(0, 0, Direction.NORTH);

                // the Human must reverse and go SOUTH
                assertEquals(Direction.SOUTH, human.chooseDirection(neighbors),
                        "Human chooseDirection() failed "
                                + "when reverse was the only valid choice!");
            }

        }
    }


    /**
     * Test method for {@link Human#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnGrassNearCrosswalk() {

        // If a Human is next to a crosswalk it should always choose to face
        // toward the crosswalk. Except when that would cause the human to reverse
        // direction. A Human will only reverse direction if no other valid option exits.
        // So, test all possible conditions.

        final Human human = new Human(0, 0, Direction.NORTH);

        final Map<Direction, Terrain> neighbors = new HashMap<>();
        neighbors.put(Direction.WEST, Terrain.CROSSWALK);
        neighbors.put(Direction.NORTH, Terrain.GRASS);
        neighbors.put(Direction.EAST, Terrain.GRASS);
        neighbors.put(Direction.SOUTH, Terrain.GRASS);

        for (final Direction d : Direction.values()) {
            human.setDirection(d);


            if (d == Direction.EAST) {
                assertNotEquals(Direction.WEST, human.chooseDirection(neighbors),
                        "A human near a crosswalk and facing " + d
                                + " should not reverse direction!");

            } else {
                assertEquals(Direction.WEST, human.chooseDirection(neighbors),
                        "A human near a crosswalk and facing " + d
                                + " chose a wrong direction!");
            }
        }
    }

}
