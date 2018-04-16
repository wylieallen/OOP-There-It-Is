package commands.skillcommands;

import entity.entitymodel.Entity;
import skills.SkillType;

// not sure how this will work
public class ObserveCommand extends SkillCommand {

    public ObserveCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }


    //This will take in the entity that is being observed and the distance they are from the entity that spawned the
    //observation influence area and make some observation
    @Override
    protected void success(Entity e, int distance) {

    }

    @Override
    protected void fail(Entity e, int distance) {

    }

}
