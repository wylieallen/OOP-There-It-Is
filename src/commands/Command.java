package commands;

import entity.entitymodel.Entity;

public interface Command {

    void trigger(Entity e);

}
