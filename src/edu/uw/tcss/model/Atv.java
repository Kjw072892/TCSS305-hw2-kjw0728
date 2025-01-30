package edu.uw.tcss.model;

import java.util.Map;

/**
 * An ATV class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public final class Atv extends AbstractVehicle {

    /**
     * Constructor for the Atv Class.
     *
     * @param theX the starting x coordinate of the ATV
     * @param theY the starting y cooridnate of the ATV
     * @param theDir the starting Dirrection of the ATV
     *               (randomly generated from them direction Enum)
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super();

        this.setX(theX);
        this.setY(theY);
        this.setDirection(theDir);

    }



    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        return !theTerrain.equals(Terrain.WALL);
    }


    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        System.out.println("(" + this.getX() + ", " + this.getY() + ")");
        System.out.println(theNeighbors);

        return Direction.random();
    }


    @Override
    public void collide(final Vehicle theOther) {

    }




}
