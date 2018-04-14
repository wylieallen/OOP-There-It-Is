package EntityControl;

import EnitityModel.Entity;
import EntityControl.AI.AI;
import GameObject.GameObjectContainer;
import Utilities.Coordinate;

import java.util.Map;

public class NpcEntityController extends EntityController {

    private AI ai;

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

    public void processAI(Map<Coordinate, GameObjectContainer> map, Entity e){
        //TODO
    }

    public void setAI(AI newAI){
        this.ai = newAI;
    }

}
