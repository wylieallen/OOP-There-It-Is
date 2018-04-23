package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import savingloading.Visitable;

/**
 * Created by dontf on 4/13/2018.
 */
public interface EntityInteraction extends Visitable {
    String name ();
    boolean interact (Entity actor, Entity actee);
}
