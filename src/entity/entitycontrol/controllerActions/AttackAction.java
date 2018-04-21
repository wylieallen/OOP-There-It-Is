package entity.entitycontrol.controllerActions;

import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;

public class AttackAction extends ControllerAction {

    //this will make the controlledEntity attack with the currently equipped hand item

    private EntityController controlledEntityController;
    private Equipment equipment;
    private int weaponSlot;

    public AttackAction(EntityController controlledEntityController, Equipment equipment, int weaponSlot) {
        this.controlledEntityController = controlledEntityController;
        this.equipment = equipment;
        this.weaponSlot = weaponSlot;
    }

    @Override
    protected void execute() {
        equipment.useWeaponItem(weaponSlot,controlledEntityController.getEntityLocation());
    }

    @Override
    public void accept(ControllerActionVisitor v) { v.visitAttackAction(this); }
}
