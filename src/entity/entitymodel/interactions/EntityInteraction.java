package entity.entitymodel.interactions;

import entity.entitymodel.Entity;

/**
 * Created by dontf on 4/13/2018.
 */
public interface EntityInteraction {
    boolean interact (Entity actor, Entity actee);
}
