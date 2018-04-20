package commands;

import commands.Command;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class MakeFriendlyCommand implements Command {

    public MakeFriendlyCommand() {

    }

    @Override
    public void trigger(Entity e) {
        e.pacify();
    }

    @Override
    public void accept(Visitor v) {
        v.visitMakeFriendlyCommand(this);
    }
}
