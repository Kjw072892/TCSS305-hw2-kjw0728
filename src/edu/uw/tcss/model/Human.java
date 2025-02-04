package edu.uw.tcss.model;

import java.awt.image.CropImageFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A Human Class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public class Human extends AbstractVehicle {
    /**
     * Stores the death count for human
     */
    private static final int DEATH_COUNT = 45;

    /**
     * Stores the previously called direction.
     */
    private Direction myPreviousDirection;

    /**
     *
     * Constructor for Human class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction coordinate
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

        myPreviousDirection = theDir;

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        boolean canMove = false;

        if (isValid(theTerrain)) {
            if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
                canMove = true;
            }
            if (theTerrain != Terrain.CROSSWALK && theLight == Light.GREEN
                    || theLight == Light.YELLOW || theLight == Light.RED) {
                canMove = true;
            }
        }

        return canMove;
    }

    private boolean isValid(final Terrain theTerrain) {
        return theTerrain == Terrain.CROSSWALK
                || theTerrain == Terrain.GRASS;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        Direction currentDirection = myPreviousDirection;
        final List<Direction> directionList = new ArrayList<>();

        for (final Direction direction : Direction.values()) {
            if (theNeighbors.get(direction) == Terrain.GRASS
                    || theNeighbors.get(direction) == Terrain.CROSSWALK
                    || theNeighbors.get(direction) == Terrain.TRAIL) {
                directionList.add(direction);
            }
        }
        //TODO: The human is reversing when near a crosswalk
        //TODO: The human is not reversing when its the only choice
        //TODO: The human reverse direction unncessarily

        for (final Direction direction : directionList) {

            if (theNeighbors.get(direction) == Terrain.CROSSWALK) {
                currentDirection = direction;
                break;
            } else {
                currentDirection = directionList.get(randomIntGenerator(directionList.size()));
            }
        }

        try {
            if (currentDirection.reverse() == myPreviousDirection
                    && theNeighbors.get(currentDirection) != Terrain.CROSSWALK) {
                currentDirection = directionList.get(randomIntGenerator(directionList.size()));
            }

        } catch (final IllegalArgumentException illegalArgumentException) {
            currentDirection = currentDirection.reverse();
        }


        myPreviousDirection = currentDirection;


        return currentDirection;
    }

    @Override
    public int getDeathTime() {
        return DEATH_COUNT;
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
