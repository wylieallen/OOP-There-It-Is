package command.SkillCommand;

import EnitityModel.Entity;
import Skills.SkillType;

public class ModifyStaminaRegenCommand extends SkillCommand {

    public ModifyStaminaRegenCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    public void trigger(Entity e, int distance) {

    }

    @Override
    protected void success(Entity e, int distance) {

    }

    @Override
    protected void fail(Entity e, int distance) {

    }

    @Override
    public void trigger(Entity e) {

    }
}
