package utilities;

import maps.tile.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class Vector {
    private static Map<Coordinate, Direction> axialToDirection;
    static {
        axialToDirection = new HashMap<>();
        axialToDirection.put(new Coordinate(0, -1), Direction.N);
        axialToDirection.put(new Coordinate(1, -1), Direction.NE);
        axialToDirection.put(new Coordinate(1, 0), Direction.SE);
        axialToDirection.put(new Coordinate(0, 1), Direction.S);
        axialToDirection.put(new Coordinate(-1, 1), Direction.SW);
        axialToDirection.put(new Coordinate(-1, 0), Direction.NW);
    }

    private double dx;
    private double dz;

    public double dx() {
        return dx;
    }

    public double dz() {
        return dz;
    }

    public double dy() {
        return 0 - dx() - dz();
    }

    public Vector() {
        dx = dz = 0;
    }

    public Vector(Direction direction, double magnitude) {
        Coordinate coordinate = direction.getOffsetCoordinate();
        dx = coordinate.x() * magnitude;
        dz = coordinate.z() * magnitude;
    }

    public Vector(Coordinate start, Coordinate end) {
        dx = end.x() - start.x();
        dz = end.z() - start.z();
    }

    public Vector(double dx, double dz){
        this.dx = dx;
        this.dz = dz;
    }

    public void add (Vector other) {
        dx += other.dx();
        dz += other.dz();
    }

    public Direction getDirection () {
        double distance = getDistance();
        if(distance == 0) return Direction.NULL;

        double dirDx = dx() * 1 / distance;
        double dirDy = dy() * 1 / distance;
        double dirDz = dz() * 1 / distance;

        double rDx = Math.round(dirDx);
        double rDy = Math.round(dirDy);
        double rDz = Math.round(dirDz);

        double dxDiff = Math.abs(dirDx - rDx);
        double dyDiff = Math.abs(dirDy - rDy);
        double dzDiff = Math.abs(dirDz - rDz);

        if(dxDiff > dyDiff && dxDiff > dzDiff)
            rDx = 0 - rDy - rDz;
        else if(dyDiff > dzDiff)
            rDy = 0 - rDx - rDz;
        else
            rDz = 0 - rDx - rDy;

        return axialToDirection.getOrDefault(new Coordinate((int)rDx, (int)rDz), Direction.NULL);
    }

    public double getMagnitude () {
        return getDistance();
    }

    public double getDistance() {
        return Math.max(Math.abs(dx()), Math.max(Math.abs(dy()), Math.abs(dz())));
    }

    public boolean isZeroVector() {
        return dx() == 0 && dy() == 0 && dz() == 0;
    }

    public static Direction getDirection (Coordinate offset) {
        return axialToDirection.getOrDefault(offset, Direction.NULL);
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Vector))
            return false;

        Vector otherVector = (Vector)other;

        return dx() == otherVector.dx() && dz() == otherVector.dz();
    }
}
