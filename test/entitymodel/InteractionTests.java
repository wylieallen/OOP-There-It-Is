package entitymodel;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitycontrol.controllerActions.DismountAction;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Equipment;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.*;
import entity.vehicle.Vehicle;
import items.takeableitems.QuestItem;
import items.takeableitems.TakeableItem;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dontf on 4/15/2018.
 */
public class InteractionTests {

    private static Entity actor;
    private static Entity actee;
    private static Vehicle vehicle;
    private static List<TakeableItem> actorItems;
    private static List<TakeableItem> acteeItems;

    @Before
    public void setUpEntities() {

        actorItems = new ArrayList<>();
        acteeItems = new ArrayList<>();

        HashMap<SkillType, Integer> skillsActor = new HashMap<>();
        HashMap<SkillType, Integer> skillsActee = new HashMap<>();

        skillsActor.put(SkillType.BANE, 10);
        skillsActor.put(SkillType.PICKPOCKET, 99);
        skillsActor.put(SkillType.CREEP, 50);

        skillsActee.put(SkillType.BINDWOUNDS, 45);
        skillsActee.put(SkillType.CREEP, 32);

        EntityStats actorStats = new EntityStats(skillsActor, 1001, 100, 85, 100, 55, 5, 25, 5, 5, 50, 65, false, false, new HashSet<>());
        EntityStats acteeStats = new EntityStats(skillsActee, 1001, 120, 45, 120, 43, 5, 23, 8, 6, 69, 100, false, false, new HashSet<>());

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

        actorActorInteractions.add(new PickPocketInteraction());

        acteeActeeInteractions.add(new TradeInteraction());
        acteeActeeInteractions.add(new UseItemInteraction());

        Inventory actorInventory = new Inventory(actorItems);
        Inventory acteeInventory = new Inventory(acteeItems);

        Equipment actorEquipment = new Equipment(5, actorInventory, actor);

        EntityController actorController = new HumanEntityController(actor, actorEquipment, new Coordinate(0, 0), null);
        actorController.setControllerActions(actorActions);

        EntityController vehicleController = new HumanEntityController(vehicle, new Equipment(5, acteeInventory, vehicle), new Coordinate(0, 0),  null);
        vehicleController.setControllerActions(acteeActions);

        actorActions.add(new DismountAction(actorController));

        actor = new Entity(new Vector(Direction.N, 0), actorStats, actorEffects, actorActorInteractions, actorInventory, true);
        actee = new Entity(new Vector(Direction.N, 0), acteeStats, acteeEffects, acteeActorInteractions, acteeInventory, true);

    }





    // PickPocket Test //

    @Test
    public void pickPocketEntityWithItemSuccessfully () {
        PickPocketInteraction ppi = new PickPocketInteraction();
        QuestItem qi = new QuestItem("cool quest", true, 123);
        acteeItems.add(qi);
    }


    @Test
    public void pickPocketEntityWithItemUnsuccessfully () {
            PickPocketInteraction ppi = new PickPocketInteraction();
            QuestItem qi = new QuestItem("cool quest", true, 123);
            acteeItems.add(qi);
    }


    // BackStab Test //

    @Test
    public void backStabEntitySuccessfully () {
        BackStabInteraction bsi = new BackStabInteraction();

        int previousHealth = actee.getCurrHealth();
        int previousXP = actor.getCurXP();
        bsi.testInteractFunction(actor, actee, true);
        Assert.assertTrue(actee.getCurrHealth() < previousHealth);
        Assert.assertTrue(actor.getCurXP() > previousXP);
    }

    @Test
    public void backStabEntityUnsuccessfully () {
        BackStabInteraction bsi = new BackStabInteraction();

        int previousHealth = actee.getCurrHealth();
        int previousXP = actor.getCurXP();
        bsi.testInteractFunction(actor, actee, false);
        Assert.assertEquals(previousHealth, actee.getCurrHealth());
        Assert.assertEquals(previousXP, actor.getCurXP());
    }






    // mount interaction test //

    // TODO: make mount test

    @Test
    public void mountInteractionTest () {
        EntityController controller = actor.getController();

        MountInteraction mounting = new MountInteraction();

        Assert.assertFalse(controller.isInVehicle());
        Assert.assertFalse(vehicle.hasDriver());

        mounting.interact(actor, vehicle);

        Assert.assertTrue(controller.isInVehicle());
        Assert.assertTrue(vehicle.hasDriver());
    }





    // trade interaction test //

    // TODO: make trade test, still needs somethings from entity controller and view to be done.





    // talk interaction test //

    // TODO: make talk test, still need view things and what has the messages.





    // useItem interaction test //

    // TODO: make useItem test.

}
