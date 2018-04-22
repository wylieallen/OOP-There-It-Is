package entitymodel;

import commands.TimedEffect;
import commands.reversiblecommands.MakeParalyzedCommand;
import commands.ParalyzeCommand;
import commands.skillcommands.SkillCommand;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.*;
import entity.entitymodel.interactions.EntityInteraction;
import items.takeableitems.TakeableItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.Influence.InfluenceType;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import skills.SkillType;
import utilities.Vector;

import java.util.*;

/**
 * Created by dontf on 4/14/2018.
 */
public class EquipmentTest {

    private static Equipment equipment;
    private static Map<EquipSlot, WearableItem> wearables;
    private static WeaponItem [] weapons;
    private static Inventory inventory;
    private static List<TakeableItem> items;

    @BeforeClass
    public static void setUpEquipment () {

        items = new ArrayList<>();
        weapons = new WeaponItem[5];
        wearables = new HashMap<>();
        inventory = new Inventory(items);

        HashMap<SkillType, Integer> skillsActor = new HashMap<>();
        EntityStats actorStats = new EntityStats(skillsActor, 1001, 100, 85, 100, 55, 5, 25, 5, 5, 50, 65, false, false, new HashSet<>());
        ArrayList<ControllerAction> actorActions = new ArrayList<>();
        ArrayList <TimedEffect> actorEffects = new ArrayList<>();
        ArrayList <EntityInteraction> actorActorInteractions = new ArrayList<>();
        //EntityController actorController = new HumanEntityController(null, null, null, null, null);

        Entity e = new Entity(new Vector(Direction.N, 0), actorStats, actorEffects, actorActorInteractions, inventory, true, "default");

        equipment = new Equipment(wearables, weapons, 5, inventory, e);

    }

    @Test
    public void testWeaponAddAndRemove () {
        WeaponItem weapon = new WeaponItem("Sword", true, 10,
                SkillType.BANE, 0, 1, 0, 0, 300, InfluenceType.LINEARINFLUENCE,
                new SkillCommand(SkillType.BANE, 1, 1, new ParalyzeCommand(10), null), false);

        items.add(weapon);
        equipment.add(weapon);

        Assert.assertSame(weapon, weapons[0]);
        Assert.assertFalse (items.contains(weapon));

        equipment.remove(weapon);

        Assert.assertNull(weapons[0]);
        Assert.assertTrue(items.contains(weapon));
    }

    @Test
    public void testWearableAddAndRemove () {
        WearableItem wearable = new WearableItem("ACDC shirt", true, new MakeParalyzedCommand(false), EquipSlot.ARMOUR);

        items.add(wearable);
        equipment.add(wearable);

        Assert.assertTrue(wearables.containsKey(EquipSlot.ARMOUR));
        Assert.assertSame(wearable, wearables.get(EquipSlot.ARMOUR));
        Assert.assertFalse (items.contains(wearable));

        equipment.remove(wearable);

        Assert.assertFalse(wearables.containsValue(wearable));
        Assert.assertTrue(items.contains(wearable));
    }

    //TODO: test useWeapon;

}
