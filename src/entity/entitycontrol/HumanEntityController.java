package entity.entitycontrol;

import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import gameobject.GameObjectContainer;
import savingloading.Visitor;
import utilities.Coordinate;
import gameview.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class HumanEntityController extends EntityController{

    private GamePanel view;

    public HumanEntityController(Entity entity, Equipment equipment, Coordinate entityLocation,
                                 List<ControllerAction> actions, GamePanel view) {
        super(entity, equipment, entityLocation, actions);
        this.view = view;
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

    @Override
    public void enrage(Entity e) {}

    @Override
    public void pacify() {}

    @Override
    public void accept(Visitor v) {
        v.visitHumanEntityController(this);
    }
}
