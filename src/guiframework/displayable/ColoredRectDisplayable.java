package guiframework.displayable;

import java.awt.*;

public class ColoredRectDisplayable extends AbstractDisplayable
{
    private Color color;

    public ColoredRectDisplayable(Point origin, int width, int height, int z, Color color)
    {
        super(origin, new Dimension(width, height), z);
        this.color = color;
    }

    protected void do_draw(Graphics2D g2d)
    {
        g2d.setColor(color);
        int size = getSize().width;
        g2d.fillRect(0, 0, size, size);
    }
}
