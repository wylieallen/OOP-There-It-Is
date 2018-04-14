package entity.entitycontrol;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import utilities.Coordinate;
import gameview.GamePanel;

import java.util.Map;

public class HumanEntityController extends EntityController{

    private GamePanel view;

    @Override
    public void update(Map<Coordinate, GameObjectContainer> mapOfContainers) {
        //TODO
    }

    @Override
    public void interact(EntityController interacter) {
        //TODO
    }

    @Override
    public void notifyFreeMove(Entity e) {
        //TODO
    }

    @Override
    public void notifyInventoryManagment(Entity e) {
        //TODO
    }

    @Override
    public void notifyInteraction(Entity player, Entity interactee) {
        //TODO
    }

    @Override
    public void notifyShopping(Entity trader1, Entity trader2) {
        //TODO
    }

    @Override
    public void notifyLevelUp(Entity e) {
        //TODO
    }

    @Override
    public void notifyMainMenu(Entity e) {
        //TODO
    }
}
