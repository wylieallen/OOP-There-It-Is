package maps.trajectorymodifier;

import savingloading.Visitable;
import utilities.Vector;
import gameobject.GameObject;

public interface TrajectoryModifier extends GameObject, Visitable {
    Vector getVector();
}
