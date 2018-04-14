package entity.entitymodel;

import entity.entitycontrol.ControllerAction;
import entity.entitycontrol.EntityController;
import entity.entitymodel.EntityInteraction;
import gameobject.GameObject;
import maps.tile.Direction;
import actions.Action;
import commands.TimedEffect;

import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */
public class Entity implements GameObject
{

    private Direction direction;
    private EntityStats stats;
    private List<ControllerAction> actions;//whenever an action gets added to this we need to notify the EntityController to add the same action
    private List<TimedEffect> effects;
    private List <EntityInteraction> actorInteractions;
    private List <EntityInteraction> acteeInteractions;
    private EntityController controller;
    private Inventory inventory;

    public Entity(Direction direction,
                  EntityStats stats,
                  List<ControllerAction> actions,
                  List<TimedEffect> effects,
                  List<EntityInteraction> actorInteractions,
                  List<EntityInteraction> acteeInteractions,
                  EntityController controller,
                  Inventory inventory)
    {
        this.direction = direction;
        this.stats = stats;
        this.actions = actions;
        this.effects = effects;
        this.actorInteractions = actorInteractions;
        this.acteeInteractions = acteeInteractions;
        this.controller = controller;
        this.inventory = inventory;
    }

    public void update () {

    }

    public boolean expired() {
        return stats.getCurHealth() <= 0;
    }

    public int getMaxHealth () {
        return -1;
    }

    public int getCurrHealth () {
        return -1;
    }

    public List <EntityInteraction> interact (Entity actor) {
        return null;
    }

    public boolean isOnMap () {
        return false;
    }

}
