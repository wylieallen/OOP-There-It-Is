package Entity.EntityControl;


import java.util.ArrayList;

public abstract class EntityController {

    private Entity controlledEntity;
    private Equipment equipment;
    private Coordinate entityLocation;
    private ArrayList<ControllerAction> actions;


    public abstract void update(Map<Coordinate, GameObjectContainer> mapOfContainers);
    public abstract void interact(EntityController interacter);
    public abstract void notifyFreeMove(Entity e);
    public abstract void notifyInventoryManagment(Entity e);
    public abstract void notifyInteraction(Enity player, Entity interactee);
    public abstract void notifyShopping(Entity trader1, Entity trader2);
    public abstract void notifyLevelUp(Entity e);
    public abstract void notifyMainMenu(Entity e);

}
