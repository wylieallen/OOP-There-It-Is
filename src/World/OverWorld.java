package World;

import entitymodel.Entity;
import gameobject.GameObjectContainer;
import Tiles.OverWorldTile;
import Tiles.Tile;
import Utilities.Coordinate;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class OverWorld implements World {

    private Map <Coordinate, OverWorldTile> tiles;

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
