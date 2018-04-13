package Vehicles;

import EnitityModel.Entity;
import EnitityModel.EntityInteraction;
import EnitityModel.EntityStats;
import EnitityModel.Inventory;
import EntityControl.EntityController;

import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */
public class Vehicle extends Entity {

    public Vehicle(Direction direction,
                   EntityStats stats,
                   List<Action> actions,
                   List<TimedEffects> effects,
                   List<EntityInteraction> actorInteractions,
                   List<EntityInteraction> acteeInteractions,
                   EntityController controller,
                   Inventory inventory)
    {
        super(direction, stats, actions, effects, actorInteractions, acteeInteractions, controller, inventory);
    }
}
