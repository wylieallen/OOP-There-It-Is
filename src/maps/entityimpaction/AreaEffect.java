package entityimpaction;

import entity.entitymodel.Entity;
import commands.Command;

public abstract class AreaEffect implements EntityImpactor {

    private Command command;

    public abstract void touch(Entity entity);
}
