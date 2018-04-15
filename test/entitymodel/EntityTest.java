package entitymodel;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitycontrol.ControllerAction;
import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.*;
import maps.tile.Direction;
import maps.trajectorymodifier.Vector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import skills.SkillType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */

public class EntityTest {

    private static Entity actor;
    private static Entity actee;

    @BeforeClass
    public static void setUpEntities () {

        HashMap <SkillType, Integer> skillsActor = new HashMap<SkillType, Integer>();
        HashMap <SkillType, Integer> skillsActee = new HashMap<SkillType, Integer>();

        skillsActor.put(SkillType.BANE, 10);
        skillsActor.put(SkillType.BARGAIN, 62);

        skillsActee.put(SkillType.BINDWOUNDS, 45);
        skillsActee.put(SkillType.CREEP, 32);

        EntityStats actorStats = new EntityStats(skillsActor, 5, 100, 85, 100, 55, 25, 5, 5, 50, 65);
        EntityStats acteeStats = new EntityStats(skillsActee, 3, 120,45, 120,43, 23, 8, 6, 69, 100);

        //TODO: once concrete ControllerActions are made test this;
        ArrayList <ControllerAction> actorActions = new ArrayList<>();
        ArrayList <ControllerAction> acteeActions = new ArrayList<>();

        ArrayList <TimedEffect> actorEffects = new ArrayList<>();
        ArrayList <TimedEffect> acteeEffects = new ArrayList<>();

        actorEffects.add(new TimedEffect(new MakeConfusedCommand(false), 10));

        acteeEffects.add(new TimedEffect(new MakeParalyzedCommand(false), 15));

        ArrayList <EntityInteraction> actorActorInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> actorActeeInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> acteeActorInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> acteeActeeInteractions = new ArrayList<>();

        actorActorInteractions.add(new TradeInteraction());
        actorActorInteractions.add(new PickPocketInteraction());

        acteeActeeInteractions.add (new TradeInteraction());
        acteeActeeInteractions.add (new UseItemInteraction());

        //TODO: add constructors when it needs to be tested;
        EntityController actorController = new EntityController() {
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
        EntityController acteeController = new EntityController() {
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

            }

            @Override
            public void notifyMainMenu(Entity e) {

            }
        };

        Inventory actorInventory = new Inventory();
        Inventory acteeInventory = new Inventory();

        actor = new Entity(new Vector(Direction.N, 0), actorStats, actorActions, actorEffects, actorActorInteractions, actorActeeInteractions, actorController, actorInventory, true);
        actee = new Entity(new Vector(Direction.N, 0), acteeStats, acteeActions, acteeEffects, acteeActorInteractions,acteeActeeInteractions, acteeController, acteeInventory, true);

    }

    @Test
    public void useManaTest () {

        Assert.assertFalse(actor.useMana(80));
        Assert.assertEquals(55, actor.getCurMana());

        Assert.assertTrue(actor.useMana(45));
        Assert.assertEquals(10, actor.getCurMana());
    }

    @Test
    public void entityNotifiesControllerOnLevelUpTest (){
        int currentLevel = actor.getCurLevel();
        actor.increaseXP(500);
        Assert.assertTrue (currentLevel < actor.getCurLevel());
    }

    @Test
    public void interactReturnsUnionTest () {
        List<EntityInteraction> union = actee.interact(actor);

        Assert.assertTrue(union.size() == 4);
    }
}
