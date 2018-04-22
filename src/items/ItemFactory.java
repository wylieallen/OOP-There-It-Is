package items;

import commands.Command;
import commands.ModifyHealthCommand;
import commands.TransitionCommand;
import commands.skillcommands.SkillCommand;
import items.takeableitems.ConsumableItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.Influence.InfluenceType;
import maps.world.Game;
import maps.world.World;
import skills.SkillType;
import utilities.Coordinate;

public class ItemFactory {
    public static WeaponItem makeAxe(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, skillLevel, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Axe", false, 0, 5000, SkillType.TWOHANDEDWEAPON, 1, 1, 1, InfluenceType.CIRCULARINFLUENCE, skill);
        //must add overworld as observer
        w.registerObserver(world);

        return w;
    }

    public static InteractiveItem makeTeleporter(World targetWorld, Coordinate targetCoordinate, Game game){
        TransitionCommand command = new TransitionCommand(targetWorld, targetCoordinate, game);
        return new InteractiveItem("Teleporter", command);
    }

    public static ConsumableItem makeHealthPotion(int healthIncrease){
        ModifyHealthCommand command = new ModifyHealthCommand(healthIncrease);
        return new ConsumableItem("HealthPotion", true, command);
    }

    public static OneshotItem makeOneShotHealth(int healthIncrease){
        ModifyHealthCommand command = new ModifyHealthCommand(healthIncrease);
        return new OneshotItem("HealthPotion", command, false);
    }


}
