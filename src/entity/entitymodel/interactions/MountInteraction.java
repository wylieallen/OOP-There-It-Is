package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import savingloading.Visitor;


/**
 * Created by dontf on 4/13/2018.
 */
public class MountInteraction implements EntityInteraction {

    @Override
    public void interact(Entity actor, Entity actee) {
        //TODO: make sure isOnMap set to false;
    }

    @Override
    public void accept(Visitor v) {
        v.visitMountInteraction(this);
    }
}
