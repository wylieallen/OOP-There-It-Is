package entity.entitycontrol;

import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import gameview.GamePanel;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.Collection;
import java.util.Map;

public class HumanEntityController extends EntityController{

    private GamePanel view;

    public HumanEntityController(Entity entity, Equipment equipment, Coordinate entityLocation,
                                 Collection<ControllerAction> actions, GamePanel view) {
        super(entity, equipment, entityLocation, actions);
        this.view = view;

        if(view != null) {
            view.setFocusable(true);
            view.requestFocus();
        }
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
    public void updateMap (Map<Coordinate, Tile> map) {
        update(map);
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
