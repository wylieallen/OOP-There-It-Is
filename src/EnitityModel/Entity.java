package EnitityModel;

import EntityControl.EntityController;

import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */
public class Entity implements GameObject {

    private Direction direction;
    private EntityStats stats;
    private List <Action> actions;
    private List <TimedEffects> effects;
    private List <EntityInteraction> actorInteractions;
    private List <EntityInteraction> acteeInteractions;
    private EntityController controller;
    private Inventory inventory;

    public Entity(Direction direction,
                  EntityStats stats,
                  List<Action> actions,
                  List<TimedEffects> effects,
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

    public int getMaxHealth () {

    }

    public int getCurrHealth () {

    }

    public List <EntityInteraction> interact (Entity actor) {

    }

    public boolean isOnMap () {

    }

}
