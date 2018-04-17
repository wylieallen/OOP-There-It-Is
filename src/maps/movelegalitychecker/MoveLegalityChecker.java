package maps.movelegalitychecker;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import savingloading.Visitable;

public interface MoveLegalityChecker extends GameObject, Visitable {

    boolean canMoveHere(Entity e);
}
