package gameview.displayable.sprite;

import gameobject.GameObject;
import gameobject.GameObjectContainer;
import gameview.GameDisplayState;
import guiframework.displayable.CompositeDisplayable;
import maps.world.World;
import utilities.Coordinate;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorldDisplayable extends CompositeDisplayable
{
    private World world;
    private Map<GameObjectContainer, CompositeDisplayable> tiles;

    public WorldDisplayable(Point origin, int height, World world)
    {
        super(origin, height);
        tiles = new HashMap<>();
        this.world = world;
    }

    private void put(GameObjectContainer tile, CompositeDisplayable displayable)
    {
        tiles.put(tile, displayable);
        add(displayable);
    }

    @Override
    public void update()
    {
        Map<Coordinate, GameObjectContainer> map = world.getMap();
        for(Coordinate c : map.keySet())
        {
            updateTileDisplayable(c, map.get(c));
        }
    }

    private void updateTileDisplayable(Coordinate c, GameObjectContainer t)
    {
        if(!tiles.containsKey(t))
        {
            put(t, new CompositeDisplayable(getPixelPoint(c), 0));
        }
        CompositeDisplayable displayable = tiles.get(t);
        displayable.clear();
        for(GameObject o : t.getGameObjects())
        {
            displayable.add(GameDisplayState.getSprite(o));
        }
    }

    private Point getPixelPoint(Coordinate c)
    {
        int x = (int) (32.0 * 1.5 * (double) c.x());
        // Why 37 instead of 32 here? Because fuck me, that's why
        // Appears to work fairly well out to any reasonable distance from origin
        // It'll be off by one pixel every once in a while but it's not too awful
        int y = (int) (-37.0 * Math.sqrt(3) * ((double) c.y() + ((double) c.x() / 2.0)));
        System.out.println("Cubic: " + c.x() + "," + c.y() + "," + c.z() + " Pixel: " + x + "," + y);
        return new Point(x, y);
    }
}
