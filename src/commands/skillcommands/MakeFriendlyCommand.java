package commands.skillcommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class MakeFriendlyCommand extends SkillCommand {

    Entity caster;

    public MakeFriendlyCommand(int level, int effectiveness, Entity caster) {
        super(SkillType.ENCHANTMENT, level, effectiveness);
        this.caster = caster;
    }

    @Override
    protected void success(Entity e, int distance) {
        e.pacify();
    }

    @Override
    protected void fail(Entity e, int distance) {
        e.enrage(caster);
    }

    @Override
    public void accept(Visitor v) {
        v.visitMakeFriendlyCommand(this);
    }
}
