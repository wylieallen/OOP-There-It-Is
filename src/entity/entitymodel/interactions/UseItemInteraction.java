package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import savingloading.Visitor;

/**
 * Created by dontf on 4/13/2018.
 */
public class UseItemInteraction implements EntityInteraction {

    @Override
    public boolean interact(Entity actor, Entity actee) {
        TakeableItem use = actor.getRandomItem();
        actor.useItem (use);
        return false;
    }

    @Override
    public void accept(Visitor v) {
        v.visitUseItemInteraction(this);
    }
}
