package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import maps.tile.Direction;

public class SetDirectionAction extends ControllerAction {

    //This will set the entity to face a certain direction

    private Entity controlledEntity;
    private Direction direction;

    public SetDirectionAction(Entity controlledEntity, Direction direction) {
        this.controlledEntity = controlledEntity;
        this.direction = direction;
    }

    @Override
    protected void execute() {
        controlledEntity.setFacing(direction);
    }

    @Override
    public void accept(ControllerActionVisitor v)
    {
        v.visitSetDirectionAction(this);
    }
}
