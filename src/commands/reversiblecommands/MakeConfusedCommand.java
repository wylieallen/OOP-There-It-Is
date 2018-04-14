package commands.reversiblecommands;

import entity.entitymodel.Entity;

public class MakeConfusedCommand extends ReversibleCommand {

    public MakeConfusedCommand(boolean isApplied) {
        super(isApplied);
    }

    @Override
    public void trigger(Entity e) {

    }

    @Override
    protected void apply(Entity e) {

    }

    @Override
    protected void unapply(Entity e) {

    }
}
