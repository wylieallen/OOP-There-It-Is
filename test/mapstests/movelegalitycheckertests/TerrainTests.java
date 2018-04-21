package mapstests.movelegalitycheckertests;

import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.world.Game;
import maps.world.LocalWorld;
import org.junit.Assert;
import org.junit.Test;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

public class TerrainTests {

    @Test
    public void testTerrain() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                LocalWorldTile tile;
                if(i == 2 && j == 0){
                    tile = new LocalWorldTile(new HashSet<>(), Terrain.WATER, null, new HashSet<>(), new HashSet<>());
                } else if (i == 1 && j == 1) {
                    tile = new LocalWorldTile(new HashSet<>(), Terrain.MOUNTAIN, null, new HashSet<>(), new HashSet<>());
                } else {
                    tile = new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>());
                }
                tiles.put(new Coordinate(i, j), tile);
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        Set<Terrain> terrains = new HashSet<>();
        terrains.add(Terrain.GRASS);
        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false, terrains);
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                null, true);

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), null, null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
        entity.setFacing(Direction.N);
        entity.setMoving();

        world.update();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                if(i == 2 && j == 1) {
                    Assert.assertTrue(tiles.get(new Coordinate(i, j)).has(entity));
                } else {
                    Assert.assertFalse(tiles.get(new Coordinate(i, j)).has(entity));
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
                if(i == 2 && j == 1) {
                    Assert.assertTrue(tiles.get(new Coordinate(i, j)).has(entity));
                } else {
                    Assert.assertFalse(tiles.get(new Coordinate(i, j)).has(entity));
                }
            }
        }

        try {
            Thread.sleep(600);
        } catch (Exception e) {}
        Game.updateGameTime();
        entity.setFacing(Direction.NW);
        entity.setMoving();

        world.update();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                if(i == 2 && j == 1) {
                    Assert.assertTrue(tiles.get(new Coordinate(i, j)).has(entity));
                } else {
                    Assert.assertFalse(tiles.get(new Coordinate(i, j)).has(entity));
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
                if(i == 3 && j == 0) {
                    Assert.assertTrue(tiles.get(new Coordinate(i, j)).has(entity));
                } else {
                    Assert.assertFalse(tiles.get(new Coordinate(i, j)).has(entity));
                }
            }
        }
    }
}
