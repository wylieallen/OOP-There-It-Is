package commands;

import commands.reversiblecommands.MakeConfusedCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;

public class EnrageCommand implements Command {

    private Entity target;

    public EnrageCommand() {
        target = null;
    }

    public EnrageCommand(Entity target) {
        this.target = target;
    }

    @Override
    public void trigger(Entity e) {
        e.enrage(target);
    }

    @Override
    public void accept(Visitor v) {
        v.visitEnrageCommand(this);
    }
}
