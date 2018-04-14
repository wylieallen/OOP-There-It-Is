package items.takeableitems;

import entitymodel.Entity;
import entitymodel.Equipment;
import items.Item;

public abstract class TakeableItem extends Item {

    public TakeableItem(String name) {
        super(name);
    }

    public void touch(Entity e) {

    }

    public abstract void activate(Equipment e);
}
