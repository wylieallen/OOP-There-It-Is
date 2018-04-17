package commands.reversiblecommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

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

    public int getVisibilityDecreaseAmount(){
        return visibilityDecreaseAmount;
    }

    @Override
    public void accept(Visitor v) {
        v.visitMakeConfusedCommand(this);
    }
}
