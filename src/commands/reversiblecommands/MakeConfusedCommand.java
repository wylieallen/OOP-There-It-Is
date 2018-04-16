package commands.reversiblecommands;

import entity.entitymodel.Entity;

public class MakeConfusedCommand extends ReversibleCommand {

    public MakeConfusedCommand(boolean isApplied) {
        super(isApplied);

    }

    @Override
    protected void apply(Entity e) {
        e.makeConfused();
    }

    @Override
    protected void unapply(Entity e) {
        e.makeUnconfused();
    }
}
