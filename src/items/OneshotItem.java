package items;

import entity.entitymodel.Entity;
import commands.Command;
import savingloading.Visitor;

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

    @Override
    public void accept(Visitor v) {
        v.visitOneShotItem(this);
    }
}
