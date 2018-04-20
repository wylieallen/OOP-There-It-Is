package commands;

import entity.entitymodel.Entity;
import savingloading.Visitor;
import skills.SkillType;

public class ModifyHealthCommand implements Command {

    private int modifyAmount;

    public ModifyHealthCommand() {
        this.modifyAmount = 0;
    }

    public ModifyHealthCommand(int modifyAmount) {
        this.modifyAmount = modifyAmount;
    }

    @Override
    public void trigger(Entity e) {
        trigger(e, modifyAmount);
    }

    @Override
    public void trigger(Entity e, int amount) {
        if(amount > 0) {
            e.healEntity(amount);
        } else {
            e.hurtEntity(-amount);
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visitModifyHealthCommand(this);
    }
}
