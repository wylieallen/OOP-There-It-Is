package maps.entityimpaction;

import entity.entitymodel.Entity;
import commands.Command;
import maps.entityimpaction.EntityImpactor;

public abstract class AreaEffect implements EntityImpactor {

    private Command command;

    public abstract void touch(Entity entity);

    protected void trigger(Entity e) {
        command.trigger(e);
    }
}
