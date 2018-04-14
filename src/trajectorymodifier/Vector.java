package trajectorymodifier;

import tile.Direction;

public class Vector {

    private Direction direction;
    private double magnitude;

    public Vector(Direction direction, double magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public Vector add(Vector other) {
        return null;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getMagnitude() {
        return magnitude;
    }
}
