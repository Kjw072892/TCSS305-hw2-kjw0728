package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Bicycle class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public final class Bicycle extends AbstractVehicle {

    /**
     *
     * Constructor for Bicyle class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction coordinate
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super();
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
    public void collide(final Vehicle theOther) {

    }
}
