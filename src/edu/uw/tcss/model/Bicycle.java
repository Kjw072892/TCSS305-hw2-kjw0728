package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Bicycle class.
 *
 * @author Kassie Whitney
 * @version 1.31.25
 */

public class Bicycle extends AbstractVehicle {


    /**
     * The death time for the Bicycle.
     */
    private static final int BICYCLE_DEATH_TIME = 35;


    /**
     *
     * Constructor for Bicyle class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction coordinate
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        boolean canMove = false;

        // Ensures bike is on the street and only passes through green light
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT
                && theLight == Light.GREEN) {
            canMove = true;

        //Ensures bike can go on trails
        } else if (theTerrain == Terrain.TRAIL) {
            canMove = true;

        //Ensures bike only crosses the crosswalk when the light is green
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            canMove = true;
        }

        return canMove;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final Terrain trail = Terrain.TRAIL;
        final Terrain crossWalk = Terrain.CROSSWALK;
        final Terrain street = Terrain.STREET;
        final Terrain light = Terrain.LIGHT;
        final Terrain grass = Terrain.GRASS;
        final Terrain wall = Terrain.WALL;

        Direction currentDirection = getDirection();

        //Tells the bicycle to go into the trail
        if (theNeighbors.get(currentDirection) != trail
                && theNeighbors.get(currentDirection.left()) == trail) {
            currentDirection = currentDirection.left();
        } else if (theNeighbors.get(currentDirection) != trail
                && theNeighbors.get(currentDirection.right()) == trail) {
            currentDirection = currentDirection.right();
        }

        //Tells the bicycle to to favor turning left if straight is not an option
        if (theNeighbors.get(currentDirection) == wall
                || theNeighbors.get(currentDirection) == grass) {
            currentDirection = currentDirection.left();
        } else {
            currentDirection.reverse();
        }

        if (!isAlive()) {
            currentDirection = Direction.random();
        }

        return currentDirection;
    }


    @Override
    public int getDeathTime() {
        return BICYCLE_DEATH_TIME;
    }



}
