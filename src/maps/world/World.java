package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import maps.tile.Tile;
import savingloading.Visitable;
import utilities.Coordinate;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public interface World extends Visitable {

    void update ();
    void add (Coordinate p, Entity e);
    void remove(Entity e);
    Map <Coordinate, GameObjectContainer> getMap ();
    Tile getTileForEntity (Entity e);
    Coordinate getEntityCoordinate(Entity e);
    Tile getTileForCoordinate(Coordinate c);
}
