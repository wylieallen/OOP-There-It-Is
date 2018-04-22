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

        Map<Coordinate, GameObject> iaMap = world.getInfluences();
        for(Coordinate c : iaMap.keySet()){
            if(map.containsKey(c)){
                tiles.get(map.get(c)).add(GameDisplayState.getSprite(iaMap.get(c)));
            }
        }

    }

    private void updateTileDisplayable(Coordinate c, GameObjectContainer t)
    {
        if(!tiles.containsKey(t))
        {
            put(t, new CompositeDisplayable(c.toPixelPt(), 0));
        }
        CompositeDisplayable displayable = tiles.get(t);
        displayable.clear();
        for(GameObject o : t.getGameObjects())
        {
            displayable.add(GameDisplayState.getSprite(o));
        }
    }
}
