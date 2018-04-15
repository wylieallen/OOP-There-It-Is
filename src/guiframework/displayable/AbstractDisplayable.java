package guiframework.displayable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class AbstractDisplayable implements Displayable
{
    private Point origin;
    private Dimension size;
    private int depth;

    public AbstractDisplayable(Point origin, Dimension size, int depth)
    {
        this.origin = origin;
        this.size = size;
        this.depth = depth;
    }

    public void draw(Graphics2D g2d)
    {
        AffineTransform t = g2d.getTransform();
        g2d.translate(origin.x, origin.y);
        do_draw(g2d);
        g2d.setTransform(t);
    }

    // Template Method primitive:
    protected abstract void do_draw(Graphics2D g2d);

    // Hooks to be overridden by descendant classes:
    public boolean expired() { return false; }
    public void update() { }

    // Default geometric structure implementations:
    public Point getOrigin() { return origin; }
    public Dimension getSize() { return size; }
    public int getDepth() { return depth; }
}
