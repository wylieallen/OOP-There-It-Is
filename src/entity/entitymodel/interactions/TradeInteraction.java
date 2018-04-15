package entity.entitymodel.interactions;

import entity.entitymodel.Entity;

import entity.entitymodel.interactions.EntityInteraction;
import savingloading.Visitor;

/**
 * Created by dontf on 4/13/2018.
 */
public class TradeInteraction implements EntityInteraction {

    private double costMultiplier;

    @Override
    public void interact(Entity actor, Entity actee) {

    }

    @Override
    public void accept(Visitor v) {
        v.visitTradeInteraction(this);
    }
}
