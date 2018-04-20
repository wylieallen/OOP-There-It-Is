package mapstests.worldtests;

import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import maps.world.OverWorld;
import org.junit.Assert;
import org.junit.Test;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OverWorldTests {

    @Test
    public void testConstruction()
    {
        Map<Coordinate, OverWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new OverWorldTile(new HashSet<>(), null));
            }
        }
        OverWorld world = new OverWorld(tiles);

        for(int i = 0; i < 5; ++i) {
            for(int j = 1; j < 5; ++j) {
                Tile tile = world.getTile(new Coordinate(i, j));
                Tile actualNeighbor = tile.getNeighbor(Direction.N);
                Tile expectedNeighbor = world.getTile(new Coordinate(i, j-1));
                Assert.assertEquals(actualNeighbor, expectedNeighbor);
            }
        }

        for(int i = 0; i < 4; ++i) {
            for(int j = 1; j < 5; ++j) {
                Tile tile = world.getTile(new Coordinate(i, j));
                Tile actualNeighbor = tile.getNeighbor(Direction.NE);
                Tile expectedNeighbor = world.getTile(new Coordinate(i+1, j-1));
                Assert.assertEquals(actualNeighbor, expectedNeighbor);
            }
        }

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = world.getTile(new Coordinate(i, j));
                Tile actualNeighbor = tile.getNeighbor(Direction.SE);
                Tile expectedNeighbor = world.getTile(new Coordinate(i+1, j));
                Assert.assertEquals(actualNeighbor, expectedNeighbor);
            }
        }

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 4; ++j) {
                Tile tile = world.getTile(new Coordinate(i, j));
                Tile actualNeighbor = tile.getNeighbor(Direction.S);
                Tile expectedNeighbor = world.getTile(new Coordinate(i, j+1));
                Assert.assertEquals(actualNeighbor, expectedNeighbor);
            }
        }

        for(int i = 1; i < 5; ++i) {
            for(int j = 0; j < 4; ++j) {
                Tile tile = world.getTile(new Coordinate(i, j));
                Tile actualNeighbor = tile.getNeighbor(Direction.SW);
                Tile expectedNeighbor = world.getTile(new Coordinate(i-1, j+1));
                Assert.assertEquals(actualNeighbor, expectedNeighbor);
            }
        }

        for(int i = 1; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = world.getTile(new Coordinate(i, j));
                Tile actualNeighbor = tile.getNeighbor(Direction.NW);
                Tile expectedNeighbor = world.getTile(new Coordinate(i-1, j));
                Assert.assertEquals(actualNeighbor, expectedNeighbor);
            }
        }
    }

    @Test
    public void testMovement()
    {
        Map<Coordinate, OverWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new OverWorldTile(new HashSet<>(), null));
            }
        }

        OverWorld world = new OverWorld(tiles);

        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                                        100, 100, 100, 5, 0, 0,
                                    3, 3, 0, false, false, new HashSet<>());
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                                    null, true, new ArrayList<>());

        EntityController entityController = new HumanEntityController(entity, null,
                                                new Coordinate(2, 2), null, null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = tiles.get(new Coordinate(i, j));
                if(i == 2 && j == 2) {
                    Assert.assertTrue(tile.has(entity));
                } else {
                    Assert.assertFalse(tile.has(entity));
                }
            }
        }

        entity.setFacing(Direction.N);
        entity.setMoving();
        world.update();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = tiles.get(new Coordinate(i, j));
                if(i == 2 && j == 1) {
                    Assert.assertTrue(tile.has(entity));
                } else {
                    Assert.assertFalse(tile.has(entity));
                }
            }
        }

        entity.setFacing(Direction.NE);
        entity.setMoving();
        world.update();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = tiles.get(new Coordinate(i, j));
                if(i == 3 && j == 0) {
                    Assert.assertTrue(tile.has(entity));
                } else {
                    Assert.assertFalse(tile.has(entity));
                }
            }
        }

    }
}