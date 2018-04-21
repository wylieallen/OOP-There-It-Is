package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;

public class MoveAction extends ControllerAction {

    //This will move the entity in the current direction it is facing

    private Entity controlledEntity;


    MoveAction(Entity entity){
        controlledEntity = entity;
    }

    @Override
    protected void execute() {
        controlledEntity.setMoving();
    }

    @Override
    public void accept(ControllerActionVisitor v)
    {
        v.visitMoveAction(this);
    }
}
