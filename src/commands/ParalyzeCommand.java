package commands;

import commands.Command;
import commands.TimedEffect;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ParalyzeCommand implements Command {

    private int duration;

    public ParalyzeCommand() {
        duration = 0;
    }

    public ParalyzeCommand(int duration) {
        this.duration = duration;
    }

    @Override
    public void trigger(Entity e) {
        trigger(e, duration);
    }

    @Override
    public void trigger(Entity e, int duration) {
        TimedEffect effect = new TimedEffect(
                new MakeParalyzedCommand(false), duration, 0);
        e.addTimedEffect(effect);
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void accept(Visitor v) {
        v.visitParalyzeCommand(this);
    }

    public int getDuration() { return duration; }
}