package guiframework.displayable;

import java.awt.*;

public interface Displayable extends Comparable<Displayable>
{
    void draw(Graphics2D g2d);
    void update();
    boolean expired();
    Point getOrigin();
    Dimension getSize();
    int getDepth();

    @Override
    default int compareTo(Displayable displayable)
    {
        return this.getDepth() - displayable.getDepth();
    }
}
