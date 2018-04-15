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

// TODO: Override all entity functions that the vehicle must push to the driver;
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
                   boolean isOnMap,
                   Entity driver)
    {
        super(vector, stats, actions, effects, actorInteractions, acteeInteractions, controller, inventory, isOnMap);
        this.driver = driver;
    }

    public Vehicle(Vector vector,
                   EntityStats stats,
                   List<ControllerAction> actions,
                   List<TimedEffect> effects,
                   List<EntityInteraction> actorInteractions,
                   List<EntityInteraction> acteeInteractions,
                   EntityController controller,
                   Inventory inventory,
                   boolean isOnMap)
    {
        super(vector, stats, actions, effects, actorInteractions, acteeInteractions, controller, inventory, isOnMap);
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
}
