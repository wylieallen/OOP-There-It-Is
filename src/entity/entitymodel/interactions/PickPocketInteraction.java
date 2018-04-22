package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import savingloading.Visitor;
import skills.SkillType;

/**
 * Created by dontf on 4/13/2018.
 */
public class PickPocketInteraction implements EntityInteraction {

    private final int xpIncrease = 10;

    @Override
    public boolean interact(Entity actor, Entity actee) {

        if (SkillType.PICKPOCKET.checkSuccess(actor.getSkillLevel(SkillType.PICKPOCKET), 1)) {
            TakeableItem picked = actee.pickPocket();

            if (picked != null) {
                actee.removeFromInventory(picked);
                actor.addToInventory(picked);
                actor.increaseXP(xpIncrease);
            }
            return true;
        }

        return false;
    }

    @Override
    public void accept(Visitor v) {
        v.visitPickPocketInteraction(this);
    }
}
