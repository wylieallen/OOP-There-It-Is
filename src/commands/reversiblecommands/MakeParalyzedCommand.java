package commands.reversiblecommands;

import entity.entitymodel.Entity;

public class MakeParalyzedCommand extends ReversibleCommand {

    private int entityBaseMoveSpeed = 0;

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
}
