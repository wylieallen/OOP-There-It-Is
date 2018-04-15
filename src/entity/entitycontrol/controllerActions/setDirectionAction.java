package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import maps.tile.Direction;

public class setDirectionAction extends ControllerAction {

    //This will set the entity to face a certain direction

    Entity controlledEntity;
    Direction direction;

    public setDirectionAction(Entity controlledEntity, Direction direction) {
        this.controlledEntity = controlledEntity;
        this.direction = direction;
    }

    @Override
    protected void execute() {
        controlledEntity.setFacing(direction);
    }
}
