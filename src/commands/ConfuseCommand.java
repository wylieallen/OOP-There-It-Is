package commands;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ConfuseCommand implements Command {

    private int duration;

    public ConfuseCommand() {
        this.duration = 0;
    }

    public ConfuseCommand(int duration) {
        this.duration = duration;
    }

    @Override
    public void trigger(Entity e) {
        trigger(e, duration);
    }

    @Override
    public void trigger(Entity e, int duration) {
        TimedEffect effect = new TimedEffect(new MakeConfusedCommand(false), duration);
        e.addTimedEffect(effect);
    }

    @Override
    public void accept(Visitor v) {
        v.visitConfuseCommand(this);
    }
}
