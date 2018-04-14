package EntityImpaction;

import EnitityModel.Entity;
import command.Command;

public abstract class AreaEffect implements EntityImpactor {

    private Command command;

    public abstract void touch(Entity entity);
}
