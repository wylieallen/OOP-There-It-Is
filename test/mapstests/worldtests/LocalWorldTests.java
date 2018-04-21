package mapstests.worldtests;

import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import maps.trajectorymodifier.River;
import maps.world.Game;
import maps.world.LocalWorld;
import org.junit.Assert;
import org.junit.Test;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LocalWorldTests {

    @Test
    public void testConstruction()
    {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

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
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false, new HashSet<>());
        entityStats.addCompatibleTerrain(Terrain.GRASS);
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                null, true);

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), null);

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

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
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

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
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

    @Test
    public void testExternalForceMovement()
    {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false, new HashSet<>());
        entityStats.addCompatibleTerrain(Terrain.GRASS);
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                null, true);

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        River river = new River(new Vector(Direction.N, 2));
        tiles.get(new Coordinate(2, 2)).addTM(river);

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

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
        entity.setFacing(Direction.S);
        entity.setMoving();
        world.update();

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

        river = new River(new Vector(Direction.S, 5));
        tiles.get(new Coordinate(2, 1)).addTM(river);

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
        entity.setFacing(Direction.N);
        entity.setMoving();
        world.update();

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

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
        entity.setFacing(Direction.SE);
        entity.setMoving();
        world.update();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = tiles.get(new Coordinate(i, j));
                if(i == 3 && j == 1) {
                    Assert.assertTrue(tile.has(entity));
                } else {
                    Assert.assertFalse(tile.has(entity));
                }
            }
        }


    }
}
