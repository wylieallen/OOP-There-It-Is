package item.takeableitem;

import EnitityModel.Entity;
import EnitityModel.Equipment;
import command.ReversibleCommand.ReversibleCommand;

public class WearableItem extends TakeableItem {

    private ReversibleCommand command;

    public WearableItem(String name, ReversibleCommand command) {
        super(name);
        this.command = command;
    }

    @Override
    public void activate(Equipment e) {

    }

    public void applyEffect(Entity e) {

    }
}
