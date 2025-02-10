package edu.uw.tcss.model;

import java.util.Map;

/**
 * A Car class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public class Car extends AbstractVehicle {


    /**
     * The death time for the Car.
     */
    private static final int CAR_DEATH_TIME = 15;


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
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        boolean canMove = false;

        if (isValid(theTerrain) && theTerrain == Terrain.LIGHT) {
            if (theLight == Light.GREEN || theLight == Light.YELLOW) {
                canMove = true;
            }
        } else if (isValid(theTerrain) && theTerrain == Terrain.CROSSWALK) {
            if (theLight == Light.GREEN) {
                canMove = true;
            }
        } else if (isValid(theTerrain) && theTerrain == Terrain.STREET) {
            canMove = true;
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
        return CAR_DEATH_TIME;
    }

    /**
     * Helper method for canPass.
     *
     * @param theTerrain the current terrain the vehicle sees.
     * @return boolean value true, if the car can pass the terrain, false otherwise.
     */
    private boolean isValid(final Terrain theTerrain) {
        final boolean canMove;
        switch (theTerrain) {
            case STREET, LIGHT, CROSSWALK -> canMove = true;
            default -> canMove = false;
        }

        return canMove;
    }
}
