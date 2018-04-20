package maps.movelegalitychecker;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import savingloading.Visitor;

public enum Terrain implements GameObject {

    GRASS, WATER, MOUNTAIN, SPACE;

    public boolean canMoveHere(Entity e) {
        return e.isTerrainCompatible(this);
    }

    @Override
    public void accept(Visitor v) {
        v.visitTerrain(this);
    }
}
