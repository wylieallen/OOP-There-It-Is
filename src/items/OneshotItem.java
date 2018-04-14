package items;

import entitymodel.Entity;
import commands.Command;

public class OneshotItem extends Item {

    private Command command;
    private boolean active;

    public OneshotItem(String name, Command command, boolean active) {
        super(name);
        this.command = command;
        this.active = active;
    }

    public void touch(Entity e) {

    }
}
