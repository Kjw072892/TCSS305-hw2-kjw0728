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

    private static final int ATV_DEATH_TIME = 25;
    private Direction myPreviousDirection = getDirection();
    private final int myDefaultX;
    private final int myDefaultY;
    private final Direction myDefaultDirection;

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
        myDefaultX = theX;
        myDefaultY = theY;
        myDefaultDirection = theDir;
    }

    @Override
    public void reset() {

        this.setX(myDefaultX);
        this.setY(myDefaultY);
        this.setDirection(myDefaultDirection);

    }

    @Override
    public int getDeathTime() {

        return ATV_DEATH_TIME;
    }


    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        return !theTerrain.equals(Terrain.WALL);
    }

    //Choose dirrection is the driver of the vehicle
    // the map outputs all the directions with the terrain features at each dirrection
    // the goal is to randomly choose a direction that isnt a wall, or the reverse of it's
    // previous direction

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final ArrayList<Direction> directionArrayList = new ArrayList<>();

        //itterating through the direction enum
        for (final Direction dir : Direction.values()) {
            if (theNeighbors.get(dir) != Terrain.WALL) {
                directionArrayList.add(dir);
            }
        }

        //Retrieves a random direction from the available direction within the list
        Direction direction = directionArrayList.get(
                randomIntGenerator(directionArrayList.size()));

        //Checks if the the next random direction is the reverse of the previous direction
        //(prevents the Atv from reversing)
        if (myPreviousDirection.reverse().equals(direction)) {
            directionArrayList.remove(direction);
            direction = directionArrayList.get(randomIntGenerator(directionArrayList.size()));
        }

        //Stores the current direction into a previous direction field for future reference
        myPreviousDirection = direction;

        return direction;
    }


    @Override
    public void collide(final Vehicle theOther) {


    }

    /**
     *
     * Random Integer generator starting from 0 to a specified integer passed in theTo
     * parameter.
     *
     * @param theTo the highest number to generate a random integer from 0.
     * @return a randomly generated int between 0 and theTo parameter
     */
    private int randomIntGenerator(final int theTo) {
        final Random random = new Random();

        return random.nextInt(theTo);
    }




}