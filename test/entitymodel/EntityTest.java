package entitymodel;

import commands.TimedEffect;
import entity.entitycontrol.ControllerAction;
import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.trajectorymodifier.Vector;
import org.junit.Before;
import skills.SkillType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dontf on 4/14/2018.
 */

public class EntityTest {

    private static Entity actor;
    private static Entity actee;

    @Before
    public void setUpEntities () {

        HashMap <SkillType, Integer> skillsActor = new HashMap<SkillType, Integer>();
        HashMap <SkillType, Integer> skillsActee = new HashMap<SkillType, Integer>();

        skillsActor.put(SkillType.BANE, 10);
        skillsActor.put(SkillType.BARGAIN, 62);

        skillsActee.put(SkillType.BINDWOUNDS, 45);
        skillsActee.put(SkillType.CREEP, 32);

        EntityStats actorStats = new EntityStats(skillsActor, 5, 100, 85, 100, 55, 25, 5, 5, 50, 65);
        EntityStats acteeStats = new EntityStats(skillsActee, 3, 120,45, 120,43, 23, 8, 6, 69, 100);

        ArrayList <ControllerAction> actorActions = new ArrayList<>();
        ArrayList <ControllerAction> acteeActions = new ArrayList<>();

        ArrayList <TimedEffect> actorEffects = new ArrayList<>();
        ArrayList <TimedEffect> acteeEffects = new ArrayList<>();

        ArrayList <EntityInteraction> actorActorInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> actorActeeInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> acteeActorInteractions = new ArrayList<>();
        ArrayList <EntityInteraction> acteeActeeInteractions = new ArrayList<>();

        //TODO: add constructors when it needs to be tested;
        EntityController actorController = null;
        EntityController acteeController = null;

        Inventory actorInventory = new Inventory();
        Inventory acteeInventory = new Inventory();

        actor = new Entity(new Vector(Direction.N, 0), actorStats, actorActions, actorEffects, actorActorInteractions, actorActeeInteractions, actorController, actorInventory, true);
        actee = new Entity(new Vector(Direction.N, 0), acteeStats, acteeActions, acteeEffects, acteeActorInteractions,acteeActeeInteractions, acteeController, acteeInventory, true);

    }

}
