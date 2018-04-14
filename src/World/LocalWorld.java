package World;

import entitymodel.Entity;
import gameobject.GameObjectContainer;
import gameobject.GameObject;
import Influence.InfluenceArea;
import Spawning.SpawnEvent;
import Spawning.SpawnObserver;
import Tiles.LocalWorldTile;
import Tiles.Tile;
import Utilities.Coordinate;

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
}
