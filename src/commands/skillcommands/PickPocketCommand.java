package commands.skillcommands;

import entity.entitymodel.Entity;
import items.takeableitems.TakeableItem;
import savingloading.Visitor;
import skills.SkillType;

public class PickPocketCommand extends SkillCommand {

    private Entity caster;

    public PickPocketCommand(int level, int effectiveness, Entity caster) {
        super(SkillType.PICKPOCKET, level, effectiveness);
        this.caster = caster;
    }

    @Override
    protected void success(Entity e, int distance) {
        TakeableItem item = e.pickPocket();
        caster.addToInventory(item);
    }

    @Override
    protected void fail(Entity e, int distance) {
        e.enrage(caster);
    }

    @Override
    public void accept(Visitor v) {
        v.visitPickPocketCommand(this);
    }
}
