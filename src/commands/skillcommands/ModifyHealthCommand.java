package commands.skillcommands;

import entity.entitymodel.Entity;
import skills.SkillType;

public class ModifyHealthCommand extends SkillCommand {

    public ModifyHealthCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {
        int adjustedEffectiveness = getSkillType().calculateModification(getEffectiveness(),
                                                        distance, getLevel());
        if(adjustedEffectiveness > 0) {
            e.healEntity(adjustedEffectiveness);
        } else {
            e.hurtEntity(-adjustedEffectiveness);
        }
    }

    @Override
    protected void fail(Entity e, int distance) {

    }
}
