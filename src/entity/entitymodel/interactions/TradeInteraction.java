package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import skills.SkillType;

/**
 * Created by dontf on 4/13/2018.
 */
public class TradeInteraction implements EntityInteraction {

    private double costMultiplier;
    private final double baseMultiplier = 20;
    private Entity shoper;
    private Entity shopKeeper;

    @Override
    public boolean interact(Entity actor, Entity actee) {
        shoper = actor;
        shopKeeper = actee;
        costMultiplier = baseMultiplier / shoper.getSkillLevel(SkillType.BARGAIN);
        return true;
    }

    public boolean buyItem (int itemIndex) {
        TakeableItem product = shopKeeper.getItem(itemIndex);

        if (purchase(shoper, costMultiplier)) {
            swapItem(product, shoper, shopKeeper);
            return true;
        }

        return false;
    }

    public boolean sellItem (int itemIndex) {
        TakeableItem product = shopKeeper.getItem(itemIndex);

        if (purchase(shopKeeper, costMultiplier)) {
            swapItem(product, shopKeeper, shoper);
            return true;
        }

        return false;
    }

    private boolean purchase (Entity buyer, double price) {
        return buyer.decreaseGold(price);
    }

    private void swapItem (TakeableItem item, Entity giveTo, Entity takeFrom) {
            takeFrom.removeFromInventory(item);
            giveTo.addToInventory(item);
    }

}
