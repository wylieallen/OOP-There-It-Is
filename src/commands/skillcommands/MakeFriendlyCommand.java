package commands.skillcommands;

import entity.entitymodel.Entity;
import skills.SkillType;

public class MakeFriendlyCommand extends SkillCommand {

    public MakeFriendlyCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {
        // TODO: make friendly
    }

    @Override
    protected void fail(Entity e, int distance) {
        // TODO: make entity mad
    }

}
