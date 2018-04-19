package maps.movelegalitychecker;

import entity.entitymodel.Entity;
import gameobject.GameObject;

public enum Terrain implements GameObject {

    GRASS, WATER, MOUNTAIN;

    public boolean canMoveHere(Entity e) {
        return e.isTerrainCompatible(this);
    }
}
