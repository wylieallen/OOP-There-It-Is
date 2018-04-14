package items.takeableitems;

import entity.entitymodel.Equipment;
import skills.SkillType;
import commands.Command;

public class WeaponItem extends TakeableItem {

    private Command command;
    private int damage;
    private int attackSpeed;
    private SkillType requiredSkill;

    public WeaponItem(String name, Command command, int damage, int attackSpeed, SkillType requiredSkill) {
        super(name);
        this.command = command;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.requiredSkill = requiredSkill;
    }

    @Override
    public void activate(Equipment e) {

    }

    public void attack() {

    }
}
