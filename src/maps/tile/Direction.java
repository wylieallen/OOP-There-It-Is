package maps.tile;

import utilities.Coordinate;

import java.awt.*;
import java.util.Random;

public enum Direction {
    N(0, -64), NE(48, -32), SE(48, 32), S(0, 64), SW(-48, 32), NW(-48, -32), NULL(0, 0);

    private Coordinate offsetCoordinate;
    private Direction clockWiseAdjacent;
    private Direction counterClockWiseAdjacent;

    static {
        N.offsetCoordinate = new Coordinate(0 ,-1);
        NE.offsetCoordinate = new Coordinate(1 ,-1);
        SE.offsetCoordinate = new Coordinate(1 ,0);
        S.offsetCoordinate = new Coordinate(0 ,1);
        SW.offsetCoordinate = new Coordinate(-1 ,1);
        NW.offsetCoordinate = new Coordinate(-1 ,0);
        NULL.offsetCoordinate = new Coordinate(0 ,0);

        N.clockWiseAdjacent = NE;
        N.counterClockWiseAdjacent = NW;
        NW.clockWiseAdjacent = N;
        NW.counterClockWiseAdjacent = SW;
        SW.clockWiseAdjacent = NW;
        SW.counterClockWiseAdjacent = S;
        S.clockWiseAdjacent = SW;
        S.counterClockWiseAdjacent = SE;
        SE.clockWiseAdjacent = S;
        SE.counterClockWiseAdjacent = NE;
        NE.clockWiseAdjacent = SE;
        NE.counterClockWiseAdjacent = N;
    }

    public Coordinate getOffsetCoordinate() {
        return offsetCoordinate;
    }

    Direction(int pixelX, int pixelY)
    {
        this.pixelOffset = new Point(pixelX, pixelY);
    }

    private Point pixelOffset;

    public int getPixelX() { return pixelOffset.x; }
    public int getPixelY() { return pixelOffset.y; }

    private static Random random = new Random();
    private static Direction[] values = values();
    public static Direction getRandom() {
        int rand = random.nextInt(values.length);
        return values[rand];
    }

    public Direction getClockWiseAdjacent(){
        return clockWiseAdjacent;
    }

    public Direction getCounterClockwiseAdjacent(){
        return counterClockWiseAdjacent;
    }
}
