package item;

import command.Command;

public class OneshotItem extends Item {

    Command command;
    boolean active;

    public OneshotItem(String name, Command command, boolean active) {
        super(name);
        this.command = command;
        this.active = active;
    }

    public void touch(Entity e) {

    }
}