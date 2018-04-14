package gameview.displayable.sprite;

import gameobject.GameObjectContainer;
import guiframework.displayable.AbstractDisplayable;
import guiframework.displayable.CompositeDisplayable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorldDisplayable extends CompositeDisplayable
{
    private Map<GameObjectContainer, CompositeDisplayable> tiles;

    public WorldDisplayable(Point origin, int height)
    {
        super(origin, height);
        tiles = new HashMap<>();
    }

    public void put(GameObjectContainer tile, CompositeDisplayable displayable)
    {
        tiles.put(tile, displayable);
        add(displayable);
    }

    public void do_draw(Graphics2D g2d)
    {
        //todo: implement
        super.do_draw(g2d);
    }
}
