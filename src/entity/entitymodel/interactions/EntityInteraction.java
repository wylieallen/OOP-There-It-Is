package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import savingloading.Visitable;
import savingloading.Visitor;

/**
 * Created by dontf on 4/13/2018.
 */
public interface EntityInteraction extends Visitable {
    boolean interact (Entity actor, Entity actee);
}
