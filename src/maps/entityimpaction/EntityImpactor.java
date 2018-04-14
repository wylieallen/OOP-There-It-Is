package maps.entityimpaction;

import entity.entitymodel.Entity;
import gameobject.GameObject;

public interface EntityImpactor extends GameObject {
    void touch(Entity entity);
}
