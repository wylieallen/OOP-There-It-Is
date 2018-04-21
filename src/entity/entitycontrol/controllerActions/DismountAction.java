package entity.entitycontrol.controllerActions;

import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;

/**
 * Created by dontf on 4/21/2018.
 */
public class DismountAction extends ControllerAction {

    private Entity mounter;
    private EntityController mounterController;

    @Override
    protected void execute() {
        mounterController.notifyDismount();
    }

    public DismountAction (Entity entity, EntityController controller) {
        mounter = entity;
        mounterController = controller;
    }

    @Override
    public void accept(ControllerActionVisitor v) {
        v.visitDismountAction(this);
    }

}
