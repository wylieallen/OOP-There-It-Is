package commands.reversiblecommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class MakeConfusedCommand extends ReversibleCommand {

    private int visibilityDecreaseAmount;

    public MakeConfusedCommand(boolean isApplied, int visibilityDecreaseAmount) {
        super(isApplied);
        this.visibilityDecreaseAmount = visibilityDecreaseAmount;
    }

    @Override
    protected void apply(Entity e) {
        e.decreaseVisibilityRadious(visibilityDecreaseAmount);
    }

    @Override
    protected void unapply(Entity e) {
        e.increaseVisibilityRadious(visibilityDecreaseAmount);
    }

    public int getVisibilityDecreaseAmount(){
        return visibilityDecreaseAmount;
    }

    @Override
    public void accept(Visitor v) {
        v.visitMakeConfusedCommand(this);
    }
}
