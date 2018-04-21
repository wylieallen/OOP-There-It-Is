package guiframework;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel
{
    private DisplayState activeState;

    public DisplayPanel(Dimension size)
    {
        super();
        super.setSize(size);
        this.activeState = new DisplayState();
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
