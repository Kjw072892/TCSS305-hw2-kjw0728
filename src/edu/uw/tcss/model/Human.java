package edu.uw.tcss.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A Human Class.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */
public class Human extends AbstractVehicle {
    /**
     * Stores the death count for human
     */
    private static final int DEATH_COUNT = 45;

    /**
     * Stores the previously called direction.
     */
    private Direction myPreviousDirection;

    /**
     *
     * Constructor for Human class.
     *
     * @param theX the starting x coordinate
     * @param theY the starting y coordinate
     * @param theDir the starting direction coordinate
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);

        myPreviousDirection = theDir;

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        boolean canMove = false;

        if (isValid(theTerrain)) {
            if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
                canMove = true;
            }
            if (theTerrain != Terrain.CROSSWALK && theLight == Light.GREEN
                    || theLight == Light.YELLOW || theLight == Light.RED) {
                canMove = true;
            }
        }

        return canMove;
    }

    private boolean isValid(final Terrain theTerrain) {
        return theTerrain == Terrain.CROSSWALK
                || theTerrain == Terrain.GRASS;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {

        final List<Direction> grassDirectionList = moveToList(theNeighbors);
        final List<Direction> crossWalkDirectionList = new ArrayList<>();
        Direction currentDirection = getDirection();

        //checks if the getDirection() is not the reverse direction of the previous direction
        if (currentDirection != myPreviousDirection.reverse()) {
            for (final Direction direction : grassDirectionList) {

                //Adds the crosswalk direction to a list
                if (theNeighbors.get(direction) == Terrain.CROSSWALK) {
                    crossWalkDirectionList.add(direction);
                    break;
                }
            }
        }

        //Sets the return variable to a random element from grassDirectionList
        if (crossWalkDirectionList.isEmpty()) {

            currentDirection =
                    grassDirectionList.get(randomIntGenerator(grassDirectionList.size()));

        } else {

            //Sets the return variable to the crosswalk direction
            currentDirection = crossWalkDirectionList.getFirst();
            grassDirectionList.clear();
        }
        myPreviousDirection = currentDirection;
        return currentDirection;
    }



    @Override
    public int getDeathTime() {
        return DEATH_COUNT;
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

    /**
     *
     * Stores the direction map into a list (helper Method).
     * Used to save space in the ChooseDirection method.
     *
     * @param theNeighbors a map of the direction with values of terrain.
     * @return a list of the filtered directions.
     */
    private List<Direction> moveToList(final Map<Direction, Terrain> theNeighbors) {
        final List<Direction> directionList = new ArrayList<>();
        for (final Direction direction : Direction.values()) {
            if (theNeighbors.get(direction) == Terrain.GRASS
                    || theNeighbors.get(direction) == Terrain.CROSSWALK) {
                directionList.add(direction);
            }
        }

        if (directionList.size() > 1) {
            directionList.remove(getDirection().reverse());
        }

        return  directionList;
    }

    /**
     *
     * Iterates through the map and stores the seen Terrain into a list.
     * (Helper method for chooseDirection).
     *
     * @param theNeighbors a map with (Direction: terrain) key-value pair.
     * @return a list of seen terrain.
     */
    private List<Terrain> terrainList(final Map<Direction, Terrain> theNeighbors) {
        final List<Terrain> terrainList = new ArrayList<>();
        for (final Direction direction : Direction.values()) {
            if (theNeighbors.get(direction) == Terrain.GRASS
                    || theNeighbors.get(direction) == Terrain.CROSSWALK) {
                terrainList.add(theNeighbors.get(direction));
            }
        }

        return  terrainList;
    }


}
