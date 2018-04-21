package mapstests.entityimpactiontests;

import commands.ModifyHealthCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import maps.entityimpaction.Trap;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import maps.world.LocalWorld;
import org.junit.Assert;
import org.junit.Test;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TrapTests {

    @Test
    public void testNonSneak() {
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
        Entity entity = new Entity(new Vector(), entityStats, new ArrayList<>(), null,
                null, true, "Default");

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), new ArrayList<>(), null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        Trap trap = new Trap(new ModifyHealthCommand(-10), false, 5, false);

        tiles.get(new Coordinate(2, 1)).getMoveLegalityCheckers ().add(trap);
        tiles.get(new Coordinate(2, 1)).addEI (trap);

        Assert.assertFalse(trap.hasFired());
        Assert.assertFalse(trap.isVisible());

        //won't do anything since entity doesn't have the right skill
        entity.startSearching();
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

        Assert.assertEquals(90, entity.getCurrHealth());

        world.update();
        Assert.assertEquals(90, entity.getCurrHealth());
    }

    @Test
    public void testSneak() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        Map<SkillType, Integer> skills = new HashMap<>();
        skills.put(SkillType.DETECTANDREMOVETRAP, 1);
        EntityStats entityStats = new EntityStats(skills, 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false, new HashSet<>());
        entityStats.addCompatibleTerrain(Terrain.GRASS);
        Entity entity = new Entity(new Vector(), entityStats, new ArrayList<>(), null,
                null, true, "Default");


        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), new ArrayList<>(), null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        Trap trap = new Trap(new ModifyHealthCommand(-10), false, 200, false);

        tiles.get(new Coordinate(2, 1)).getMoveLegalityCheckers ().add (trap);
        tiles.get(new Coordinate(2, 1)).addEI(trap);

        Assert.assertFalse(trap.hasFired());
        Assert.assertFalse(trap.isVisible());

        entity.startSearching();
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

        Assert.assertFalse(trap.hasFired());
        Assert.assertTrue(trap.isVisible());
        Assert.assertEquals(100, entity.getCurrHealth());

        entity.setFacing(Direction.N);
        entity.setMoving();
        world.update();

        Assert.assertTrue(trap.hasFired());
        Assert.assertTrue(trap.isVisible());
        Assert.assertEquals(90, entity.getCurrHealth());
    }
}
