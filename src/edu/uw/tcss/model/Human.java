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
     *
     * Constructor for Human class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction coordinate
     */
    public Human(final int theX, final int theY, final Direction theDir) {
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
        return Direction.WEST;
    }

    @Override
    public void collide(final Vehicle theOther) {

    }

    @Override
    public int getDeathTime() {
        return 0;
    }

    @Override
    public void reset() {

    }
}
