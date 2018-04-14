package entity.vehicle;

import commands.TimedEffect;
import entity.entitycontrol.ControllerAction;
import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import maps.trajectorymodifier.Vector;

import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */
public class Vehicle extends Entity {

    private Entity driver;

    public Vehicle(Vector vector,
                   EntityStats stats,
                   List<ControllerAction> actions,
                   List<TimedEffect> effects,
                   List<EntityInteraction> actorInteractions,
                   List<EntityInteraction> acteeInteractions,
                   EntityController controller,
                   Inventory inventory,
                   Entity driver)
    {
        super(vector, stats, actions, effects, actorInteractions, acteeInteractions, controller, inventory, true);
        this.driver = driver;
    }

    public void setDriver (Entity driver) {
        this.driver = driver;
    }
}
