package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import utilities.Coordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dontf on 4/14/2018.
 */
public class OverWorld implements World {

    private Map<Coordinate, OverWorldTile> tiles;

    /*public OverWorld()
    {
        tiles = new HashMap<>();
    }*/

    public OverWorld(Map <Coordinate, OverWorldTile> tiles) {
        this.tiles = tiles;
        buildNeighborList();
    }

    @Override
    public void update() {
        updateTiles();
    }

    private void buildNeighborList() {
        for(Map.Entry<Coordinate, OverWorldTile> entry: tiles.entrySet()) {
            Coordinate coordinate = entry.getKey();
            for(Direction direction: Direction.values()) {
                OverWorldTile neighbor = tiles.get(coordinate.getNeighbor(direction));
                entry.getValue().setNeighbor(direction, neighbor);
            }
        }
    }

    private void updateTiles() {
        Set<MoveLegalityChecker> updated = new HashSet<>();
        for(OverWorldTile tile: tiles.values()) {
            tile.update(updated);
        }
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
        tiles.get(p).setEntity(e);
    }

    @Override
    public Map<Coordinate, GameObjectContainer> getMap() {
        Map<Coordinate, GameObjectContainer> map = new HashMap<>();
        for(Map.Entry<Coordinate, OverWorldTile> entry : tiles.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    public OverWorldTile getTile(Coordinate c) {
        return tiles.getOrDefault(c, null);
    }

    @Override
    public Tile getTileForEntity(Entity e) {
        for(OverWorldTile tile: tiles.values()) {
            if(tile.has(e))
                return tile;
        }
        return null;
    }

    @Override
    public void remove(Entity e) {
        for(OverWorldTile tile: tiles.values()) {
            if(tile.remove(e))
                return;
        }
    }

}
