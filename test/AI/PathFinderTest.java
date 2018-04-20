package AI;

import entity.entitycontrol.AI.aiutilities.PathFinder;
import entity.entitymodel.Entity;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.*;
import utilities.Vector;

import java.util.*;

/**
 * Created by dontf on 4/19/2018.
 */
public class PathFinderTest {

    private static Map<Coordinate, LocalWorldTile> map = new HashMap <>();
    private static Coordinate start;
    private static Coordinate end;

    private static List<Terrain> grass = new ArrayList <> ();
    private static List<Terrain> mountain = new ArrayList <> ();
    private static List<Terrain> water = new ArrayList <> ();
    private static List<Terrain> grass_mountain = new ArrayList <> ();
    private static List<Terrain> grass_water = new ArrayList <> ();
    private static List<Terrain> mountain_water = new ArrayList <> ();
    private static List<Terrain> grass_mountain_water = new ArrayList <> ();

    @BeforeClass
    public static void setUpMap () {

        Set <MoveLegalityChecker> mlc_grass = new HashSet<>();
        mlc_grass.add(Terrain.GRASS);
        Set <MoveLegalityChecker> mlc_mountain = new HashSet<>();
        mlc_mountain.add(Terrain.MOUNTAIN);
        Set <MoveLegalityChecker> mlc_water = new HashSet<>();
        mlc_water.add(Terrain.WATER);

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


        LocalWorldTile grass0 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass1 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass2 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass3 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass4 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass5 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass6 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass7 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass8 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass9 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile grass10 = new LocalWorldTile(mlc_grass, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile water0 = new LocalWorldTile(mlc_water, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile water1 = new LocalWorldTile(mlc_water, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile mountain0 = new LocalWorldTile(mlc_mountain, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);
        LocalWorldTile mountain1 = new LocalWorldTile(mlc_mountain, new Entity(new Vector(Direction.N, 5), null, null, null, null, null, false, null), null, null);


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

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, new ArrayList<Terrain>(), map);
        Assert.assertTrue(path.isEmpty());
    }

    @Test
    public void emptyMap () {
        start = new Coordinate(-1, -1);
        end = new Coordinate(0, 0);

        HashMap <Coordinate, Direction> path = PathFinder.createLocalPath(start, end, grass, new HashMap<Coordinate, LocalWorldTile>());
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
