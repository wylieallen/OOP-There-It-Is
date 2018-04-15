package items;

import entity.entitymodel.Entity;
import commands.Command;
import savingloading.Visitor;

public class InteractiveItem extends Item {

    private Command command;

    public InteractiveItem(String name, Command command) {
        super(name);
        this.command = command;
    }

    public void touch(Entity e) {
        command.trigger(e);
    }

    @Override
    public void accept(Visitor v) {
        v.visitInteractiveItem(this);
    }
}
