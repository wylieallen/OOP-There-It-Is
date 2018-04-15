package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import maps.tile.Tile;
import utilities.Coordinate;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public interface World {

    void update ();
    void add (Coordinate p, Entity e);
    void remove(Entity e);
    Map <Coordinate, GameObjectContainer> getMap ();
    Tile getTileForEntity (Entity e);

}
