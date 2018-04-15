package maps.tile;

import java.awt.*;

public enum Direction {
    N(0, -64), NE(48, -32), SE(48, 32), S(0, 64), SW(-48, 32), NW(-48, -32), NULL(0, 0);

    private int dx;
    static {
        N.dx = 0;
        NE.dx = 1;
        SE.dx = 1;
        S.dx = 0;
        SW.dx = -1;
        NW.dx = -1;
        NULL.dx = 0;
    }

    public int getDx() {
        return dx;
    }

    private int dy;
    static {
        N.dy = -1;
        NE.dy = -1;
        SE.dy = 0;
        S.dy = 1;
        SW.dy = 1;
        NW.dy = 0;
        NULL.dy = 0;
    }

    public int getDy() {
        return dy;
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
