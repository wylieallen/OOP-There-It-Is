package maps.entityimpaction;

import entity.entitymodel.Entity;
import commands.Command;
import maps.entityimpaction.EntityImpactor;
import maps.world.Game;

public abstract class AreaEffect implements EntityImpactor {

    private Command command;

    public AreaEffect(Command command) {
        this.command = command;
    }

    public abstract void touch(Entity entity);

    protected void trigger(Entity e) {
        command.trigger(e);
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }

    public Command getCommand() {
        return command;
    }
}
