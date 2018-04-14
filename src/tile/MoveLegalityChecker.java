package tile;

import entitymodel.Entity;

public interface MoveLegalityChecker {
    boolean canMoveHere(Entity entity);
}
