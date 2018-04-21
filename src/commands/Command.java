package commands;

import entity.entitymodel.Entity;
import savingloading.Visitable;

public interface Command extends Visitable{

    void trigger(Entity e);

}
