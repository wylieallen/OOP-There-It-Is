package commands.reversiblecommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class TimedStaminaRegenCommand extends ReversibleCommand {

    private int cachedStaminaRegenDifference;
    private double factor;

    public TimedStaminaRegenCommand(boolean isApplied, int cachedStaminaRegenDifference, double factor) {
        super(isApplied);
        this.cachedStaminaRegenDifference = cachedStaminaRegenDifference;
        this.factor = factor;
    }

    @Override
    protected void apply(Entity e) {
        // TODO: decrease stamina regeneration rate in entity
        int oldStaminaRegen = e.getManaRegenRate();
        int newStaminaRegen = (int)(oldStaminaRegen * factor);
        cachedStaminaRegenDifference = newStaminaRegen - oldStaminaRegen;
        e.setManaRegenRate(newStaminaRegen);
    }

    @Override
    protected void unapply(Entity e) {
        int oldStaminaRegen = e.getManaRegenRate() - cachedStaminaRegenDifference;
        e.setManaRegenRate(oldStaminaRegen);
    }

    public int getStaminaRegenDecrease(){
        return cachedStaminaRegenDifference;
    }

    public double getFactor(){
        return factor;
    }

    @Override
    public void accept(Visitor v) {
        v.visitTimedStaminaRegenCommand(this);
    }
}
