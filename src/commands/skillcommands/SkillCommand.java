package commands.skillcommands;

import entity.entitymodel.Entity;
import savingloading.Visitable;
import skills.SkillType;
import commands.Command;

import java.util.Random;

// SkillCommand initially implemented Command,
//  but trigger(Entity e) in Command is not
//  trigger(Entity e, int distance) in SkillCommand
//  so there would be a method not being used (mixed-instance cohesion)


public abstract class SkillCommand implements Command, Visitable {
    private SkillType skillType;
    private int level;
    private int effectiveness; // range: 1-100

    public SkillCommand(SkillType skillType, int level, int effectiveness) {
        this.skillType = skillType;
        this.level = level;
        this.effectiveness = effectiveness;
    }

    protected SkillType getSkillType() { return this.skillType; }
    protected int getLevel() { return this.level; }

    public void trigger(Entity e, int distance) {
        boolean success = getSkillType().checkSuccess(e.getSkillLevel(getSkillType()), distance);
        if(success) {
            success(e, distance);
        } else {
            fail(e, distance);
        }
    }

    protected abstract void success(Entity e, int distance);
    protected abstract void fail(Entity e, int distance);

    @Override
    public void trigger(Entity e) {
        success(e, 0);
    }

    public int getEffectiveness() {
        return effectiveness;
    }
}
