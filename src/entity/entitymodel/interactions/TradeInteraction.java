package entity.entitymodel.interactions;

import entity.entitymodel.Entity;

/**
 * Created by dontf on 4/13/2018.
 */
public class TradeInteraction implements EntityInteraction {

    private double costMultiplier;

    @Override
    public boolean interact(Entity actor, Entity actee) {
        return false;
    }

}
