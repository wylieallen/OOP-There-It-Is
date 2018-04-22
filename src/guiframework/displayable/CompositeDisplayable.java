package guiframework.displayable;

import java.awt.*;
import java.util.*;

public class CompositeDisplayable extends AbstractDisplayable
{
    private PriorityQueue<Displayable> components;

    public CompositeDisplayable(Point origin, int height)
    {
        super(origin, new Dimension(), height);
        this.components = new PriorityQueue<>();
    }

    public void add(Displayable d)
    {
        components.add(d);
        resize();
    }

    public void remove(Displayable d)
    {
        components.remove(d);
        resize();
    }

    private void resize()
    {
        int maxX = 0, maxY = 0;

        for(Displayable component : components)
        {
            Point buttonPt = component.getOrigin();
            Dimension buttonSize = component.getSize();
            int pointEndX = buttonPt.x + buttonSize.width;
            if(maxX < pointEndX) maxX = pointEndX;
            int pointEndY = buttonPt.y + buttonSize.height;
            if(maxY < pointEndY) maxY = pointEndY;
        }

        getSize().setSize(maxX, maxY);
    }

    public void update()
    {
        Set<Displayable> expireds = new HashSet<>();
        for(Displayable d : components)
        {
            d.update();
            if(d.expired()) expireds.add(d);
        }

        if(expireds.size() > 0)
        {
            components.removeAll(expireds);
            resize();
        }
    }

    public boolean expired()
    {
        return components.size() < 1;
    }

    public void do_draw(Graphics2D g2d)
    {
        Displayable[] displayables = new Displayable[0];
        displayables = components.toArray(displayables);
        Arrays.sort(displayables, components.comparator());
        for(Displayable displayable: displayables) {
            displayable.draw(g2d);
        }
    }


    public void clear()
    {
        components.clear();
    }
}
