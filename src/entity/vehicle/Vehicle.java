package entity.vehicle;

import commands.TimedEffect;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import entity.entitymodel.interactions.MountInteraction;
import items.takeableitems.TakeableItem;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
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

    public Vehicle(Vector vector,
                   EntityStats stats,
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
            // after mounting you interact with mount, maybe use item?
            return new ArrayList<>();
        }

        actor.getController().notifyInteraction(actor, driver);
        return new ArrayList<>();
    }

    @Override
    public boolean addToInventory (TakeableItem item) {
        if (hasDriver()) {
            return driver.addToInventory(item);
        } else {
            return super.addToInventory(item);
        }
    }

    @Override
    public void update(Map<Coordinate, Tile> map) {

        if (driver != null) {
            if (driver.isOnMap()) {
                driver = null;
            }
            driver.update(map);
        }

        super.update(map);
    }

    private void setDriver (Entity driver) {
        this.driver = driver;
    }

    public void removeDriver () {
        driver = null;
    }

    public boolean hasDriver () { return driver != null; }

    @Override
    public Vector getMovementVector () {
        Vector v;
        if (hasDriver()) {
            v = driver.getMovementVector();

            if (v.isZeroVector()) {
                return v;
            } else {
                super.setFacing(driver.getFacing());
                super.setMoving();
                v = super.getMovementVector();
                super.resetMovementVector();
                driver.resetMovementVector();
                return v;
            }
        } else {
            v = super.getMovementVector();
            super.resetMovementVector();
            return v;
        }

    }

    @Override
    public boolean canMoveHere (Entity mover) {
        MountInteraction mountingTime = new MountInteraction();
        mountingTime.interact(mover, this);
        return false;
    }

    @Override
    public void accept(Visitor v) {
        v.visitVehicle(this);
    }
}
