package items;

import entity.entitymodel.Entity;
import savingloading.Visitable;

public abstract class Item implements Visitable{

    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void touch(Entity e);
}
