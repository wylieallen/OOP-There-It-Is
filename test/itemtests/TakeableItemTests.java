package itemtests;

import commands.reversiblecommands.TimedStaminaRegenCommand;
import commands.skillcommands.ModifyHealthCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.*;
import items.takeableitems.ConsumableItem;
import items.takeableitems.QuestItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.Influence.InfluenceType;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.world.LocalWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import skills.SkillType;
import spawning.SpawnObserver;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

public class TakeableItemTests {

    private static LocalWorld world;
    private static Map<Coordinate, LocalWorldTile> tiles;
    private static Entity entity;
    private static Inventory inventory;
    private static Equipment equipment;

    @BeforeClass
    public static void setup(){
        tiles = new HashMap<>();
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                tiles.put(new Coordinate(i, j), new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>()));
            }
        }
        world = new LocalWorld(tiles, new HashSet<>());

        Map<SkillType, Integer> skills = new HashMap<>();
        skills.put(SkillType.ONEHANDEDWEAPON, 1);
        EntityStats entityStats = new EntityStats(skills, 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false);
        entityStats.addCompatibleTerrain(Terrain.GRASS);
        inventory = new Inventory();
        entity = new Entity(new Vector(), entityStats, null, new ArrayList<>(), null,
                inventory, true);

        equipment = new Equipment(new HashMap<>(), new WeaponItem[5], 5, inventory, entity);
        equipment.addSpawnObserver(world);
        EntityController entityController = new HumanEntityController(entity, null,
                new Coordinate(2, 2), null, null);

        entity.setController(entityController);

    }

    @Test
    public void testConsumableItem() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                LocalWorldTile tile = tiles.get(new Coordinate(i, j));
                tile.remove(entity);
            }
        }
        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        ConsumableItem item = new ConsumableItem("Health Potion", true,
                new ModifyHealthCommand(SkillType.NULL, 0, 10));

        tiles.get(new Coordinate(2, 1)).addEI(item);

        entity.hurtEntity(20);

        entity.setFacing(Direction.N);
        entity.setMoving();

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

        Assert.assertTrue(inventory.contains(item));

        equipment.consume(item);

        Assert.assertFalse(inventory.contains(item));
        Assert.assertEquals(90, entity.getCurrHealth());

    }

    @Test
    public void testQuestItem() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                LocalWorldTile tile = tiles.get(new Coordinate(i, j));
                tile.remove(entity);
            }
        }
        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        QuestItem item = new QuestItem("Health Potion", true, 1);

        tiles.get(new Coordinate(2, 1)).addEI(item);

        entity.setFacing(Direction.N);
        entity.setMoving();

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

        Assert.assertTrue(inventory.contains(item));
    }

    @Test
    public void testWeaponItem() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                LocalWorldTile tile = tiles.get(new Coordinate(i, j));
                tile.remove(entity);
            }
        }
        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        WeaponItem item = new WeaponItem("Sword", true, 10,0,
                SkillType.ONEHANDEDWEAPON, 1, 0, 0,
                InfluenceType.LINEARINFLUENCE, new ModifyHealthCommand(SkillType.ONEHANDEDWEAPON, 1, 10));
        item.registerObserver(world);

        tiles.get(new Coordinate(2, 1)).addEI(item);

        entity.setFacing(Direction.N);
        entity.setMoving();

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

        Assert.assertTrue(inventory.contains(item));

        equipment.add(item);
        Assert.assertFalse(inventory.contains(item));

        entity.setFacing(Direction.S);
        item.attack(entity, new Coordinate(2, 1));

        Assert.assertEquals(1, world.getInfluenceAreas().size());
    }

    @Test
    public void testWearableItem() {
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                LocalWorldTile tile = tiles.get(new Coordinate(i, j));
                tile.remove(entity);
            }
        }
        tiles.get(new Coordinate(2, 2)).setEntity(entity);

        WearableItem item = new WearableItem("Armor", true,
                new TimedStaminaRegenCommand(false, 0, 2),
                EquipSlot.ARMOUR);

        tiles.get(new Coordinate(2, 1)).addEI(item);

        entity.setFacing(Direction.N);
        entity.setMoving();

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

        Assert.assertTrue(inventory.contains(item));

        equipment.add(item);
        Assert.assertFalse(inventory.contains(item));

        Assert.assertEquals(10, entity.getManaRegenRate());
    }
}
