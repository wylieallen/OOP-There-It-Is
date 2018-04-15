package utilities;

import maps.tile.Direction;

/**
 * Created by dontf on 4/14/2018.
 */
public class Coordinate implements Comparable<Coordinate> {

    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return 0 - x - y;
    }

    public Coordinate getNeighbor(Direction direction) {
        int newX = x + direction.getDx();
        int newY = y + direction.getDy();

        return new Coordinate(newX, newY);
    }

    @Override
    public int compareTo(Coordinate other) {
        if(x == other.x && y == other.y) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Coordinate))
            return false;

        Coordinate otherCoordinate = (Coordinate)other;
        return (x == otherCoordinate.x && y == otherCoordinate.y);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
