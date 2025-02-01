package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Human Class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public final class Human extends AbstractVehicle {

     /**
     * Stores the previously called direction.
     */
    private Direction myPreviousDirection = getDirection();

    /**
     * Stores the starting x value of the Human at program start up.
     */
    private final int myDefaultX;

    /**
     * Stores the starting y value of the Human at program start up.
     */
    private final int myDefaultY;

    /**
     * Stores the Starting direction (NORTH, EAST, SOUTH, WEST) of the Human at program
     * start up
     */
    private final Direction myDefaultDirection;

    /**
     * Stores the vehicles alive value. True if alive, false if not alive.
     */
    private boolean myIsAlive;

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
