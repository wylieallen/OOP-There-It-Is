package utilities;

import maps.tile.Direction;

import java.awt.*;

/**
 * Created by dontf on 4/14/2018.
 */
public class Coordinate implements Comparable<Coordinate> {

    private final int x;
    private final int z;

    public Coordinate(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.z = other.z;
    }

    public int x() {
        return x;
    }

    public int z() {
        return z;
    }

    public int y() {
        return 0 - x() - z();
    }

    public Coordinate add(Coordinate other) {
        return new Coordinate(x() + other.x(), z() + other.z());
    }

    public Coordinate getNeighbor(Direction direction) {
        return add(direction.getOffsetCoordinate());
    }

    public int distance(Coordinate other) {
        int dx = Math.abs(x() - other.x());
        int dy = Math.abs(y() - other.y());
        int dz = Math.abs(z() - other.z());

        return Math.max( dx, Math.max(dy, dz) );
    }

    public Direction direction (Coordinate toThis) {

        if (this.equals(toThis)) return Direction.NULL;

        Coordinate diff = new Coordinate(toThis.x () - this.x (), toThis.z () - this.y ());
        int magnitude = this.distance(toThis);
        Coordinate unitdiff = new Coordinate(diff.x()/magnitude, diff.z()/magnitude);
        return Vector.getDirection(unitdiff);
    }

    @Override
    public int compareTo(Coordinate other) {
        if(x() == other.x() && z() == other.z()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Coordinate))
            return false;

        Coordinate otherCoordinate = (Coordinate)other;
        return (x() == otherCoordinate.x() && z() == otherCoordinate.z());
    }

    @Override
    public int hashCode() {
        int result = x();
        result = 31 * result + z();
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y() + "," + z + ")";
    }

    public Point toPixelPt()
    {
        int pixelX = (int) (32.0 * 1.5 * (double) x());
        // Why 37 instead of 32 here? Because fuck me, that's why
        // Appears to work fairly well out to any reasonable distance from origin
        // It'll be off by one pixel every once in a while but it's not too awful
        int pixelY = (int) (-37.0 * Math.sqrt(3) * ((double) y() + ((double) x() / 2.0)));
        //System.out.println("Cubic: " + c.x() + "," + c.y() + "," + c.z() + " Pixel: " + x + "," + y);
        return new Point(pixelX, pixelY);

    }
}
