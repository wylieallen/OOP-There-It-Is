package maps.movelegalitychecker;

import entity.entitymodel.Entity;
import gameobject.GameObject;

public interface MoveLegalityChecker extends GameObject {

    boolean canMoveHere(Entity e);
}
