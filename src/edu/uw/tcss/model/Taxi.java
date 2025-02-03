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
     * constant denoting the max number of cycles passed
     */
    private static final int MAX_CLOCK_COUNT = 3;

    /**
     * the Taxis death time
     */
    private static final int TAXI_DEATH_TIME = 15;

    /**
     * variable to store the number of cycles that passed
     */
    private int myClockCount;




    /**
     * Constructor for Taxi.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean canMove;

        if (theTerrain == Terrain.STREET) {
            myClockCount = 0;
            canMove = true;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            canMove = true;
        } else {
            canMove = theTerrain == Terrain.LIGHT
                    && theLight == Light.GREEN || theLight == Light.YELLOW;
        }

        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            myClockCount++;
            canMove = myClockCount == MAX_CLOCK_COUNT;
        }
        return canMove;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final Terrain street = Terrain.STREET;
        final Terrain crosswalk = Terrain.CROSSWALK;
        final Terrain wall = Terrain.WALL;
        final Terrain grass = Terrain.GRASS;
        final Terrain trail = Terrain.TRAIL;
        final Terrain light = Terrain.LIGHT;

        Direction currentDirection = getDirection();

        if (theNeighbors.get(getDirection()) != street) {

            if (theNeighbors.get(currentDirection.left()) == street
                     || theNeighbors.get(currentDirection.left()) == light
                     || theNeighbors.get(currentDirection.left()) == crosswalk) {
                currentDirection = currentDirection.left();
            }

            if (theNeighbors.get(currentDirection.right()) == street
                     || theNeighbors.get(currentDirection.right()) == light
                     || theNeighbors.get(currentDirection.right()) == crosswalk) {
                currentDirection = currentDirection.right();
            }

            if (theNeighbors.get(currentDirection) == wall
                     || theNeighbors.get(currentDirection) == grass) {
                currentDirection = currentDirection.reverse();
            }
        } else {
            currentDirection = getDirection();
        }

        return currentDirection;
    }

    @Override
    public int getDeathTime() {
        return TAXI_DEATH_TIME;
    }

}