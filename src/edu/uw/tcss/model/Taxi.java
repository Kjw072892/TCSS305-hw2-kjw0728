package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Taxi class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */

public class Taxi extends AbstractVehicle {


    /**
     * Stores previously called directions.
     */
    private Direction myPreviousDirection;

    /**
     * Constructor for Taxi.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

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

}