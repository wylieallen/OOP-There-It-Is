package commands.skillcommands;

import entity.entitymodel.Entity;
import savingloading.Visitable;
import savingloading.Visitor;
import skills.SkillType;
import commands.Command;

import java.util.Random;

// SkillCommand initially implemented Command,
//  but trigger(Entity e) in Command is not
//  trigger(Entity e, int distance) in SkillCommand
//  so there would be a method not being used (mixed-instance cohesion)


public class SkillCommand implements Visitable {
    private SkillType skillType;
    private int level;
    private int effectiveness;
    private Command successCommand;
    private Command failureCommand;

    public SkillCommand(SkillType skillType, int level, int effectiveness, Command successCommand,
                        Command failureCommand) {
        this.skillType = skillType;
        this.level = level;
        this.effectiveness = effectiveness;
        this.successCommand = successCommand;
        this.failureCommand = failureCommand;
    }

    public void trigger(Entity e) {
        trigger(e, 0);
    }

    public void trigger(Entity e, int distance) {
        boolean success = getSkillType().checkSuccess(e.getSkillLevel(getSkillType()), distance);
        int adjustedEffectiveness = getSkillType().calculateModification(effectiveness, distance, level);

        if(success && successCommand != null) {
            successCommand.trigger(e, adjustedEffectiveness);
        } else if(failureCommand != null) {
            failureCommand.trigger(e, adjustedEffectiveness);
        }
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public void setLevel(int newLevel) {
        level = newLevel;
    }

    public SkillType getSkillType() {
        return this.skillType;
    }

    public int getLevel() {
        return this.level;
    }

    @Override
    public void accept(Visitor v) {
        v.visitSkillCommand(this);
    }
}
