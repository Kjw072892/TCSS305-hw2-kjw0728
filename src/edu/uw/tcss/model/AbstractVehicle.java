package edu.uw.tcss.model;

import java.util.Locale;
import java.util.Objects;


/**
 * An abstract class that implements the vehicle interface.
 *
 * @author Kassie Whitney
 * @version 1.31.25
 */

public abstract class AbstractVehicle implements Vehicle {

    /**
     * Stores the starting x value of the Car at program start up.
     */
    private final int myDefaultX;

    /**
     * Stores the starting y value of the Car at program start up.
     */
    private final int myDefaultY;

    /**
     * Stores the Starting direction (NORTH, EAST, SOUTH, WEST) of the Car at program start up
     */
    private final Direction myDefaultDirection;

    /**
     * Stores the vehicle X value.
     */
    private int myX;

    /**
     * Stores the vehicle Y value.
     */
    private int myY;

    /**
     * Stores the vehicle direction.
     */
    private Direction myDirection;

    /**
     * Stores the Bicycle alive value. True if alive, false if not alive.
     */
    private boolean myIsAlive;

    /**
     * Gui Poke counter
     */
    private int myPokes;


    protected AbstractVehicle(final int theX, final int theY, final Direction theDir) {
        super();
        myX = theX;
        myDefaultX = theX;
        myY = theY;
        myDefaultY = theY;
        myDirection = theDir;
        myDefaultDirection = theDir;
        myIsAlive = true;

    }

    @Override
    public String getImageFileName() {

        final String vicName = this.getClass().getSimpleName().toLowerCase(Locale.ROOT);
        String fileName = String.format("%s.gif", vicName);

        if (!isAlive()) {
            fileName = String.format("%s_dead.gif", vicName);
        }

        return fileName;
    }

    @Override
    public boolean isAlive() {

        return myIsAlive;
    }

    @Override
    public void poke() {
        ++myPokes;
        if (myPokes >= getDeathTime()) {
            myIsAlive = true;

        }
    }

    @Override
    public void collide(final Vehicle theOther) {

        final int otherX = theOther.getX();
        final int otherY = theOther.getY();

        if (this.getX() == otherX
                && this.getY() == otherY
                && this.getDeathTime() > theOther.getDeathTime()
                && theOther.isAlive()
                && isAlive()) {
            myPokes = 0;
            myIsAlive = false;
            myDirection = Direction.random();
        }
    }

    @Override
    public final Direction getDirection() {

        return myDirection;
    }

    @Override
    public final int getX() {
        return myX;
    }

    @Override
    public final int getY() {
        return myY;
    }

    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;
    }

    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    @Override
    public void setY(final int theY) {
        myY = theY;
    }

    @Override
    public void reset() {

        setX(myDefaultX);
        setY(myDefaultY);
        setDirection(myDefaultDirection);
        myIsAlive = true;

    }

    @Override
    public String toString() {
        return String.format("[Vehicle: %s, \nLocation: (%d, %d)]",
                this.getClass().getSimpleName().toUpperCase(Locale.ROOT), getX(), getY());
    }

}