package entity.vehicle;

import commands.TimedEffect;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import entity.entitymodel.interactions.MountInteraction;
import maps.tile.Tile;
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
            System.out.println(this.toString());
            actor.setMount (this);
            // after mounting you interact with mount, maybe use item?
            return super.interact(actor);
        }

        return driver.interact(actor);
    }

    @Override
    public void update(Map<Coordinate, Tile> map) {

        if (driver != null) {
            if (driver.isOnMap()) {
                driver = null;
            }
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

        if (hasDriver()) {
            Vector v = new Vector(driver.getMovementDirection(), driver.getBaseMoveSpeed() + getBaseMoveSpeed());
            return v;
        } else {
            return super.getMovementVector();
        }

    }

    @Override
    public boolean canMoveHere (Entity mover) {
        MountInteraction mountingTime = new MountInteraction();
        return mountingTime.interact(mover, this);
    }

    @Override
    public void accept(Visitor v) {
        v.visitVehicle(this);
    }
}
