package commands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class KillCommand implements Command {

    public KillCommand() {
    }

    @Override
    public void trigger(Entity e) {
        e.kill();
    }

    @Override
    public void trigger(Entity e, int amount) {
        trigger(e);
    }

    @Override
    public void accept(Visitor v) {
        v.visitKillCommand(this);
    }
}
