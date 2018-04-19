package entity.vehicle;

import commands.TimedEffect;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import entity.entitycontrol.EntityController;
import maps.tile.Direction;
import actions.Action;
import commands.TimedEffect;
import savingloading.Visitor;
import utilities.Vector;

import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */

// TODO: Override all entity functions that the vehicle must push to the driver;
public class Vehicle extends Entity {

    private Entity driver;

    public Vehicle(Vector movementVector,
                   EntityStats stats,
                   List<ControllerAction> actions,
                   List<TimedEffect> effects,
                   List<EntityInteraction> actorInteractions,
                   Inventory inventory,
                   boolean isOnMap,
                   Entity driver)
    {
        super(movementVector, stats, actions, effects, actorInteractions, inventory, isOnMap);
        this.driver = driver;
    }

    public Vehicle(Vector vector,
                   EntityStats stats,
                   List<ControllerAction> actions,
                   List<TimedEffect> effects,
                   List<EntityInteraction> actorInteractions,
                   Inventory inventory,
                   boolean isOnMap)
    {
        super(vector, stats, effects, actorInteractions, inventory, isOnMap);
        this.driver = null;
    }

    @Override
    public List <EntityInteraction> interact (Entity actor) {

        if (!hasDriver()) {
            setDriver(actor);
            actor.setMount (this);
            // after mounting you interact with mount, maybe use item?
            return super.interact(actor);
        }

        return driver.interact(actor);
    }

    private void setDriver (Entity driver) {
        this.driver = driver;
    }

    public boolean hasDriver () { return driver != null; }

    @Override
    public void accept(Visitor v) {
        v.visitVehicle(this);
    }
}
