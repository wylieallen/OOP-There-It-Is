package gameview.displayable.widget;

import entity.entitycontrol.KeyRole;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class ConfigControlsDisplayable extends CompositeDisplayable
{
    private Displayable background = new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(256, 384, Color.WHITE), 0);
    private int cursorIndex = 0;
    private boolean primarySelected = true;


    public ConfigControlsDisplayable(Point origin)
    {
        super(origin, 999999);
        update();
    }

    @Override
    public void update()
    {
        clear();
        add(background);
        int i = 0;
        for(KeyRole keyRole : KeyRole.values())
        {
            add(new StringDisplayable(new Point(4, 12 + (i * 16)), () -> keyRole + ": " + KeyEvent.getKeyText(keyRole.getPrimaryKeycode())
                    + " , " + KeyEvent.getKeyText(keyRole.getSecondaryKeycode()), Color.BLACK, 1));
            ++i;
        }
        if(cursorIndex > -1)
        {
            int x = primarySelected ? -4 : 64;
            add(new ImageDisplayable(new Point(x, 4 + (cursorIndex * 16)), ImageMaker.makeRightPointingTriangle(), 99));
        }
    }

    public void adjustIndex(int delta)
    {
        cursorIndex += delta;
        int cursorMax = KeyRole.values().length - 1;
        if(cursorIndex < 0)
        {
            cursorIndex = cursorMax;
        }
        else if(cursorIndex > cursorMax)
        {
            cursorIndex = 0;
        }
    }

    public void togglePrimarySecondary()
    {
        primarySelected = !primarySelected;
    }

    public int getCursorIndex()
    {
        return cursorIndex;
    }

    public boolean primarySelected()
    {
        return primarySelected;
    }
}
