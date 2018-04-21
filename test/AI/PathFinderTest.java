package AI;

import entity.entitycontrol.AI.aiutilities.PathFinder;
import entity.entitymodel.Entity;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Coordinate;
import utilities.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dontf on 4/19/2018.
 */
public class PathFinderTest {

    private static Map<Coordinate, Tile> map = new HashMap <>();
    private static Coordinate start;
    private static Coordinate end;

    private static Set<Terrain> grass = new HashSet<>();
    private static Set<Terrain> mountain = new HashSet<>();
    private static Set<Terrain> water = new HashSet<>();
    private static Set<Terrain> grass_mountain = new HashSet<>();
    private static Set<Terrain> grass_water = new HashSet<>();
    private static Set<Terrain> mountain_water = new HashSet<>();
    private static Set<Terrain> grass_mountain_water = new HashSet<>();

    @BeforeClass
    public static void setUpMap () {

        grass.add(Terrain.GRASS);

        mountain.add(Terrain.MOUNTAIN);

        water.add(Terrain.WATER);

        grass_mountain.add(Terrain.GRASS);
        grass_mountain.add(Terrain.MOUNTAIN);

        grass_water.add(Terrain.GRASS);
        grass_water.add(Terrain.WATER);

        mountain_water.add(Terrain.MOUNTAIN);
        mountain_water.add(Terrain.WATER);

        grass_mountain_water.add(Terrain.GRASS);
        grass_mountain_water.add(Terrain.MOUNTAIN);
        grass_mountain_water.add(Terrain.WATER);


        LocalWorldTile grass0 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass1 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass2 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass3 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass4 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass5 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass6 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass7 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass8 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass9 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile grass10 = new LocalWorldTile(new HashSet<> (), Terrain.GRASS, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile water0 = new LocalWorldTile(new HashSet<> (), Terrain.WATER, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile water1 = new LocalWorldTile(new HashSet<> (), Terrain.WATER, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile mountain0 = new LocalWorldTile(new HashSet<> (), Terrain.MOUNTAIN, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);
        LocalWorldTile mountain1 = new LocalWorldTile(new HashSet<> (), Terrain.MOUNTAIN, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false), null, null);


        map.put(new Coordinate(-2, 0), grass0);
        map.put(new Coordinate(-1, -1), grass1);
        map.put(new Coordinate(0, -1), grass2);
        map.put(new Coordinate(1, -2), grass3);
        map.put(new Coordinate(-2, 1), grass4);
        map.put(new Coordinate(1, -1), grass5);
        map.put(new Coordinate(-2, 2), grass6);
        map.put(new Coordinate(-1, 1), grass7);
        map.put(new Coordinate(1, 0), grass8);
        map.put(new Coordinate(0, 2), grass9);
        map.put(new Coordinate(1, 1), grass10);

        map.put(new Coordinate(-1, 0), mountain0);
        map.put(new Coordinate(0, 0), mountain1);

        map.put(new Coordinate(0, 1), water0);
        map.put(new Coordinate(-1, 2), water1);
    }

    @Test
    public void invalidStart () {
        start = new Coordinate(-5, -5);
        end = new Coordinate(0, 0);

       HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass, map);
       Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void invalidEnd () {
        start = new Coordinate(0, 0);
        end = new Coordinate(-6, 10);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass, map);
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void emptyCompatibleList () {
        start = new Coordinate(-1, -1);
        end = new Coordinate(0, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, new HashSet<>(), map);
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void emptyMap () {
        start = new Coordinate(-1, -1);
        end = new Coordinate(0, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass, new HashMap<Coordinate, Tile>());
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void pathFindForValidGrass () {
        start = new Coordinate(-2, 2);
        end = new Coordinate(1, -2);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 6);
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 2)));
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 0)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, -1)));
        Assert.assertTrue(path.containsKey(new Coordinate(0, -1)));
        Assert.assertTrue(path.containsKey(new Coordinate(1, -2)));
    }

    @Test
    public void pathFindForInvalidGrass () {
        start = new Coordinate(-2, 2);
        end = new Coordinate(-1, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass, map);
        for (Coordinate c : path.keySet()) {
            System.out.println("x: " + c.x() + " z: " + c.z());
        }
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void pathFindForMountainOnly () {
        start = new Coordinate(-1, 0);
        end = new Coordinate(0, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, mountain, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 2);
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 0)));
        Assert.assertTrue(path.containsKey(new Coordinate(0, 0)));
    }

    @Test
    public void pathFindForWaterOnly () {
        start = new Coordinate(0, 1);
        end = new Coordinate(-1, 2);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, water, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 2);
        Assert.assertTrue(path.containsKey(new Coordinate(0, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 2)));
    }

    @Test
    public void pathFindForGrassAndMountain () {
        start = new Coordinate(-2, 2);
        end = new Coordinate(0, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass_mountain, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 3);
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 2)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(0, 0)));
    }

    @Test
    public void pathFindForGrassAndWater () {
        start = new Coordinate(-1, -1);
        end = new Coordinate(-1, 2);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass_water, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 5);
        Assert.assertTrue(path.containsKey(new Coordinate(-1, -1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 0)));
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-2, 2)) || path.containsKey(new Coordinate(-1, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 2)));
    }

    @Test
    public void pathFindForMountainAndWater () {
        start = new Coordinate(-1, 2);
        end = new Coordinate(-1, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, mountain_water, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 4);
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 0)));
        Assert.assertTrue(path.containsKey(new Coordinate(0, 0)));
        Assert.assertTrue(path.containsKey(new Coordinate(0, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 2)));
    }

    @Test
    public void pathFindForGrassAndMountainAndWater () {
        start = new Coordinate(-1, -1);
        end = new Coordinate(-1, 2);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass_mountain_water, map);
        Assert.assertFalse(path.isEmpty());
        Assert.assertTrue(path.size() == 4);
        Assert.assertTrue(path.containsKey(new Coordinate(-1, -1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 0)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 1)));
        Assert.assertTrue(path.containsKey(new Coordinate(-1, 2)));
    }
}
