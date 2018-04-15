package entitymodel;

import commands.TimedEffect;
import commands.reversiblecommands.MakeParalyzedCommand;
import commands.skillcommands.ParalyzeCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.*;
import entity.entitymodel.interactions.EntityInteraction;
import items.takeableitems.TakeableItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import skills.SkillType;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        HashMap<SkillType, Integer> skillsActor = new HashMap<SkillType, Integer>();
        EntityStats actorStats = new EntityStats(skillsActor, 5, 100, 85, 100, 55, 25, 5, 5, 50, 65, false);
        ArrayList<ControllerAction> actorActions = new ArrayList<>();
        ArrayList <TimedEffect> actorEffects = new ArrayList<>();
        ArrayList <EntityInteraction> actorActorInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> actorActeeInteractions = new ArrayList<>();
        EntityController actorController = new EntityController(null, null, null, null) {
            @Override
            protected void processController() {

            }

            @Override
            public void interact(EntityController interacter) {

            }

            @Override
            public void notifyFreeMove(Entity e) {

            }

            @Override
            public void notifyInventoryManagment(Entity e) {

            }

            @Override
            public void notifyInteraction(Entity player, Entity interactee) {

            }

            @Override
            public void notifyShopping(Entity trader1, Entity trader2) {

            }

            @Override
            public void notifyLevelUp(Entity e) {
                System.out.println("My Entity Leveled Up: " + e.getCurLevel());
            }

            @Override
            public void notifyMainMenu(Entity e) {

            }
        };

        Entity e = new Entity(new Vector(Direction.N, 0), actorStats, actorActions, actorEffects, actorActorInteractions, actorActeeInteractions, inventory,true);

        equipment = new Equipment(wearables, weapons, 5, inventory, e);

    }

    @Test
    public void testWeaponAddAndRemove () {
        WeaponItem weapon = new WeaponItem("Sword", new ParalyzeCommand(SkillType.BANE, 10, 80), 5, 15, SkillType.BANE);

        items.add(weapon);
        equipment.add(weapon);

        Assert.assertTrue(weapons[0] == weapon);
        Assert.assertFalse (items.contains(weapon));

        equipment.remove(weapon);

        Assert.assertTrue (weapons [0] == null);
        Assert.assertTrue(items.contains(weapon));
    }

    @Test
    public void testWearableAddAndRemove () {
        WearableItem wearable = new WearableItem("ACDC shirt", new MakeParalyzedCommand(false), EquipSlot.ARMOUR);

        items.add(wearable);
        equipment.add(wearable);

        Assert.assertTrue(wearables.containsKey(EquipSlot.ARMOUR));
        Assert.assertTrue(wearables.get(EquipSlot.ARMOUR) == wearable);
        Assert.assertFalse (items.contains(wearable));

        equipment.remove(wearable);

        Assert.assertFalse(wearables.containsKey(EquipSlot.ARMOUR));
        Assert.assertTrue(items.contains(wearable));
    }

    //TODO: test useWeapon;

}
