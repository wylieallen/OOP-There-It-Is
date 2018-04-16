package commands.skillcommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ParalyzeCommand extends SkillCommand {

    private int entityBaseMoveSpeed = 0;

    public ParalyzeCommand(SkillType skillType, int level, int effectiveness) {
        super(skillType, level, effectiveness);
    }

    @Override
    protected void success(Entity e, int distance) {
        entityBaseMoveSpeed = e.getBaseMoveSpeed();
        e.decreaseBaseMoveSpeed(entityBaseMoveSpeed);
    }

    @Override
    protected void fail(Entity e, int distance) {
        // TODO: make enity mad
    }

    @Override
    public void accept(Visitor v) {
        v.visitParalyzeCommand(this);
    }
}