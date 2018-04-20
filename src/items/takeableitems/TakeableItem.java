package items.takeableitems;

import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import items.Item;

public abstract class TakeableItem extends Item {

    private boolean onMap;

    public TakeableItem(String name, boolean onMap) {
        super(name);
        this.onMap = onMap;
    }

    public void touch(Entity e) {
        e.addToInventory(this);
        onMap = false;
    }

    public abstract void activate(Equipment e);

    @Override
    public boolean shouldBeRemoved() {
        return !onMap;
    }

    public boolean isOnMap(){
        return onMap;
    }
}
