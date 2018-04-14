package movelegalitychecker;

import EnitityModel.Entity;

public enum Terrain implements MoveLegalityChecker{

    GRASS, WATER, MOUNTAIN;

    @Override
    public boolean canMoveHere(Entity e) {
        return false;
    }
}
