package edu.uw.tcss.model;

import java.util.Locale;


/**
 * An abstract class that implements the vehicle interface.
 *
 * @author Kassie Whitney
 * @version 1.29.25
 */

public abstract class AbstractVehicle implements Vehicle {

    private  int myX;
    private  int myY;
    private Direction myDirection;

    protected AbstractVehicle() {
        super();
        myDirection = Direction.SOUTH;
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
    public boolean isAlive() {

        return true;
    }

    @Override
    public void poke() {

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
    public String toString() {
        return String.format("[Vehicle: %s, Location: (%d, %d)]",
                this.getClass().getSimpleName().toUpperCase(Locale.ROOT), getX(), getY());
    }

}