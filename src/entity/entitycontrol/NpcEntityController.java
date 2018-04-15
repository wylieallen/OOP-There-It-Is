package entity.entitycontrol;

import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitycontrol.AI.AI;
import entity.entitymodel.Equipment;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.Map;

public class NpcEntityController extends EntityController {

    private AI ai;

    public NpcEntityController(Entity entity, Equipment equipment, Coordinate entityLocation,
                               ArrayList<ControllerAction> actions, AI ai) {
        super(entity, equipment, entityLocation, actions);
        this.ai = ai;
    }

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
