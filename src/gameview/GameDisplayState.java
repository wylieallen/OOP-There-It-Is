package gameview;

import gameobject.GameObject;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.DisplayState;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import maps.tile.Tile;
import maps.world.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameDisplayState extends DisplayState
{
    private Point camera;
    private Map<GameObject, Displayable> spriteMap;
    private Map<World, WorldDisplayable> worlds;
    private WorldDisplayable activeWorldDisplayable;

    public GameDisplayState()
    {
        spriteMap = ImageMaker.makeDefaultMap();
        this.camera = new Point(0, 0);
        this.worlds = new HashMap<>();
        activeWorldDisplayable = new WorldDisplayable(new Point(0, 0), -1);
        super.add(activeWorldDisplayable);
    }

    public void add(Tile tile, Point origin)
    {
        CompositeDisplayable displayable = new CompositeDisplayable(origin, 0);
        for(GameObject object : tile.getGameObjects())
        {
            displayable.add(spriteMap.get(object));
        }
        activeWorldDisplayable.put(tile, displayable);
    }

    public void transitionWorld(World nextWorld)
    {
        remove(activeWorldDisplayable);
        add(activeWorldDisplayable = worlds.get(nextWorld));
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        super.draw(g2d);
    }



}
