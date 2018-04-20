package items;

import entity.entitymodel.Entity;
import commands.Command;
import savingloading.Visitor;

public class OneshotItem extends Item {

    private Command command;
    private boolean hasFired;

    public OneshotItem(String name, Command command, boolean expired) {
        super(name);
        this.command = command;
        this.hasFired = expired;
    }

    public void touch(Entity e) {
        command.trigger(e);
        hasFired = true;

    }

    @Override
    public boolean expired() {
        return hasFired;
    }

    @Override
    public boolean shouldBeRemoved() { return hasFired; }

    public Command getCommand(){
        return command;
    }

    public boolean isActive() {
        return !hasFired;
    }

    @Override
    public void accept(Visitor v) {
        v.visitOneShotItem(this);
    }
}
