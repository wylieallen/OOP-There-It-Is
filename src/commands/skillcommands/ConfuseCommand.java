package commands.skillcommands;

import entitymodel.Entity;
import skills.SkillType;

public class ConfuseCommand extends SkillCommand {

    public ConfuseCommand(SkillType skillType, int level, int effectiveness) {
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
