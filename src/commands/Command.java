package commands;

import entitymodel.Entity;

public interface Command {

    void trigger(Entity e);

}
