package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Car class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public final class Car extends AbstractVehicle {

    /**
     * The death time for the Car.
     */
    private static final int CAR_DEATH_TIME = 15;

    /**
     * Stores the previously called direction.
     */
    private Direction myPreviousDirection;

    /**
     * Stores the starting x value of the Car at program start up.
     */
    private final int myDefaultX;

    /**
     * Stores the starting y value of the Car at program start up.
     */
    private final int myDefaultY;

    /**
     * Stores the Starting direction (NORTH, EAST, SOUTH, WEST) of the Car at program start up
     */
    private final Direction myDefaultDirection;


    /**
     *
     *Constructor for Car class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction coordinate
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
        myDefaultX = theX;
        myDefaultY = theY;
        myDefaultDirection = theDir;
        myPreviousDirection = theDir;
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return false;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return null;
    }

    @Override
    public int getDeathTime() {
        return 0;
    }

    @Override
    public void reset() {

    }
}
