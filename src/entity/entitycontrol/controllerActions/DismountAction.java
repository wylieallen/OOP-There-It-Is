package entity.entitycontrol.controllerActions;

import entity.entitycontrol.EntityController;

/**
 * Created by dontf on 4/21/2018.
 */
public class DismountAction extends ControllerAction {

    private EntityController mounterController;

    @Override
    protected void execute() {
        System.out.println("Execute Dismount");
        mounterController.notifyDismount();
    }

    public DismountAction (EntityController controller) {
        mounterController = controller;
    }

    @Override
    public void accept(ControllerActionVisitor v) {
        v.visitDismountAction(this);
    }

}
