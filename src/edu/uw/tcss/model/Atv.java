package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * An ATV class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public final class Atv extends AbstractVehicle {

    /**
     * The death time for the ATV.
     */
    private static final int ATV_DEATH_TIME = 25;

    /**
     * Stores the previously called direction.
     */
    private Direction myPreviousDirection;

    /**
     * Constructor for the Atv Class.
     *
     * @param theX the starting x coordinate of the ATV
     * @param theY the starting y cooridnate of the ATV
     * @param theDir the starting Dirrection of the ATV
     *               (randomly generated from them direction Enum)
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

        myPreviousDirection = theDir;
    }

    @Override
    public int getDeathTime() {
        return ATV_DEATH_TIME;
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return !theTerrain.equals(Terrain.WALL);
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final ArrayList<Direction> directionArrayList = new ArrayList<>();

        //itterating through direction enum
        for (final Direction dir : Direction.values()) {
            if (theNeighbors.get(dir) != Terrain.WALL) {
                directionArrayList.add(dir);
            }
        }

        //Retrieves a random direction from the available direction within the list
        Direction direction = directionArrayList.get(
                randomIntGenerator(directionArrayList.size()));

        //Checks if the the next random direction is the reverse of the previous direction
        //(prevents the Atv from choosing a direction
        // that would be the inverse of its previous direction.)
        if (myPreviousDirection.reverse() == direction) {
            directionArrayList.remove(direction);
            direction = directionArrayList.get(randomIntGenerator(directionArrayList.size()));
        }

        //Stores the current direction into a previous direction field for future reference
        myPreviousDirection = direction;

        return direction;
    }

    /**
     *
     * Random Integer generator starting from 0 to a specified integer passed in theUpperBound
     * parameter.
     *
     * @param theUpperBound the highest number to generate a random integer from 0.
     * @return a randomly generated integer between 0 and theUpperBound parameter.
     */
    private int randomIntGenerator(final int theUpperBound) {
        final Random random = new Random();

        return random.nextInt(theUpperBound);
    }




}