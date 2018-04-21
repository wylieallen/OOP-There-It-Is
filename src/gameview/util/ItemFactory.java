package gameview.util;

import commands.ModifyHealthCommand;
import commands.skillcommands.SkillCommand;
import items.takeableitems.WeaponItem;
import maps.Influence.InfluenceType;
import maps.world.World;
import skills.SkillType;

public class ItemFactory {
    public static WeaponItem makeAxe(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, skillLevel, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Axe", false, 0, 5000, SkillType.TWOHANDEDWEAPON, 1, 1, 1, InfluenceType.CIRCULARINFLUENCE, skill);
        //must add overworld as observer
        w.registerObserver(world);

        return w;
    }
}
