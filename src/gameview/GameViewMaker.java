package gameview;

import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import entity.entitymodel.Inventory;
import gameobject.GameObject;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.displayable.Displayable;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import utilities.Coordinate;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameViewMaker
{
    private Map<GameObject, Displayable> spriteMap;
    private Map<World, WorldDisplayable> worldDisplayableMap;

    public GameViewMaker()
    {
        spriteMap = new HashMap<>();
        worldDisplayableMap = new HashMap<>();
    }

    public GameDisplayState makeGameDisplayState(GamePanel panel)
    {
        spriteMap = ImageMaker.makeDefaultMap();
        worldDisplayableMap = new HashMap<>();

        // OverWorld:

        Map<Coordinate, OverWorldTile> overworldMap = new HashMap<>();

        // Manual approach:

        Set<MoveLegalityChecker> mlc = new HashSet<>();

        mlc.add(Terrain.GRASS);
        OverWorldTile tile = new OverWorldTile(mlc, null);
        overworldMap.put(new Coordinate(0, 0), tile);

        mlc = new HashSet<>();
        mlc.add(Terrain.WATER);
        OverWorldTile tile2 = new OverWorldTile(mlc, null);
        overworldMap.put(Direction.NE.getOffsetCoordinate(), tile2);

        mlc = new HashSet<>();
        mlc.add(Terrain.WATER);
        OverWorldTile tile3 = new OverWorldTile(mlc, null);
        overworldMap.put(Direction.S.getOffsetCoordinate(), tile3);


        // expandOverworld approach:

        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        /*
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

        Entity player = new Entity();
        spriteMap.put(player, ImageMaker.makeEntityDisplayable(player));

        //tile.setEntity(player);

        overworldMap.get(new Coordinate(2, 1)).setEntity(player);

        System.out.println("Tiles in overworld: " + overworldMap.keySet().size());

        OverWorld overworld = new OverWorld(overworldMap);

        WorldDisplayable overworldDisplayable = new WorldDisplayable(new Point(0, 0), 0, overworld);
        worldDisplayableMap.put(overworld, overworldDisplayable);

        // LocalWorlds:
        List<LocalWorld> localWorldsList = new ArrayList<>();

        Game game = new Game(overworld, overworld, localWorldsList, 0, player);
        game.setPlayerController(new HumanEntityController(player, new Equipment(10, new Inventory(), player), game.getCoordinate(player), new ArrayList<>(), panel));

        return new GameDisplayState(panel.getSize(), new Game(overworld, overworld, localWorldsList, 0, player), spriteMap, worldDisplayableMap, overworld);
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
                    Set <MoveLegalityChecker> mlc = new HashSet<>();
                    mlc.add(terrain);
                    OverWorldTile newTile = new OverWorldTile(mlc, null);
                    map.put(newCoord, newTile);
                }
            }
        }
    }
}