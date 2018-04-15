package entity.entitymodel.interactions;

import entity.entitymodel.Entity;


/**
 * Created by dontf on 4/13/2018.
 */
public class MountInteraction implements EntityInteraction {

    @Override
    public boolean interact(Entity actor, Entity actee) {
        // check for driver in vehicl if so return false;
        actor.setOnMap(false);
        return false;
    }

}
