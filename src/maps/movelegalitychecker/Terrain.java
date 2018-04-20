package maps.movelegalitychecker;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public enum Terrain implements MoveLegalityChecker{

    GRASS, WATER, MOUNTAIN, SPACE;

    @Override
    public boolean canMoveHere(Entity e) {
        return e.isTerrainCompatible(this);
    }

    @Override
    public void accept(Visitor v) {
        v.visitTerrain(this);
    }
}
