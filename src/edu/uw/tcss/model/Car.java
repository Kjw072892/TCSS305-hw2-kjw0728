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

        final boolean canMove;

        if (theTerrain == Terrain.STREET) {
            canMove = true;
        } else if (theTerrain == Terrain.CROSSWALK
                && theLight == Light.YELLOW || theLight == Light.RED) {
            canMove = false;
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN) {
            canMove = true;
        } else {
            canMove = theTerrain == Terrain.LIGHT
                    && theLight == Light.GREEN || theLight == Light.YELLOW;
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




}
