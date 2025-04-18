package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A Truck class.
 *
 * @author Kassie Whitney
 * @version 1.31.25
 */
public class Truck extends AbstractVehicle {


    /**
     * Stores the previously called direction.
     */
    private Direction myPreviousDirection;

    /**
     *
     * Constructor for Truck class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y cooridnate
     * @param theDir the starting Dirrection
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

        myPreviousDirection = theDir;

    }
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        boolean canMove = false;

        if (theTerrain == Terrain.STREET
                || theTerrain == Terrain.LIGHT
                || theTerrain == Terrain.CROSSWALK) {

            if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT) {
                canMove = true;

            }

            if (theTerrain == Terrain.CROSSWALK && theLight != Light.RED) {

                canMove = true;
            }
        }

        return canMove;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final Terrain wall = Terrain.WALL;
        final Terrain street = Terrain.STREET;
        final Terrain grass = Terrain.GRASS;
        final Terrain lights = Terrain.LIGHT;
        final Terrain crosswalks = Terrain.CROSSWALK;
        final Terrain trails = Terrain.TRAIL;
        final List<Direction> directionList = new ArrayList<>();

        // Filters out areas where the truck can't go and adds directions to array list
        for (final Direction direction : Direction.values()) {
            if (theNeighbors.get(direction) == street
                    || theNeighbors.get(direction) == lights
                    || theNeighbors.get(direction) == crosswalks) {

                directionList.add(direction);
            }
        }

        // Removes the direction opposite to its previous direction.
        // Ensures the truck does not go backwards if other options are avialable.
        if (directionList.size() > 1) {
            directionList.remove(myPreviousDirection.reverse());
        }

        //Randomly chooses a valid direction from the list
        final Direction currentDirection =
                directionList.get(randomIntGenerator(directionList.size()));

        // Stores the current direction in a the field to reference when the method is called
        myPreviousDirection = currentDirection;

        return currentDirection;
    }

    @Override
    public int getDeathTime() {
        return 0;
    }


    /**
     *
     * Random Integer generator starting from 0 to a specified integer passed in theUpperBound
     * parameter.
     *
     * @param theUpperBound the highest number to generate a random integer from 0.
     * @return a randomly generated integer between 0 and theUpperBound parameter.
     */
    private int randomIntGenerator(final int theUpperBound) {
        final Random random = new Random();

        return random.nextInt(theUpperBound);
    }
}
