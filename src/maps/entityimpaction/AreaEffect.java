package maps.entityimpaction;

import entity.entitymodel.Entity;
import commands.Command;

public abstract class AreaEffect implements EntityImpactor {

    private Command command;
    private String name;

    public AreaEffect(Command command, String name) {
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

    public String name(){
        return name;
    }
}
