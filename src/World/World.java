package World;

import EnitityModel.Entity;

import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public interface World {

    public void update ();
    public void add (Coordinate p, Entity e);
    public Map <Coordinate, GameObjectContainer> getMap ();
    public Tile getTileForEntity (Entity e);

}
