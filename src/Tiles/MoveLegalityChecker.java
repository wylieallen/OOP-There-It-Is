package Tiles;

import EnitityModel.Entity;

public interface MoveLegalityChecker {
    public boolean canMoveHere(Entity entity);
}
