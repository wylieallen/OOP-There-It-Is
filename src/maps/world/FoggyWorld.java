package maps.world;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.Influence.InfluenceArea;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class FoggyWorld implements World {

    private World world;
    private Entity entity;

    public FoggyWorld(World world, Entity entity) {
        this.world = world;
        this.entity = entity;
    }

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void add(Coordinate p, Entity e) {
        world.add(p, e);
    }

    @Override
    public Map<Coordinate, GameObjectContainer> getMap() {
        Map<Coordinate, GameObjectContainer> wholeMap = world.getMap();
        Coordinate entityCoordinate = world.getEntityCoordinate(entity);

        Map<Coordinate, GameObjectContainer> visibleTiles = new HashMap<>();

        for(Map.Entry<Coordinate, GameObjectContainer> entry: wholeMap.entrySet()) {
            int distance = entry.getKey().distance(entityCoordinate);
            if(distance <= entity.getVisibilityRadius()) {
                visibleTiles.put(entry.getKey(), entry.getValue());
            }
        }

        return visibleTiles;
    }

    @Override
    public Tile getTileForEntity(Entity e) {
        return world.getTileForEntity(e);
    }

    @Override
    public void remove(Entity e){
        world.remove(e);
    }

    @Override
    public Coordinate getEntityCoordinate(Entity e) {
        return world.getEntityCoordinate(e);
    }

    @Override
    public Tile getTileForCoordinate(Coordinate c) {
        return null;
    }

    @Override
    public void accept(Visitor v) { }

    @Override
    public void notifySpawn(InfluenceArea IA, GameObject spawner) {
        world.notifySpawn(IA, spawner);
    }

}
