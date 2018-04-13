package guiframework;

import guiframework.displayable.ColoredRectDisplayable;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel
{
    private DisplayState activeState;

    public DisplayPanel()
    {
        this.activeState = new DisplayState();
        activeState.add(new ColoredRectDisplayable(new Point(256, 256), 50, 50, 0, Color.RED));
        activeState.add(new ColoredRectDisplayable(new Point(256, 256), 50, 50, 1, Color.YELLOW));
        activeState.add(new ColoredRectDisplayable(new Point(256, 256), 100, 100, -1, Color.GREEN));
    }

    public void setDisplayState(DisplayState displayState) { this.activeState = displayState; }

    public DisplayState getActiveState() { return activeState; }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        activeState.draw((Graphics2D) g);
    }
}
