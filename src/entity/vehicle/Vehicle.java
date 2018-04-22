package entity.vehicle;

import commands.TimedEffect;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.controllerActions.DismountAction;
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
    private int driverTriedToDitch;

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
        driverTriedToDitch = 0;
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
        driverTriedToDitch = 0;
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
        // todo: LoD problem?
        EntityController controller = driver.getController();
        controller.notifyFreeMove(this);
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
    public void levelUp () {
        if (hasDriver()) {
            driver.levelUp();
        }
        super.levelUp();
    }

    @Override
    public boolean hurtEntity (int amount) {
        if (super.hurtEntity(amount) && hasDriver()) {
            driver.getController().notifyDismount();
            return true;
        }

        return false;
    }

    @Override
    public boolean canMoveHere (Entity mover) {
        MountInteraction mountingTime = new MountInteraction();
        mountingTime.interact(mover, this);
        return false;
    }

    @Override
    public void notifyMovement () {
        if (hasDriver())
            driver.notifyMovement();
        super.notifyMovement();
    }

    @Override
    public boolean expired () {
        if (hasDriver() && driver.expired()) {
            driver = null;
        }

        if ((!hasDriver() || driverTriedToDitch > 25) && super.expired()) {
            return true;
        }

        if (hasDriver() && super.expired()) {
            driver.getController().notifyDismount();
            ++driverTriedToDitch;
        }

        return false;
    }

    @Override
    public void accept(Visitor v) {
        v.visitVehicle(this);
    }
}
