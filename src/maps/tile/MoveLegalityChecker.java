package maps.tile;

import entity.entitymodel.Entity;

public interface MoveLegalityChecker {
    boolean canMoveHere(Entity entity);
}
