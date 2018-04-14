package movelegalitychecker;

import entitymodel.Entity;

public class Obstacle implements MoveLegalityChecker {

    @Override
    public boolean canMoveHere(Entity e) {
        return false;
    }
}
