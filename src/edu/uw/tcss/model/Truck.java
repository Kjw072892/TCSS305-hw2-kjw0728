package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Truck class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public final class Truck extends AbstractVehicle {

    /**
     *
     * Constructor for Truck class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y cooridnate
     * @param theDir the starting Dirrection
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super();

        this.setX(theX);
        this.setY(theY);
        this.setDirection(theDir);

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
