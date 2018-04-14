package World;

import EnitityModel.Entity;
import Spawning.SpawnObserver;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class LocalWorld implements World, SpawnObserver {

    private Map <Coordinate, LocalWorldTile> tiles;
    private Set <InfluenceAreas> influenceAreas;
    private List <SpawnEvent>

    @Override
    public void notifySpawn(InfluenceArea IA, GameObject spanwer) {

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
