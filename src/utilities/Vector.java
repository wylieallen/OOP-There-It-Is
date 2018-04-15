package utilities;

import maps.tile.Direction;

/**
 * Created by dontf on 4/14/2018.
 */
public class Vector {

    private Direction direction;
    private double magnitude;

    public Vector() {
        direction = Direction.NULL;
        magnitude = 0;
    }

    public Vector(Direction direction, double magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public void add (Vector other) {
        double thisDx = direction.getDx() * magnitude;
        double thisDy = direction.getDy() * magnitude;

        double otherDx = other.direction.getDx() * magnitude;
        double otherDy = other.direction.getDy() * magnitude;

        double totalDx = thisDx + otherDx;
        double totalDy = thisDy + otherDy;
    }

    public Direction getDirection () {
        return direction;
    }

    public double getMagnitude () {
        return magnitude;
    }

    public void setDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void setMagnitude(double newMagnitude) {
        magnitude = newMagnitude;
    }

    public boolean isZeroVector() {
        return magnitude == 0;
    }
}
