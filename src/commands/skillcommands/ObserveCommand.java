package commands.skillcommands;

import entity.entitymodel.Entity;
import skills.SkillType;

// not sure how this will work
public class ObserveCommand extends SkillCommand {

    public ObserveCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {

    }

    @Override
    protected void fail(Entity e, int distance) {

    }

}
