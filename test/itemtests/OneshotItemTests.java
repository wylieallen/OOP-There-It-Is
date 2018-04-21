package itemtests;

import commands.ModifyHealthCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import items.OneshotItem;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
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

public class OneshotItemTests {

    @Test
    public void testOneshotItem(){
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false);
        entityStats.addCompatibleTerrain(Terrain.GRASS);
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                null, true);

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), new ArrayList<>(), null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        OneshotItem item = new OneshotItem("Health Potion",
                new ModifyHealthCommand(10), false);
        tiles.get(new Coordinate(2, 1)).addEI(item);

        entity.setFacing(Direction.N);
        entity.setMoving();
        entity.hurtEntity(20);

        world.update();

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                LocalWorldTile tile = tiles.get(new Coordinate(i, j));
                if(i == 2 && j == 1) {
                    Assert.assertTrue(tile.has(entity));
                } else {
                    Assert.assertFalse(tile.has(entity));
                }
                Assert.assertFalse(tile.has(item));
            }
        }
        Assert.assertEquals(90, entity.getCurrHealth());
    }
}
