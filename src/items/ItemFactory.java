package items;

import commands.*;
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

    //Smasher Weapons

    //Brawling Weapons
    public static WeaponItem makeBadGlove(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BRAWLING, skillLevel, -5, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Glove", false, 0, 1000, SkillType.BRAWLING, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGlove(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BRAWLING, skillLevel, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Glove", false, 0, 1000, SkillType.BRAWLING, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodGlove(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BRAWLING, skillLevel, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Glove", false, 0, 1000, SkillType.BRAWLING, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //One-handed Weapons
    public static WeaponItem makeBadSword(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.ONEHANDEDWEAPON, skillLevel, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Sword", false, 0, 1000, SkillType.ONEHANDEDWEAPON, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeSword(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.ONEHANDEDWEAPON, skillLevel, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Sword", false, 0, 1000, SkillType.ONEHANDEDWEAPON, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodSword(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.ONEHANDEDWEAPON, skillLevel, -25, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Sword", false, 0, 1000, SkillType.ONEHANDEDWEAPON, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }


    //Two-Handed Weapons
    public static WeaponItem makeBadAxe(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, skillLevel, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Axe", false, 0, 5000, SkillType.TWOHANDEDWEAPON, 1, 1, 1, InfluenceType.ANGULARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeAxe(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, skillLevel, -25, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Axe", false, 0, 5000, SkillType.TWOHANDEDWEAPON, 1, 1, 1, InfluenceType.CIRCULARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodAxe(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, skillLevel, -30, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Axe", false, 0, 5000, SkillType.TWOHANDEDWEAPON, 2, 1, 1, InfluenceType.CIRCULARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Summoner Weapons

    //Enchantment Weapons
    public static WeaponItem makeConfuseGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.ENCHANTMENT, skillLevel, 5000, new ConfuseCommand(), null);
        WeaponItem w = new WeaponItem ("Confuse Gadget", false, 0, 5000, SkillType.ENCHANTMENT, 3, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeParalyzeGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.ENCHANTMENT, skillLevel, 5000, new ParalyzeCommand(), null);
        WeaponItem w = new WeaponItem ("Paralyze Gadget", false, 0, 5000, SkillType.ENCHANTMENT, 3, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makePacifyGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.ENCHANTMENT, skillLevel, 0, new MakeFriendlyCommand(), null);
        WeaponItem w = new WeaponItem ("Pacify Gadget", false, 0, 5000, SkillType.ENCHANTMENT, 3, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Boon Weapons
    public static WeaponItem makeHealGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BOON, skillLevel, 10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Heal Gadget", false, 0, 5000, SkillType.BOON, 1, 1, 1, InfluenceType.SELFINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeStaminaRegenGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BOON, skillLevel, 5000, new ModifyStaminaRegenCommand(2), null);
        WeaponItem w = new WeaponItem ("Faster Stamina Regen Gadget", false, 0, 5000, SkillType.BOON, 1, 1, 1, InfluenceType.SELFINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeStrongHealGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BOON, skillLevel, 100, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Strong Heal Gadget", false, 0, 5000, SkillType.BOON, 1, 1, 1, InfluenceType.SELFINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Bane Weapons
    public static WeaponItem makeLinearBaneGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, skillLevel, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Linear Damage Gadget", false, 0, 5000, SkillType.BANE, 5, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeAngularBaneGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, skillLevel, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Angular Damage Gadget", false, 0, 5000, SkillType.BANE, 5, 1, 1, InfluenceType.ANGULARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeCircularBaneGadget(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, skillLevel, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Circular Damage Gadget", false, 0, 5000, SkillType.BANE, 5, 1, 1, InfluenceType.CIRCULARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Staff Weapons
    public static WeaponItem makeBadStaff(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, skillLevel, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Staff", false, 0, 5000, SkillType.BANE, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeStaff(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, skillLevel, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Staff", false, 0, 5000, SkillType.BANE, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodStaff(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, skillLevel, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Staff", false, 0, 5000, SkillType.BANE, 1, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Sneak Weapons

    //Ranged Weapons
    public static WeaponItem makeBadGun(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.RANGEDWEAPON, skillLevel, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Gun", false, 0, 5000, SkillType.RANGEDWEAPON, 5, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGun(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.RANGEDWEAPON, skillLevel, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Gun", false, 0, 5000, SkillType.RANGEDWEAPON, 5, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodGun(World world, int skillLevel) {
        SkillCommand skill = new SkillCommand(SkillType.RANGEDWEAPON, skillLevel, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Gun", false, 0, 5000, SkillType.RANGEDWEAPON, 5, 1, 1, InfluenceType.LINEARINFLUENCE, skill);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }



    //Interactive Items
    public static InteractiveItem makeTeleporter(World targetWorld, Coordinate targetCoordinate, Game game){
        TransitionCommand command = new TransitionCommand(targetWorld, targetCoordinate, game);
        return new InteractiveItem("Teleporter", command);
    }

    //Consumable Items
    public static ConsumableItem makeHealthPotion(int healthIncrease){
        ModifyHealthCommand command = new ModifyHealthCommand(healthIncrease);
        return new ConsumableItem("HealthPotion", true, command);
    }

    //Oneshot Items
    public static OneshotItem makeOneShotHealth(int healthIncrease){
        ModifyHealthCommand command = new ModifyHealthCommand(healthIncrease);
        return new OneshotItem("HealthPotion", command, false);
    }


}
