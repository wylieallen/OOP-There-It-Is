package commands;

import commands.TimedEffect;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ModifyStaminaRegenCommand implements Command {

    private double factor;
    private int duration;

    public ModifyStaminaRegenCommand(double factor) {
        this.factor = factor;
        this.duration = 0;
    }

    public ModifyStaminaRegenCommand(double factor, int duration) {
        this.factor = factor;
        this.duration = duration;
    }

    @Override
    public void trigger(Entity e) {
        trigger(e, duration);
    }

    @Override
    public void trigger(Entity e, int duration) {
        TimedEffect effect = new TimedEffect(
                new TimedStaminaRegenCommand(false, 0, factor), duration);
        e.addTimedEffect(effect);
    }

    public double getFactor(){
        return factor;
    }

    @Override
    public void accept(Visitor v) {
        v.visitModifyStaminaRegenCommand(this);
    }
}
