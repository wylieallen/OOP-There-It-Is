package entity.entitycontrol.controllerActions;

import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;

public class attackAction extends ControllerAction {

    //this will make the controlledEntity attack with the currently equipped hand item

    EntityController controlledEntityController;
    Equipment equipment;
    int weaponSlot;

    public attackAction(EntityController controlledEntityController, Equipment equipment, int weaponSlot) {
        this.controlledEntityController = controlledEntityController;
        this.equipment = equipment;
        this.weaponSlot = weaponSlot;
    }

    @Override
    protected void execute() {
        equipment.useWeaponItem(weaponSlot,controlledEntityController.getEntityLocation());
    }
}
