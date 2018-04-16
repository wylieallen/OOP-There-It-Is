package mapstests.entityimpactiontests;

import commands.skillcommands.ModifyHealthCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import maps.entityimpaction.AreaEffect;
import maps.entityimpaction.InfiniteAreaEffect;
import maps.entityimpaction.OneShotAreaEffect;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.world.LocalWorld;
import org.junit.Assert;
import org.junit.Test;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

public class AreaEffectTests {
    @Test
    public void testInfiniteAreaEffect() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>(), new ArrayList<>());

        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false);
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                null, true);

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), null, null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        entity.hurtEntity(50);
        AreaEffect ae = new InfiniteAreaEffect(new ModifyHealthCommand(SkillType.NULL, 0, 20));

        tiles.get(new Coordinate(2, 1)).addEI(ae);

        //won't do anything since entity doesn't have the right skill
        entity.startSearching();
        entity.setFacing(Direction.N);
        entity.setMoving();

        world.update();

        Assert.assertEquals(70, entity.getCurrHealth());

        world.update();
        Assert.assertEquals(90, entity.getCurrHealth());

        world.update();
        Assert.assertEquals(100, entity.getCurrHealth());
    }

    @Test
    public void testOneShotAreaEffect() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world = new LocalWorld(tiles, new HashSet<>(), new ArrayList<>());

        EntityStats entityStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false);
        Entity entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                null, true);

        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), null, null);

        entity.setController(entityController);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        entity.hurtEntity(50);
        AreaEffect ae = new OneShotAreaEffect(new ModifyHealthCommand(SkillType.NULL, 0, 20), false);

        tiles.get(new Coordinate(2, 1)).addEI(ae);

        //won't do anything since entity doesn't have the right skill
        entity.startSearching();
        entity.setFacing(Direction.N);
        entity.setMoving();

        world.update();

        Assert.assertEquals(70, entity.getCurrHealth());

        world.update();
        Assert.assertEquals(70, entity.getCurrHealth());

        world.update();
        Assert.assertEquals(70, entity.getCurrHealth());
    }

}
