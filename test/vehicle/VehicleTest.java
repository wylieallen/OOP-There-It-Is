package vehicle;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import entity.entitymodel.interactions.PickPocketInteraction;
import entity.entitymodel.interactions.TradeInteraction;
import entity.entitymodel.interactions.UseItemInteraction;
import entity.vehicle.Vehicle;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import skills.SkillType;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dontf on 4/15/2018.
 */
public class VehicleTest {

    private static Vehicle yourMomPreMounted;
    private static Vehicle yourMomNotMountedYet;
    private static Entity actor;

    private static TradeInteraction trading = new TradeInteraction();
    private static PickPocketInteraction picking = new PickPocketInteraction();

    @Before
    public void setUpEntities () {

        HashMap<SkillType, Integer> skillsActor = new HashMap<>();
        HashMap<SkillType, Integer> skillsActee = new HashMap<>();

        skillsActor.put(SkillType.BANE, 10);
        skillsActor.put(SkillType.PICKPOCKET, 99);
        skillsActor.put(SkillType.CREEP, 50);

        skillsActee.put(SkillType.BINDWOUNDS, 45);
        skillsActee.put(SkillType.CREEP, 32);

        EntityStats actorStats = new EntityStats(skillsActor, 5, 100, 85, 100, 55, 5, 25, 5, 5, 50, 65, false, false, new HashSet<>());
        EntityStats acteeStats = new EntityStats(skillsActee, 3, 120, 45, 120, 43, 5, 23, 8, 6, 69, 100, false, false, new HashSet<>());

        //TODO: once concrete ControllerActions are made test this;
        ArrayList<ControllerAction> actorActions = new ArrayList<>();
        ArrayList<ControllerAction> acteeActions = new ArrayList<>();

        ArrayList<TimedEffect> actorEffects = new ArrayList<>();
        ArrayList<TimedEffect> acteeEffects = new ArrayList<>();

        actorEffects.add(new TimedEffect(new MakeConfusedCommand(false), 10, 0));

        acteeEffects.add(new TimedEffect(new MakeParalyzedCommand(false), 15, 0));

        ArrayList<EntityInteraction> actorActorInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> actorActeeInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> acteeActorInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> acteeActeeInteractions = new ArrayList<>();

        actorActorInteractions.add(picking);

        acteeActeeInteractions.add(trading);
        acteeActeeInteractions.add(new UseItemInteraction());

        //TODO: add constructors when it needs to be tested;
        /*EntityController actorController = new EntityController(actor, null, null, null) {
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

            @Override
            public void pacify() {}

            @Override
            public void enrage(Entity e) {}
        };
        EntityController acteeController = new EntityController(yourMomNotMountedYet, null, null, null) {
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

            @Override
            public void pacify() {}

            @Override
            public void enrage(Entity e) {}
        };*/

        actor = new Entity(new Vector(Direction.N, 1), actorStats, actorActions, actorEffects, actorActorInteractions, new Inventory(), true);
        yourMomPreMounted = new Vehicle(new Vector(Direction.N, 1), acteeStats, acteeActions, acteeEffects, acteeActorInteractions, new Inventory(), true, actor);
        yourMomNotMountedYet = new Vehicle(new Vector(Direction.N, 1), acteeStats, acteeActions, acteeEffects, acteeActorInteractions, new Inventory(), true);


        EntityController actorController = new HumanEntityController(actor,null,
                null, new ArrayList<>(), null);

        EntityController yourMomPreMountedController = new NpcEntityController(yourMomPreMounted, null,
                null, new ArrayList<>(), new HostileAI(new ArrayList<>(), actor, null),
                new FriendlyAI(acteeActeeInteractions, null, false), false);

        EntityController yourMomNotMountedYetController = new NpcEntityController(yourMomNotMountedYet, null,
                null, new ArrayList<>(), new HostileAI(new ArrayList<>(), actor, null),
                new FriendlyAI(acteeActeeInteractions, null, false), false);

        actor.setController(actorController);
        yourMomPreMounted.setController(yourMomPreMountedController);
        yourMomNotMountedYet.setController(yourMomNotMountedYetController);
    }

    @Test
    public void vehicleMountedSuccessfully () {
        Assert.assertFalse(yourMomNotMountedYet.hasDriver());
        List<EntityInteraction> union = yourMomNotMountedYet.interact(actor);
        Assert.assertFalse(actor.isOnMap());
        Assert.assertTrue(yourMomNotMountedYet.hasDriver());
        Assert.assertTrue(union.contains(trading));
    }

    @Test
    public void vehicleMountedUnsuccessfully () {
        List<EntityInteraction> union = yourMomPreMounted.interact(actor);
        Assert.assertTrue(actor.isOnMap());
        Assert.assertTrue(yourMomPreMounted.hasDriver());
        Assert.assertTrue(union.contains(picking));
    }

}
