package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.Influence.InfluenceArea;
import maps.tile.Tile;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public interface World extends SpawnObserver {

    void update ();
    void add (Coordinate p, Entity e);
    void remove(Entity e);
    Map <Coordinate, GameObjectContainer> getMap ();
    Tile getTileForEntity (Entity e);
    Coordinate getEntityCoordinate(Entity e);
    void notifySpawn(InfluenceArea IA, GameObject spawner);
}
