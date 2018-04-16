package commands.skillcommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ConfuseCommand extends SkillCommand {

    private int radiusDecrease = 10;

    public ConfuseCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {
        e.decreaseVisibilityRadious(radiusDecrease-distance);
    }

    @Override
    protected void fail(Entity e, int distance) {
        // TODO: make entity mad or something
    }

    @Override
    public void accept(Visitor v) {
        v.visitConfuseCommand(this);
    }
}
