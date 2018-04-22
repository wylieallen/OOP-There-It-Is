package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.Influence.InfluenceArea;
import maps.tile.Tile;
import savingloading.Visitable;
import spawning.SpawnObservable;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public interface World extends SpawnObserver, Visitable {

    void update ();
    void add (Coordinate p, Entity e);
    void remove(Entity e);
    Map <Coordinate, GameObjectContainer> getMap ();
    Map <Coordinate, GameObject> getInfluences();
    Map <Coordinate, Tile> getTileMap ();
    Tile getTileForEntity (Entity e);
    Coordinate getEntityCoordinate(Entity e);
    void notifySpawn(InfluenceArea IA, SpawnObservable spawner);
    Tile getTileForCoordinate(Coordinate c);
}
