package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import skills.SkillType;

/**
 * Created by dontf on 4/13/2018.
 */
public class PickPocketInteraction implements EntityInteraction {

    private final int xpIncrease = 10;

    @Override
    public boolean interact(Entity actor, Entity actee) {

        if (SkillType.PICKPOCKET.checkSuccess(actor.getSkillLevel(SkillType.PICKPOCKET), 1)) {
            TakeableItem picked = actee.getRandomItem();

            if (picked != null) {
                actee.removeFromInventory(picked);
                actor.addToInventory(picked);
                actor.increaseXP(xpIncrease);
            }
            return true;
        }

        return false;
    }

    public void testInteractFunction (Entity actor, Entity actee, boolean success) {
        if (success) {
            TakeableItem picked = actee.getRandomItem();

            if (picked != null) {
                actee.removeFromInventory(picked);
                actor.addToInventory(picked);
                actor.increaseXP(xpIncrease);
            }
        }
    }

}
