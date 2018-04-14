package command;

import EnitityModel.Entity;

public interface Command {

    void trigger(Entity e);

}
