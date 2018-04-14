package Tiles;

import entitymodel.Entity;

public interface MoveLegalityChecker {
    public boolean canMoveHere(Entity entity);
}
