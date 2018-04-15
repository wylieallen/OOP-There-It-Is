package commands.reversiblecommands;

import entity.entitymodel.Entity;

public class MakeParalyzedCommand extends ReversibleCommand {

    public MakeParalyzedCommand(boolean isApplied) {
        super(isApplied);
    }

    @Override
    protected void apply(Entity e) {
        e.decreaseBaseMoveSpeed(e.getBaseMoveSpeed());
    }

    @Override
    protected void unapply(Entity e) {

    }
}
