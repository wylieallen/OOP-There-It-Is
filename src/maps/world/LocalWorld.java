package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.Influence.InfluenceArea;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.*;

/**
 * Created by dontf on 4/14/2018.
 */
public class LocalWorld implements World {

    private Map<Coordinate, LocalWorldTile> tiles;
    private Set<InfluenceArea> influenceAreas;

    public LocalWorld(Map<Coordinate, LocalWorldTile> tiles, Set<InfluenceArea> influenceAreas) {
        this.tiles = tiles;
        this.influenceAreas = influenceAreas;
        buildNeighborList();
    }

    private void buildNeighborList() {
        for(Map.Entry<Coordinate, LocalWorldTile> entry: tiles.entrySet()) {
            Coordinate coordinate = entry.getKey();
            for(Direction direction: Direction.values()) {
                LocalWorldTile neighbor = tiles.get(coordinate.getNeighbor(direction));
                entry.getValue().setNeighbor(direction, neighbor);
            }
        }
    }

    @Override
    public void notifySpawn(InfluenceArea IA, GameObject spawner) {
        influenceAreas.add(IA);
    }

    @Override
    public void update() {
        updatePhase();
        movementPhase();
        interactionPhase();
    }

    private void updatePhase() {
        for(InfluenceArea IA: influenceAreas) {
            IA.update(tiles);
        }
        for(LocalWorldTile tile: tiles.values()) {
            tile.do_update(getTileMap ());
        }
    }

    private void movementPhase() {
        Set<MoveLegalityChecker> updated = new HashSet<>();
        for(LocalWorldTile tile: tiles.values()) {
            tile.do_moves(updated);
        }
    }

    private void interactionPhase() {
        for(InfluenceArea IA: influenceAreas) {
            IA.update(tiles);
        }
        for(LocalWorldTile tile: tiles.values()) {
            tile.do_interactions();
        }
    }


    @Override
    public void add(Coordinate p, Entity e) {
        tiles.get(p).setEntity(e);
    }

    @Override
    public Map<Coordinate, GameObjectContainer> getMap() {
        Map<Coordinate, GameObjectContainer> ret = new HashMap<>();
        for(Coordinate c : tiles.keySet())
        {
            ret.put(c, tiles.get(c));
        }
        return ret;
    }

    @Override
    public Map<Coordinate, GameObject> getInfluences() {
        Map<Coordinate, GameObject> iaMap = new HashMap<Coordinate,GameObject>();
        for(InfluenceArea ia : influenceAreas){
            List<Coordinate> affectedCoords = ia.getAffectedCoordinates();
            for(Coordinate coord : affectedCoords){
                iaMap.put(coord,ia);
            }
        }
        return iaMap;
    }


    public LocalWorldTile getTile(Coordinate c) {
        return tiles.getOrDefault(c, null);
    }

    @Override
    public Tile getTileForEntity(Entity e) {
        for(LocalWorldTile tile: tiles.values()) {
            if(tile.has(e)) {
                return tile;
            }
        }
        return null;
    }


    @Override
    public void remove(Entity e) {
        for(LocalWorldTile tile: tiles.values()) {
            if(tile.remove(e))
                return;
        }
    }

    @Override
    public Coordinate getEntityCoordinate(Entity e) {
        for(Map.Entry<Coordinate, LocalWorldTile> entry: tiles.entrySet()) {
            if(entry.getValue().has(e)) {
                return new Coordinate(entry.getKey());
            }
        }
        return null;
    }

    public Set<InfluenceArea> getInfluenceAreas() {
        return influenceAreas;
    }

    @Override
    public Tile getTileForCoordinate(Coordinate c) {
        return tiles.get(c);
    }

    public Map<Coordinate, LocalWorldTile> getTiles() {
        return tiles;
    }

    @Override
    public Map <Coordinate, Tile> getTileMap () {
        HashMap<Coordinate, Tile> temp = new HashMap<>();
        for (Coordinate c : tiles.keySet()) {
            temp.put (c, tiles.get(c));
        }
        return temp;
    }

    @Override
    public void accept(Visitor v) {
        v.visitLocalWorld(this);
    }
}
