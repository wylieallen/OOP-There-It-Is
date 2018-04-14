package entitymodel;

import entitycontrol.EntityController;
import gameobject.GameObject;
import tile.Direction;
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
    private List<Action> actions;
    private List<TimedEffect> effects;
    private List <EntityInteraction> actorInteractions;
    private List <EntityInteraction> acteeInteractions;
    private EntityController controller;
    private Inventory inventory;

    public Entity(Direction direction,
                  EntityStats stats,
                  List<Action> actions,
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
