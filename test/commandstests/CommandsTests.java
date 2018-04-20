package commandstests;

import commands.*;
import commands.PickPocketCommand;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import items.takeableitems.ConsumableItem;
import items.takeableitems.TakeableItem;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CommandsTests {

    private static Entity caster;
    private static Entity target;
    private static List<TimedEffect> targetEffects;
    private static HumanEntityController casterController;
    private static NpcEntityController targetController;
    private static Inventory casterInventory;
    private static Inventory targetInventory;

    @BeforeClass
    public static void setupEntities() {
        casterInventory = new Inventory();
        targetInventory = new Inventory();

        HashMap<SkillType, Integer> casterSkills = new HashMap<>();
        casterSkills.put(SkillType.ENCHANTMENT, 1);
        casterSkills.put(SkillType.ONEHANDEDWEAPON, 1);
        EntityStats casterStats = new EntityStats(casterSkills, 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false, new HashSet<>());
        caster = new Entity(new Vector(), casterStats, new ArrayList<>(), null,
                casterInventory, true);

        casterController = new HumanEntityController(caster, null,
                new Coordinate(2, 2), null, null);

        caster.setController(casterController);

        EntityStats targetStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 5, 0, 0,
                3, 3, 0, false, false, new HashSet<>());
        targetEffects = new ArrayList<>();
        target = new Entity(new Vector(), targetStats, targetEffects, null,
                targetInventory, true);

        targetController = new NpcEntityController(target, null,
                null, null, new HostileAI(new ArrayList<>(), caster),
                new FriendlyAI(new ArrayList<>()), false);

        target.setController(targetController);
    }

    @Test
    public void testConfuseCommand() {
        ConfuseCommand command = new ConfuseCommand(3);

        command.trigger(target);

        Assert.assertEquals(1, targetEffects.size());
        Assert.assertTrue(target.isConfused());

        target.update();
        Assert.assertEquals(1, targetEffects.size());
        Assert.assertTrue(target.isConfused());

        target.update();
        Assert.assertEquals(1, targetEffects.size());
        Assert.assertTrue(target.isConfused());

        target.update();
        Assert.assertEquals(0, targetEffects.size());
        Assert.assertFalse(target.isConfused());
    }

    @Test
    public void testMakeFriendlyCommand() {
        MakeFriendlyCommand command = new MakeFriendlyCommand();

        command.trigger(target);

        Assert.assertFalse(targetController.isAggro());

        target.update();
        Assert.assertFalse(targetController.isAggro());
    }

    @Test
    public void testModifyHealthCommand() {
        ModifyHealthCommand command = new ModifyHealthCommand(-10);

        Assert.assertEquals(100, target.getCurrHealth());

        command.trigger(target);

        Assert.assertEquals(90, target.getCurrHealth());
    }

    @Test
    public void testModifyStaminaRegenCommand() {
        ModifyStaminaRegenCommand command = new ModifyStaminaRegenCommand(0.5, 0);

        target.setCurMana(50);
        Assert.assertEquals(50, target.getCurMana());

        target.update();
        Assert.assertEquals(55, target.getCurMana());

        target.update();
        Assert.assertEquals(60, target.getCurMana());

        command.trigger(target);

        target.update();
        Assert.assertEquals(62, target.getCurMana());

        target.update();
        Assert.assertEquals(67, target.getCurMana());

        target.update();
        Assert.assertEquals(72, target.getCurMana());

        target.update();
        Assert.assertEquals(77, target.getCurMana());

        command = new ModifyStaminaRegenCommand(2, 0);

        target.setCurMana(10);
        Assert.assertEquals(10, target.getCurMana());

        target.update();
        Assert.assertEquals(15, target.getCurMana());

        target.update();
        Assert.assertEquals(20, target.getCurMana());

        command.trigger(target);

        target.update();
        Assert.assertEquals(30, target.getCurMana());

        target.update();
        Assert.assertEquals(35, target.getCurMana());

        target.update();
        Assert.assertEquals(40, target.getCurMana());
    }

    @Test
    public void testParalyzeCommand() {
        ParalyzeCommand command = new ParalyzeCommand(3);

        Assert.assertEquals(2, target.getBaseMoveSpeed());

        command.trigger(target);

        Assert.assertEquals(0, target.getBaseMoveSpeed());

        target.update();
        Assert.assertEquals(0, target.getBaseMoveSpeed());

        target.update();
        Assert.assertEquals(0, target.getBaseMoveSpeed());

        target.update();
        Assert.assertEquals(2, target.getBaseMoveSpeed());
    }

    @Test
    public void testPickpocketCommand() {
        TakeableItem item = new ConsumableItem("Health Potion", true, new ModifyHealthCommand(10));
        target.addToInventory(item);

        PickPocketCommand command = new PickPocketCommand(caster);

        Assert.assertTrue(targetInventory.contains(item));
        Assert.assertFalse(casterInventory.contains(item));

        command.trigger(target);

        Assert.assertFalse(targetInventory.contains(item));
        Assert.assertTrue(casterInventory.contains(item));
    }

    @Test
    public void testObservationCommand() {
        ObserveCommand command = new ObserveCommand(0);

        command.trigger(target);

        command.trigger(target, 500);

        //TODO: make meaningful unit tests
    }
}
