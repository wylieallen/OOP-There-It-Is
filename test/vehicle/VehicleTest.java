package vehicle;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
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

        EntityStats actorStats = new EntityStats(skillsActor, 1001, 100, 85, 100, 55, 5, 25, 5, 5, 50, 65, false, false, new HashSet<>());
        EntityStats acteeStats = new EntityStats(skillsActee, 1001, 120, 45, 120, 43, 5, 23, 8, 6, 69, 100, false, false, new HashSet<>());



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

        actor = new Entity(new Vector(Direction.N, 1), actorStats, actorEffects, actorActorInteractions, new Inventory(), true);
        yourMomPreMounted = new Vehicle(new Vector(Direction.N, 1), acteeStats, acteeEffects, acteeActorInteractions, new Inventory(), true, actor);
        yourMomNotMountedYet = new Vehicle(new Vector(Direction.N, 1), acteeStats, acteeEffects, acteeActorInteractions, new Inventory(), true);


        EntityController actorController = new HumanEntityController(actor,null,
                null, null);

        EntityController yourMomPreMountedController = new NpcEntityController(yourMomPreMounted, null,
                null, new HostileAI(new ArrayList<>(), actor, null),
                new FriendlyAI(acteeActeeInteractions, null, false), false);

        EntityController yourMomNotMountedYetController = new NpcEntityController(yourMomNotMountedYet, null,
                null, new HostileAI(new ArrayList<>(), actor, null),
                new FriendlyAI(acteeActeeInteractions, null, false), false);

        actor.setController(actorController);
        yourMomPreMounted.setController(yourMomPreMountedController);
        yourMomNotMountedYet.setController(yourMomNotMountedYetController);
    }

    @Test
    public void vehicleMountedSuccessfully () {
        Assert.assertFalse(yourMomNotMountedYet.hasDriver());
        yourMomNotMountedYet.interact(actor);
        Assert.assertFalse(actor.isOnMap());
        Assert.assertTrue(yourMomNotMountedYet.hasDriver());
    }

    @Test
    public void vehicleMountedUnsuccessfully () {
        yourMomPreMounted.interact(actor);
        Assert.assertTrue(actor.isOnMap());
        Assert.assertTrue(yourMomPreMounted.hasDriver());
    }

}
