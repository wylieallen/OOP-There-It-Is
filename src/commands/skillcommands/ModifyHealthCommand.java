package commands.skillcommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ModifyHealthCommand extends SkillCommand {

    private int healthDecrement;

    public ModifyHealthCommand(SkillType skillType, int level, int effectiveness, int healthDecrement) {
        super(skillType, level, effectiveness);
        this.healthDecrement = healthDecrement;
    }

    @Override
    protected void success(Entity e, int distance) {
        e.hurtEntity(healthDecrement);
    }

    @Override
    protected void fail(Entity e, int distance) {
        // nothing
    }

    public int getHealthDecrement(){
        return healthDecrement;
    }

    @Override
    public void accept(Visitor v) {
        v.visitModifyHealthCommand(this);
    }
}
