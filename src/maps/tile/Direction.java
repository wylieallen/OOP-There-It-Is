package maps.tile;

import java.awt.*;

public enum Direction {
    N(0, -64), NE(48, -32), SE(48, 32), S(0, 64), SW(-48, 32), NW(-48, -32);

    Direction(int pixelX, int pixelY)
    {
        this.pixelOffset = new Point(pixelX, pixelY);
    }

    // todo: Direction is technically part of the model, not the view, so pixel coords should probably be handled elsewhere
    private Point pixelOffset;

    public int getPixelX() { return pixelOffset.x; }
    public int getPixelY() { return pixelOffset.y; }
}
