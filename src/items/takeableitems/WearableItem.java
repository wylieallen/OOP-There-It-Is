package items.takeableitems;

import entitymodel.Entity;
import entitymodel.Equipment;
import commands.reversiblecommands.ReversibleCommand;

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
