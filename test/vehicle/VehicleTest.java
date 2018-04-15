package vehicle;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitycontrol.EntityController;
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

        HashMap<SkillType, Integer> skillsActor = new HashMap<SkillType, Integer>();
        HashMap<SkillType, Integer> skillsActee = new HashMap<SkillType, Integer>();

        skillsActor.put(SkillType.BANE, 10);
        skillsActor.put(SkillType.PICKPOCKET, 99);
        skillsActor.put(SkillType.CREEP, 50);

        skillsActee.put(SkillType.BINDWOUNDS, 45);
        skillsActee.put(SkillType.CREEP, 32);

        EntityStats actorStats = new EntityStats(skillsActor, 5, 100, 85, 100, 55, 25, 5, 5, 50, 65, false);
        EntityStats acteeStats = new EntityStats(skillsActee, 3, 120, 45, 120, 43, 23, 8, 6, 69, 100, false);

        //TODO: once concrete ControllerActions are made test this;
        ArrayList<ControllerAction> actorActions = new ArrayList<>();
        ArrayList<ControllerAction> acteeActions = new ArrayList<>();

        ArrayList<TimedEffect> actorEffects = new ArrayList<>();
        ArrayList<TimedEffect> acteeEffects = new ArrayList<>();

        actorEffects.add(new TimedEffect(new MakeConfusedCommand(false, 5), 10));

        acteeEffects.add(new TimedEffect(new MakeParalyzedCommand(false), 15));

        ArrayList<EntityInteraction> actorActorInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> actorActeeInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> acteeActorInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> acteeActeeInteractions = new ArrayList<>();

        actorActorInteractions.add(picking);

        acteeActeeInteractions.add(trading);
        acteeActeeInteractions.add(new UseItemInteraction());

        //TODO: add constructors when it needs to be tested;
        EntityController actorController = new EntityController(actor, null, null, null) {
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
        };

        actor = new Entity(new Vector(Direction.N, 1), actorStats, actorActions, actorEffects, actorActorInteractions, actorActeeInteractions, new Inventory(), true);
        yourMomPreMounted = new Vehicle(new Vector(Direction.N, 1), acteeStats, acteeActions, acteeEffects, acteeActorInteractions, acteeActeeInteractions, new Inventory(), true, actor);
        yourMomNotMountedYet = new Vehicle(new Vector(Direction.N, 1), acteeStats, acteeActions, acteeEffects, acteeActorInteractions, acteeActeeInteractions, new Inventory(), true);

        actor.setController(actorController);
        yourMomPreMounted.setController(acteeController);
        yourMomNotMountedYet.setController(acteeController);
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
