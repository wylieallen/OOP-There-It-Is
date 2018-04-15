package maps.tile;

import utilities.Coordinate;

import java.awt.*;

public enum Direction {
    N(0, -64), NE(48, -32), SE(48, 32), S(0, 64), SW(-48, 32), NW(-48, -32), NULL(0, 0);

    private Coordinate offsetCoordinate;
    static {
        N.offsetCoordinate = new Coordinate(0 ,-1);
        NE.offsetCoordinate = new Coordinate(1 ,-1);
        SE.offsetCoordinate = new Coordinate(1 ,0);
        S.offsetCoordinate = new Coordinate(0 ,1);
        SW.offsetCoordinate = new Coordinate(-1 ,1);
        NW.offsetCoordinate = new Coordinate(-1 ,0);
        NULL.offsetCoordinate = new Coordinate(0 ,0);
    }

    public Coordinate getOffsetCoordinate() {
        return offsetCoordinate;
    }

    Direction(int pixelX, int pixelY)
    {
        this.pixelOffset = new Point(pixelX, pixelY);
    }

    // todo: Direction is technically part of the model, not the view, so pixel coords should probably be handled elsewhere
    private Point pixelOffset;

    public int getPixelX() { return pixelOffset.x; }
    public int getPixelY() { return pixelOffset.y; }
}
