package commands.reversiblecommands;

import entity.entitymodel.Entity;
import commands.Command;

public abstract class ReversibleCommand implements Command {

    private boolean isApplied;

    public ReversibleCommand(boolean isApplied) {
        this.isApplied = isApplied;
    }

    public void trigger(Entity e){
        if (!isApplied)
            apply(e);
        else
            unapply(e);
        isApplied = !isApplied;
    }

    protected abstract void apply(Entity e);
    protected abstract void unapply(Entity e);

    protected boolean isApplied(){
        return isApplied;
    }

}
