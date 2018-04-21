package entity.entitycontrol;

import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitycontrol.controllerActions.DirectionalMoveAction;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import gameview.GamePanel;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class HumanEntityController extends EntityController{

    private GamePanel view;

    public HumanEntityController(Entity entity, Equipment equipment, Coordinate entityLocation, GamePanel view) {
        super(entity, equipment, entityLocation, new ArrayList<>());
        this.view = view;

        if(view != null) {
            view.setFocusable(true);
            view.requestFocus();
        }

        for(Direction d : Direction.values())
        {
            if(d != Direction.NULL)
                addAction(new DirectionalMoveAction(entity, d));
        }
    }

    public void setControllerActions(Collection<ControllerAction> actions){
        super.setControllerActions(actions);

        if(view != null) {
            view.clearKeyListeners();
            for(ControllerAction action : actions)
            {
                action.accept(view);
            }
        }

    }

    @Override
    public void addAction(ControllerAction action) {
        super.addAction(action);
        if(view != null) {
            action.accept(view);
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
