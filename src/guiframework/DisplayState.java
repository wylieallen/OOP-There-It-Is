package guiframework;

import guiframework.displayable.Displayable;

import java.awt.*;
import java.util.PriorityQueue;

public class DisplayState
{
    private PriorityQueue<Displayable> displayables;

    public DisplayState()
    {
        displayables = new PriorityQueue<>();
    }

    public void draw(Graphics2D g2d)
    {
        for(Displayable d : displayables)
            d.draw(g2d);
    }

    public void add(Displayable d)
    {
        displayables.add(d);
    }

    public void remove(Displayable d)
    {
        displayables.remove(d);
    }

    public void update()
    {
        displayables.forEach(Displayable::update);
    }
}
