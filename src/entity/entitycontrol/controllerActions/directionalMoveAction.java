package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import maps.tile.Direction;

public class directionalMoveAction extends ControllerAction {

    //This will set the entity to face a certain direction and then set them to moving in that direction

    private Entity controlledEntity;
    private Direction direction;

    directionalMoveAction(Entity entity, Direction direction){
        this.controlledEntity = entity;
        this.direction = direction;
    }

    @Override
    protected void execute() {
        controlledEntity.setFacing(direction);
        controlledEntity.setMoving();
    }
}
