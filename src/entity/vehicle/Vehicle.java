package entity.vehicle;

import commands.TimedEffect;
import entity.entitycontrol.ControllerAction;
import entity.entitycontrol.EntityController;
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
public class Vehicle extends Entity {

    private Entity driver;

    public Vehicle(Vector movementVector,
                   EntityStats stats,
                   List<ControllerAction> actions,
                   List<TimedEffect> effects,
                   List<EntityInteraction> actorInteractions,
                   List<EntityInteraction> acteeInteractions,
                   EntityController controller,
                   Inventory inventory,
                   Entity driver,
                   boolean onMap)
    {
        super(movementVector, stats, actions, effects, actorInteractions, acteeInteractions, inventory, onMap);
        this.driver = driver;
    }

    public void setDriver (Entity driver) {
        this.driver = driver;
    }

    @Override
    public void accept(Visitor v) {
        v.visitVehicle(this);
    }
}
