package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import utilities.Coordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class OverWorld implements World {

    private Map<Coordinate, OverWorldTile> tiles;

    public OverWorld()
    {
        tiles = new HashMap<>();
    }

    @Override
    public void update() {

    }

    public void add(Coordinate p, Terrain t)
    {
        if(!tiles.containsKey(p))
        {
            tiles.put(p, new OverWorldTile());
        }
        tiles.get(p).add(t);
    }

    @Override
    public void add(Coordinate p, Entity e) {

    }

    @Override
    public Map<Coordinate, GameObjectContainer> getMap() {
        return null;
    }

    @Override
    public Tile getTileForEntity(Entity e) {
        return null;
    }

}
