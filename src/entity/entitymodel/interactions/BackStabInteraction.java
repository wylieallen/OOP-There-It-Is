package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

/**
 * Created by dontf on 4/13/2018.
 */
public class BackStabInteraction implements EntityInteraction {

    private final int baseDamage = 25;
    private final int xpIncrease = 10;

    @Override
    public boolean interact(Entity actor, Entity actee) {

        if (SkillType.CREEP.checkSuccess(actor.getSkillLevel(SkillType.CREEP), 1)) {
            actee.hurtEntity(SkillType.CREEP.calculateModification(baseDamage, 1, actor.getSkillLevel(SkillType.CREEP)));
            actor.increaseXP(xpIncrease);
            return true;
        }

        return false;

    }

    public void testInteractFunction (Entity actor, Entity actee, boolean success) {
        if (success) {
            actee.hurtEntity(baseDamage);
            actor.increaseXP(xpIncrease);
        }
    }

    @Override
    public String name () { return "Back Stab"; }

    @Override
    public void accept(Visitor v) {
        v.visitBackStabInteraction(this);
    }
}
