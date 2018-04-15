package commands.reversiblecommands;

import entity.entitymodel.Entity;

public class MakeConfusedCommand extends ReversibleCommand {

    private int decreaseAmount;

    public MakeConfusedCommand(boolean isApplied, int decreaseAmount) {
        super(isApplied);
        this.decreaseAmount = decreaseAmount;
    }

    @Override
    protected void apply(Entity e) {
        e.decreaseVisibilityRadious(decreaseAmount);
    }

    @Override
    protected void unapply(Entity e) {
        e.increaseVisibilityRadious(decreaseAmount);
    }
}
