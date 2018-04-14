package commands.skillcommands;

import entitymodel.Entity;
import skills.SkillType;
import commands.Command;

public abstract class SkillCommand implements Command {
    private SkillType skillType;
    private int level;
    private int effectiveness;

    public SkillCommand(SkillType skillType, int level, int effectiveness) {
        this.skillType = skillType;
        this.level = level;
        this.effectiveness = effectiveness;
    }

    protected SkillType getSkillType() { return this.skillType; }
    protected int getLevel() { return this.level; }
    protected int getEffectiveness() { return this.effectiveness; }

    public abstract void trigger(Entity e, int distance);
    protected abstract void success(Entity e, int distance);
    protected abstract void fail(Entity e, int distance);

}
