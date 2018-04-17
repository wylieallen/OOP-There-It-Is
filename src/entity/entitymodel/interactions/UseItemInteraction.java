package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;

/**
 * Created by dontf on 4/13/2018.
 */
public class UseItemInteraction implements EntityInteraction {

    @Override
    public boolean interact(Entity actor, Entity actee) {
        TakeableItem use = actor.getRandomItem();
        return false;
    }

}
