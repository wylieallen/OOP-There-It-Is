package item.takeableitem;

import command.Command;

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
}