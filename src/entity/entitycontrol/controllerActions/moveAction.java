package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;

public class moveAction extends ControllerAction {

    //This will move the entity in the current direction it is facing

    private Entity controlledEntity;


    moveAction(Entity entity){
        controlledEntity = entity;
    }

    @Override
    protected void execute() {
        controlledEntity.setMoving();
    }
}
