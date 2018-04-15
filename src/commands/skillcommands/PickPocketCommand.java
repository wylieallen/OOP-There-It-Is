package commands.skillcommands;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import skills.SkillType;

public class PickPocketCommand extends SkillCommand {

    public PickPocketCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {
        TakeableItem item = e.pickPocket();
        // TODO: how to get item into the Entity using the skill's inventory?
    }

    @Override
    protected void fail(Entity e, int distance) {
        // nothing
    }

}
