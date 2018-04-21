package commands;

import commands.Command;
import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import savingloading.Visitor;
import skills.SkillType;

public class PickPocketCommand implements Command {

    private Entity caster;

    public PickPocketCommand() {
        this.caster = null;
    }

    public PickPocketCommand(Entity caster) {
        this.caster = caster;
    }

    @Override
    public void trigger(Entity e) {
        TakeableItem item = e.pickPocket();

        if(caster != null) {
            caster.addToInventory(item);
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visitPickPocketCommand(this);
    }
}
