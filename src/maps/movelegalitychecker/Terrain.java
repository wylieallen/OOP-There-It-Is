package maps.movelegalitychecker;

import entity.entitymodel.Entity;

public enum Terrain implements MoveLegalityChecker{

    GRASS, WATER, MOUNTAIN;

    @Override
    public boolean canMoveHere(Entity e) {
        return e.isTerrainCompatible(this);
    }
}
