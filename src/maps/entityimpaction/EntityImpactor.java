package maps.entityimpaction;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import savingloading.Visitable;

public interface EntityImpactor extends GameObject, Visitable {
    void touch(Entity entity);
    boolean shouldBeRemoved();
}
