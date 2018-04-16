package commandstests;

import commands.TimedEffect;
import commands.skillcommands.ConfuseCommand;
import commands.skillcommands.MakeFriendlyCommand;
import commands.skillcommands.ModifyHealthCommand;
import commands.skillcommands.SkillCommand;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillCommandsTests {

    private static Entity caster;
    private static Entity target;
    private static List<TimedEffect> targetEffects;
    private static HumanEntityController casterController;
    private static NpcEntityController targetController;

    @BeforeClass
    public static void setupEntities() {
        HashMap<SkillType, Integer> casterSkills = new HashMap<>();
        casterSkills.put(SkillType.ENCHANTMENT, 1);
        casterSkills.put(SkillType.ONEHANDEDWEAPON, 1);
        EntityStats casterStats = new EntityStats(casterSkills, 2, 100,
                100, 100, 100, 0, 0,
                3, 3, 0, false, false);
        caster = new Entity(new Vector(), casterStats, null, new ArrayList<>(), null,
                null, true);

        casterController = new HumanEntityController(caster, null,
                new Coordinate(2, 2), null, null);

        caster.setController(casterController);

        EntityStats targetStats = new EntityStats(new HashMap<>(), 2, 100,
                100, 100, 100, 0, 0,
                3, 3, 0, false, false);
        targetEffects = new ArrayList<>();
        target = new Entity(new Vector(), targetStats, null, targetEffects, null,
                null, true);

        targetController = new NpcEntityController(target, null,
                null, null, new HostileAI(new ArrayList<>(), caster),
                new FriendlyAI(new ArrayList<>()), false);

        target.setController(targetController);
    }

    @Test
    public void testConfuseCommand() {
        ConfuseCommand command = new ConfuseCommand(caster.getSkillLevel(SkillType.ENCHANTMENT), 3, caster);

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
        MakeFriendlyCommand command = new MakeFriendlyCommand(caster.getSkillLevel(SkillType.ENCHANTMENT), 3, caster);

        command.trigger(target);

        Assert.assertFalse(targetController.isAggro());

        target.update();
        Assert.assertFalse(targetController.isAggro());
    }

    @Test
    public void testModifyHealthCommand() {
        ModifyHealthCommand command = new ModifyHealthCommand(SkillType.ONEHANDEDWEAPON,
                caster.getSkillLevel(SkillType.ONEHANDEDWEAPON), -10);

        Assert.assertEquals(100, target.getCurrHealth());

        command.trigger(target);

        Assert.assertEquals(85, target.getCurrHealth());
    }
}
