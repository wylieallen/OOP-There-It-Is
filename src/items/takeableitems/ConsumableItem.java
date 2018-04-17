package items.takeableitems;

import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import commands.Command;
import savingloading.Visitor;

public class ConsumableItem extends TakeableItem {

    private Command command;

    public ConsumableItem(String name, Command command) {
        super(name);
        this.command = command;
    }

    @Override
    public void activate(Equipment e) {

    }

    public void applyEffect(Entity e) {

    }

    public Command getCommand() {
        return command;
    }

    @Override
    public void accept(Visitor v) {
        v.visitConsumableItem(this);
    }
}
