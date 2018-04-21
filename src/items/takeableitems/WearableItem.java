package items.takeableitems;

import entity.entitymodel.Entity;
import entity.entitymodel.EquipSlot;
import entity.entitymodel.Equipment;
import commands.reversiblecommands.ReversibleCommand;
import savingloading.Visitor;

public class WearableItem extends TakeableItem {

    private ReversibleCommand command;
    private EquipSlot equipType;

    public WearableItem(String name, boolean onMap, ReversibleCommand command, EquipSlot equipType) {
        super(name, onMap);
        this.command = command;
        this.equipType = equipType;
    }

    public EquipSlot getEquipType () { return equipType; }

    @Override
    public void activate(Equipment e) {
        e.add(this);
    }

    public void applyEffect(Entity e) {
        command.trigger(e);
    }

    public ReversibleCommand getCommand() {
        return command;
    }

    public void setEquipType(EquipSlot equipType) {
        this.equipType = equipType;
    }

    @Override
    public void accept(Visitor v) {
        v.visitWearableItem(this);
    }

    public final static WearableItem NONE = new WearableItem("NONE", false, new ReversibleCommand(false) {
        @Override
        protected void apply(Entity e) {

        }

        @Override
        protected void unapply(Entity e) {

        }

        @Override
        public void accept(Visitor v) {

        }
    }, EquipSlot.NONE);
}
