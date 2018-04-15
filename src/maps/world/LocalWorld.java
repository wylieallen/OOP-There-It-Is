package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.tile.Direction;
import spawning.SpawnEvent;
import spawning.SpawnObserver;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import utilities.Coordinate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dontf on 4/14/2018.
 */
public class LocalWorld implements World, SpawnObserver {

    private Map<Coordinate, LocalWorldTile> tiles;
    private Set<InfluenceArea> influenceAreas;
    private List<SpawnEvent> spawnEvents;

    public LocalWorld(Map<Coordinate, LocalWorldTile> tiles, Set<InfluenceArea> influenceAreas, List<SpawnEvent> spawnEvents) {
        this.tiles = tiles;
        this.influenceAreas = influenceAreas;
        this.spawnEvents = spawnEvents;
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

    }

    @Override
    public void update() {
        updatePhase();
        movementPhase();
        interactionPhase();
    }

    private void updatePhase() {
        for(LocalWorldTile tile: tiles.values()) {
            tile.do_update();
        }
    }

    private void movementPhase() {
        Set<MoveLegalityChecker> updated = new HashSet<>();
        for(LocalWorldTile tile: tiles.values()) {
            tile.do_moves(updated);
        }
    }

    private void interactionPhase() {
        for(LocalWorldTile tile: tiles.values()) {
            tile.do_interactions();
        }
    }


    @Override
    public void add(Coordinate p, Entity e) {

    }

    @Override
    public Map<Coordinate, GameObjectContainer> getMap() {
        return null;
    }

    public LocalWorldTile getTile(Coordinate c) {
        return tiles.getOrDefault(c, null);
    }

    @Override
    public Tile getTileForEntity(Entity e) {
        return null;
    }

    @Override
    public void remove(Entity e) {
        for(LocalWorldTile tile: tiles.values()) {
            if(tile.remove(e))
                return;
        }
    }
}
