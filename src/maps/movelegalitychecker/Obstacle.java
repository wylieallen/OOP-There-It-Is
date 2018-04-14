package maps.movelegalitychecker;

import entity.entitymodel.Entity;

public class Obstacle implements MoveLegalityChecker {

    @Override
    public boolean canMoveHere(Entity e) {
        return false;
    }

    @Override
    public boolean expired() { return false; }

    @Override
    public void update() {}
}
