package guiframework.displayable;

import guiframework.util.TypedAbstractFunction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StringDisplayable extends AbstractDisplayable
{
    private TypedAbstractFunction<String> stringMaker;
    private Color color;
    private Font font;
    private FontMetrics metrics;

    public StringDisplayable(Point origin, TypedAbstractFunction<String> stringMaker, Color color, Font font, int height)
    {
        super(origin, new Dimension(), height);
        this.stringMaker = stringMaker;
        this.color = color;
        this.font = font;
        this.metrics = (new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)).createGraphics().getFontMetrics();
    }

    public void do_draw(Graphics2D g2d)
    {
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(stringMaker.execute(), 0, 0);
    }

    @Override
    public Dimension getSize()
    {
        return new Dimension(metrics.stringWidth(stringMaker.execute()), metrics.getHeight());
    }



}
