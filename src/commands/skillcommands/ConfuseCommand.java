package commands.skillcommands;

import commands.TimedEffect;
import commands.reversiblecommands.MakeConfusedCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ConfuseCommand extends SkillCommand {

    private Entity caster;

    public ConfuseCommand(int level, int effectiveness, Entity caster) {
        super(SkillType.ENCHANTMENT, level, effectiveness);
        this.caster = caster;
    }

    @Override
    protected void success(Entity e, int distance) {
        int adjustedEffectiveness = getSkillType().calculateModification(getEffectiveness(), distance, getLevel());
        TimedEffect effect = new TimedEffect(new MakeConfusedCommand(false), adjustedEffectiveness * 1000, 0);
        e.addTimedEffect(effect);
    }

    @Override
    protected void fail(Entity e, int distance) {
        e.enrage(caster);
    }

    @Override
    public void accept(Visitor v) {
        v.visitConfuseCommand(this);
    }
}
