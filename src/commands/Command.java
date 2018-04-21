package commands;

import entity.entitymodel.Entity;
import savingloading.Visitable;

public interface Command extends Visitable{

    void trigger(Entity e);
    default void trigger(Entity e, int effectiveness) { trigger(e); }

}
