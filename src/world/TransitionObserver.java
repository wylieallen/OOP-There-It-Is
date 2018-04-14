package world;

import entitymodel.Entity;
import utilities.Coordinate;

/**
 * Created by dontf on 4/14/2018.
 */
public interface TransitionObserver {

    void notifyTransition (Entity e, World target, Coordinate p);

}
