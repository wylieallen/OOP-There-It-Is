package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import maps.tile.Tile;
import utilities.Coordinate;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class FoggyWorld implements World {

    private World world;
    private Entity entity;

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