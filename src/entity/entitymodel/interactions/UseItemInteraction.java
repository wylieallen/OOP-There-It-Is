package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import savingloading.Visitor;

/**
 * Created by dontf on 4/13/2018.
 */
public class UseItemInteraction implements EntityInteraction {

    @Override
    public boolean interact(Entity actor, Entity actee) {
        actor.getController().notifyUseItem(actor, actee.getController());
        return true;
    }

    @Override
    public String name () { return "Use Item"; }

    @Override
    public void accept(Visitor v) {
        v.visitUseItemInteraction(this);
    }
}
