package item.takeableitem;

import EnitityModel.Entity;
import EnitityModel.Equipment;
import item.Item;

public abstract class TakeableItem extends Item {

    public TakeableItem(String name) {
        super(name);
    }

    public void touch(Entity e) {

    }

    public abstract void activate(Equipment e);
}
