package itemtests;

import commands.TransitionCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import items.InteractiveItem;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import org.junit.Assert;
import org.junit.Test;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

public class InteractiveItemTests {

    @Test
    public void TestTransitionItem() {
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

        Map<Coordinate, LocalWorldTile> tiles2 = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles2.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), null, new HashSet<>(), new HashSet<>()));
            }
        }
        LocalWorld world2 = new LocalWorld(tiles2, new HashSet<>(), new ArrayList<>());

        Map<Coordinate, OverWorldTile> tiles3 = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles3.put(new Coordinate(i, j), new OverWorldTile(new HashSet<>(), null));
            }
        }
        OverWorld overworld = new OverWorld(tiles3);

        List<LocalWorld> worlds = new ArrayList<>();
        worlds.add(world);
        worlds.add(world2);

        Game game = new Game(world, overworld, worlds, 0, entity);

        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        InteractiveItem item = new InteractiveItem("Door",
                new TransitionCommand(world2, new Coordinate(0, 0), game));
        tiles.get(new Coordinate(2, 1)).addEI(item);

        entity.setFacing(Direction.N);
        entity.setMoving();

        game.update();


        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = tiles.get(new Coordinate(i, j));
                    Assert.assertFalse(tile.has(entity));
            }
        }
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                Tile tile = tiles2.get(new Coordinate(i, j));
                if(i == 0 && j == 0) {
                    Assert.assertTrue(tile.has(entity));
                } else {
                    Assert.assertFalse(tile.has(entity));
                }
            }
        }
    }
}
