package entity.entitymodel.interactions;

import entity.entitymodel.Entity;


/**
 * Created by dontf on 4/13/2018.
 */
public class MountInteraction implements EntityInteraction {

    @Override
    public boolean interact(Entity actor, Entity actee) {
        actor.setOnMap(false);
        return false;
    }

}
