package utilities;

import maps.tile.Direction;

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

    //determine how far two tiles are from each other;
    public int distance (Coordinate c) {
        int temp = Math.max(Math.abs(c.x() - x()), Math.abs(c.z() - z()));
        return Math.max(temp, Math.abs(c.y() - y()));
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
}
