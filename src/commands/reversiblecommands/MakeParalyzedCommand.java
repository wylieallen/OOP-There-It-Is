package commands.reversiblecommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class MakeParalyzedCommand extends ReversibleCommand {

    private double entityBaseMoveSpeed = 0;

    public MakeParalyzedCommand(boolean isApplied) {
        super(isApplied);
    }

    @Override
    protected void apply(Entity e) {
        entityBaseMoveSpeed = e.getBaseMoveSpeed();
        e.decreaseBaseMoveSpeed(entityBaseMoveSpeed);
    }

    @Override
    protected void unapply(Entity e) {
        e.increaseBaseMoveSpeed(entityBaseMoveSpeed);
    }

    @Override
    public void accept(Visitor v) {
        v.visitMakeParalyzedCommand(this);
    }

    public double getEntityBaseMoveSpeed() {
        return entityBaseMoveSpeed;
    }
}
