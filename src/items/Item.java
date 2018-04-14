package items;

import entitymodel.Entity;

public abstract class Item {

    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void touch(Entity e);
}
