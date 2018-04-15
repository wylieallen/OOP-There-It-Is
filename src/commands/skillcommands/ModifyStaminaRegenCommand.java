package commands.skillcommands;

import entity.entitymodel.Entity;
import skills.SkillType;

public class ModifyStaminaRegenCommand extends SkillCommand {

    public ModifyStaminaRegenCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {
        // TODO: decrease stamina regen in entity
    }

    @Override
    protected void fail(Entity e, int distance) {

    }

}
