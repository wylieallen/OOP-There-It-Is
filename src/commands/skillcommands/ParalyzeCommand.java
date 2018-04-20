package commands.skillcommands;

import commands.TimedEffect;
import commands.reversiblecommands.MakeParalyzedCommand;
import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ParalyzeCommand extends SkillCommand {

    private int entityBaseMoveSpeed = 0;
    private Entity caster;

    public ParalyzeCommand(SkillType skillType, int level, int effectiveness, Entity caster) {
        super(skillType, level, effectiveness);
        this.caster = caster;
    }

    @Override
    protected void success(Entity e, int distance) {
        int adjustedEffectiveness = getSkillType().calculateModification(getEffectiveness(), distance, getLevel());
        TimedEffect effect = new TimedEffect(
                new MakeParalyzedCommand(false), adjustedEffectiveness * 1000, 0);
        e.addTimedEffect(effect);
    }

    @Override
    protected void fail(Entity e, int distance) {
        e.enrage(caster);
    }

    @Override
    public void accept(Visitor v) {
        v.visitParalyzeCommand(this);
    }
}