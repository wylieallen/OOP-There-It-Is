package entity.entitycontrol;

import entity.entitymodel.Entity;
import entity.entitycontrol.AI.AI;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.Map;

public class NpcEntityController extends EntityController {

    private AI ai;

    @Override
    protected void processController() {
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
