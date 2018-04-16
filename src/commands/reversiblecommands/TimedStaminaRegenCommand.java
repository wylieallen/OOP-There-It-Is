package commands.reversiblecommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class TimedStaminaRegenCommand extends ReversibleCommand {

    private int staminaRegenDecrease = 0;

    public TimedStaminaRegenCommand(boolean isApplied, int staminaRegenDecrease) {
        super(isApplied);
        this.staminaRegenDecrease = staminaRegenDecrease;
    }

    @Override
    protected void apply(Entity e) {
        // TODO: decrease stamina regeneration rate in entity
    }

    @Override
    protected void unapply(Entity e) {

    }

    public int getStaminaRegenDecrease(){
        return staminaRegenDecrease;
    }

    @Override
    public void accept(Visitor v) {
        v.visitTimedStaminaRegenCommand(this);
    }
}
