package commands.skillcommands;

import commands.TimedEffect;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ModifyStaminaRegenCommand extends SkillCommand {

    private double factor;

    public ModifyStaminaRegenCommand(SkillType skillType, int level, int effectiveness, double factor) {
        super(skillType, level, effectiveness);
        this.factor = factor;
    }

    @Override
    protected void success(Entity e, int distance) {
        int adjustedEffectiveness = getSkillType().calculateModification(getEffectiveness(), distance, getLevel());
        TimedEffect effect = new TimedEffect(
                new TimedStaminaRegenCommand(false, 0, factor), adjustedEffectiveness);
        e.addTimedEffect(effect);
    }

    @Override
    protected void fail(Entity e, int distance) {

    }

    @Override
    public void accept(Visitor v) {
        v.visitModifyStaminaRegenCommand(this);
    }
}
