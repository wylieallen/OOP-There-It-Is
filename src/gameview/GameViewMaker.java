package gameview;

import gameobject.GameObject;
import gameobject.GameObjectContainer;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import maps.world.OverWorld;
import maps.world.World;
import utilities.Coordinate;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GameViewMaker
{
    private Map<GameObject, Displayable> spriteMap;
    private Map<World, WorldDisplayable> worldDisplayableMap;

    public GameViewMaker()
    {
        spriteMap = new HashMap<>();
        worldDisplayableMap = new HashMap<>();
    }

    public GameDisplayState makeGameDisplayState()
    {
        spriteMap = ImageMaker.makeDefaultMap();
        worldDisplayableMap = new HashMap<>();

        // OverWorld:

        Map<Coordinate, OverWorldTile> overworldMap = new HashMap<>();

        // Manual approach:

        OverWorldTile tile = new OverWorldTile();
        tile.add(Terrain.GRASS);
        overworldMap.put(new Coordinate(0, 0), tile);

        OverWorldTile tile2 = new OverWorldTile();
        tile2.add(Terrain.WATER);
        overworldMap.put(Direction.NE.getOffsetCoordinate(), tile2);

        OverWorldTile tile3 = new OverWorldTile();
        tile3.add(Terrain.MOUNTAIN);
        overworldMap.put(Direction.S.getOffsetCoordinate(), tile3);


        // expandOverworld approach:
        /*
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);

        for(int i = 0; i < 30; i++)
        {
            expandOverworld(overworldMap, Terrain.WATER);
        }
        */


        // Parallelogram approach:
        /*
        for(int i = -64; i < 64; i++)
        {
            for(int j = -64; j < 64; j++)
            {
                if(i != 0 || j != 0)
                {
                    OverWorldTile newTile = new OverWorldTile();
                    newTile.add(Terrain.GRASS);
                    overworldMap.put(new Coordinate(i, j), newTile);
                }
            }
        }
        */


        System.out.println("Tiles in overworld: " + overworldMap.keySet().size());

        OverWorld overworld = new OverWorld(overworldMap);


        WorldDisplayable overworldDisplayable = new WorldDisplayable(new Point(0, 0), 0, overworld);
        worldDisplayableMap.put(overworld, overworldDisplayable);

        // LocalWorlds:

        return new GameDisplayState(spriteMap, worldDisplayableMap, overworld);
    }

    // todo: expandOverworld is very inefficient right now
    private void expandOverworld(Map<Coordinate, OverWorldTile> map, Terrain terrain)
    {
        for(Coordinate c : new HashSet<>(map.keySet()))
        {
            for(Direction d : Direction.values())
            {
                Coordinate newCoord = c.add(d.getOffsetCoordinate());//new Coordinate(c.getX() + d.getDx(), c.getY() + d.getDy());
                if(!map.containsKey(newCoord))
                {
                    OverWorldTile newTile = new OverWorldTile();
                    newTile.add(terrain);
                    map.put(newCoord, newTile);
                }
            }
        }
    }
}