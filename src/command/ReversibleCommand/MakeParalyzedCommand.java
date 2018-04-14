package command.ReversibleCommand;

import EnitityModel.Entity;
import command.ReversibleCommand.ReversibleCommand;

public class MakeParalyzedCommand extends ReversibleCommand {

    public MakeParalyzedCommand(boolean isApplied) {
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
