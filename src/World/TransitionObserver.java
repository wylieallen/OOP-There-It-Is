package World;

import entitymodel.Entity;
import Utilities.Coordinate;

/**
 * Created by dontf on 4/14/2018.
 */
public interface TransitionObserver {

    public void notifyTransition (Entity e, World target, Coordinate p);

}
