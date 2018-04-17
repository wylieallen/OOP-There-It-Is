package items;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.entityimpaction.EntityImpactor;

public abstract class Item implements GameObject, EntityImpactor {

    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }
}
