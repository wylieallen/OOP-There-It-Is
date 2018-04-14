package item;

import EnitityModel.Entity;
import command.Command;

public class InteractiveItem extends Item {

    private Command command;

    public InteractiveItem(String name, Command command) {
        super(name);
        this.command = command;
    }

    public void touch(Entity e) {
        command.trigger(e);
    }

}
