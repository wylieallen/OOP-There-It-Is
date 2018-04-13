package guiframework.displayable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplayable extends AbstractDisplayable
{
    private BufferedImage image;

    public ImageDisplayable(Point origin, BufferedImage image, int height)
    {
        super(origin, new Dimension(image.getWidth(), image.getHeight()), height);
        this.image = image;
    }

    public void do_draw(Graphics2D g2d)
    {
        g2d.drawImage(image, 0, 0, null);
    }
}
