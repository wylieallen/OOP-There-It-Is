package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import savingloading.Visitor;


/**
 * Created by dontf on 4/13/2018.
 */
public class MountInteraction implements EntityInteraction {

    @Override
    public boolean interact(Entity actor, Entity actee) {
        actee.interact(actor);
        return true;
    }

    @Override
    public void accept(Visitor v) {
        v.visitMountInteraction(this);
    }
}
