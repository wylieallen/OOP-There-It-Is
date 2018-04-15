package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import maps.tile.OverWorldTile;
import spawning.SpawnEvent;
import spawning.SpawnObserver;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dontf on 4/14/2018.
 */
public class LocalWorld implements World, SpawnObserver {

    private Map <Coordinate, LocalWorldTile> tiles;
    private Set<InfluenceArea> influenceAreas;
    private List<SpawnEvent> spawnEvents;

    @Override
    public void notifySpawn(InfluenceArea IA, GameObject spawner) {

    }

    @Override
    public void update() {

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

    @Override
    public void remove(Entity e) {
        for(LocalWorldTile tile: tiles.values()) {
            if(tile.remove(e))
                return;
        }
    }
}
