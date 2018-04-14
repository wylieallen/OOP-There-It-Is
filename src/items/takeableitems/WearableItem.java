package items.takeableitems;

import entity.entitymodel.Entity;
import entity.entitymodel.EquipSlot;
import entity.entitymodel.Equipment;
import commands.reversiblecommands.ReversibleCommand;

public class WearableItem extends TakeableItem {

    private ReversibleCommand command;
    private EquipSlot equipType;

    public WearableItem(String name, ReversibleCommand command, EquipSlot equipType) {
        super(name);
        this.command = command;
        this.equipType = equipType;
    }

    public EquipSlot getEquipType () { return equipType; }

    @Override
    public void activate(Equipment e) {

    }

    public void applyEffect(Entity e) {

    }
}
