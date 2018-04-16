package entitymodel;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.*;
import items.takeableitems.QuestItem;
import items.takeableitems.TakeableItem;
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
public class InteractionTests {

    private static Entity actor;
    private static Entity actee;
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

        EntityStats actorStats = new EntityStats(skillsActor, 5, 100, 85, 100, 55, 5, 25, 5, 5, 50, 65, false, false);
        EntityStats acteeStats = new EntityStats(skillsActee, 3, 120, 45, 120, 43, 5, 23, 8, 6, 69, 100, false, false);

        //TODO: once concrete ControllerActions are made test this;
        ArrayList<ControllerAction> actorActions = new ArrayList<>();
        ArrayList<ControllerAction> acteeActions = new ArrayList<>();

        ArrayList<TimedEffect> actorEffects = new ArrayList<>();
        ArrayList<TimedEffect> acteeEffects = new ArrayList<>();

        actorEffects.add(new TimedEffect(new MakeConfusedCommand(false), 10));

        acteeEffects.add(new TimedEffect(new MakeParalyzedCommand(false), 15));

        ArrayList<EntityInteraction> actorActorInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> actorActeeInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> acteeActorInteractions = new ArrayList<>();
        ArrayList<EntityInteraction> acteeActeeInteractions = new ArrayList<>();

        actorActorInteractions.add(new PickPocketInteraction());

        acteeActeeInteractions.add(new TradeInteraction());
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
        };
        EntityController acteeController = new EntityController(actee, null, null, null) {
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
        };*/

        Inventory actorInventory = new Inventory(actorItems);
        Inventory acteeInventory = new Inventory(acteeItems);

        actor = new Entity(new Vector(Direction.N, 0), actorStats, actorActions, actorEffects, actorActorInteractions, actorInventory, true);
        actee = new Entity(new Vector(Direction.N, 0), acteeStats, acteeActions, acteeEffects, acteeActorInteractions, acteeInventory, true);

    }





    // PickPocket Test //

    @Test
    public void pickPocketEntityWithItemSuccessfully () {
        PickPocketInteraction ppi = new PickPocketInteraction();
        QuestItem qi = new QuestItem("cool quest", 123);
        acteeItems.add(qi);

        ppi.testInteractFunction(actor, actee, true);
        Assert.assertTrue(actorItems.contains(qi));
        Assert.assertFalse(acteeItems.contains(qi));
    }

    @Test
    public void pickPocketEntityWithItemUnsuccessfully () {
        PickPocketInteraction ppi = new PickPocketInteraction();
        QuestItem qi = new QuestItem("cool quest", 123);
        acteeItems.add(qi);

        ppi.testInteractFunction(actor, actee, false);
        Assert.assertFalse(actorItems.contains(qi));
        Assert.assertTrue(acteeItems.contains(qi));
    }

    @Test
    public void pickPocketEntityWithoutItem () {
        PickPocketInteraction ppi = new PickPocketInteraction();
        ppi.testInteractFunction(actor, actee, true);

        Assert.assertTrue(acteeItems.isEmpty());
        Assert.assertTrue(actorItems.isEmpty());
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

    // TODO: make mount test, still needs somethings from entity controller and view to be done.






    // trade interaction test //

    // TODO: make trade test, still needs somethings from entity controller and view to be done.





    // talk interaction test //

    // TODO: make talk test, still need view things and what has the messages.

}
