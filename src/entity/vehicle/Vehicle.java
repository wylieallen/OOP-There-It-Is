package entity.vehicle;

import commands.TimedEffect;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitycontrol.controllerActions.DismountAction;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import gameobject.GameObjectContainer;
import savingloading.Visitor;
import utilities.Coordinate;
import utilities.Vector;

import java.util.List;
import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */

// TODO: Override all entity functions that the vehicle must push to the driver;
public class Vehicle extends Entity {

    private Entity driver;

    public Vehicle(Vector movementVector,
                   EntityStats stats,
                   List<TimedEffect> effects,
                   List<EntityInteraction> actorInteractions,
                   Inventory inventory,
                   boolean isOnMap,
                   Entity driver)
    {
        super(movementVector, stats, effects, actorInteractions, inventory, isOnMap, "Default");
        this.driver = driver;
    }

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
        super(vector, stats, effects, actorInteractions, inventory, isOnMap, "Default");
        this.driver = null;
    }

    @Override
    public List <EntityInteraction> interact (Entity actor) {

        if (!hasDriver()) {
            setDriver(actor);
            actor.setMount (this);
            actor.addControllerAction(new DismountAction(actor, actor.getController()));
            // after mounting you interact with mount, maybe use item?
            return super.interact(actor);
        }

        return driver.interact(actor);
    }

    @Override
    public void update(Map<Coordinate, GameObjectContainer> mapOfContainers) {
        
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
