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
    public static WeaponItem makeBadGlove(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BRAWLING, 0, -5, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Glove", onMap, 1000, SkillType.BRAWLING, 5, 1, 1, 1, 300,InfluenceType.LINEARINFLUENCE, skill,false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGlove(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BRAWLING, 0, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Glove", onMap, 1000, SkillType.BRAWLING, 5, 1, 1, 1, 300,InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodGlove(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BRAWLING, 0, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Glove", onMap, 1000, SkillType.BRAWLING, 5, 1, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //One-handed Weapons
    public static WeaponItem makeBadSword(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.ONEHANDEDWEAPON, 0, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Sword", onMap, 1000, SkillType.ONEHANDEDWEAPON, 10, 1, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeSword(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.ONEHANDEDWEAPON, 0, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Sword", onMap, 1000, SkillType.ONEHANDEDWEAPON, 10, 1, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodSword(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.ONEHANDEDWEAPON, 0, -25, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Sword", onMap, 1000, SkillType.ONEHANDEDWEAPON, 10, 1, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }


    //Two-Handed Weapons
    public static WeaponItem makeBadAxe(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, 0, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Axe", onMap, 5000, SkillType.TWOHANDEDWEAPON, 20, 1, 1, 1, 300, InfluenceType.ANGULARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeAxe(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, 0, -25, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Axe", onMap, 5000, SkillType.TWOHANDEDWEAPON, 20, 1, 1, 1, 300, InfluenceType.CIRCULARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodAxe(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, 0, -30, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Axe", onMap, 5000, SkillType.TWOHANDEDWEAPON, 20, 2, 1, 1, 300, InfluenceType.CIRCULARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Summoner Weapons

    //Enchantment Weapons
    public static WeaponItem makeConfuseGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.ENCHANTMENT, 0, 5000, new ConfuseCommand(), null);
        WeaponItem w = new WeaponItem ("Confuse Gadget", onMap, 5000, SkillType.ENCHANTMENT, 10, 3, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeParalyzeGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.ENCHANTMENT, 0, 5000, new ParalyzeCommand(), null);
        WeaponItem w = new WeaponItem ("Paralyze Gadget", onMap, 5000, SkillType.ENCHANTMENT, 10, 3, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makePacifyGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.ENCHANTMENT, 0, 0, new MakeFriendlyCommand(), null);
        WeaponItem w = new WeaponItem ("Pacify Gadget", onMap, 5000, SkillType.ENCHANTMENT, 10, 3, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill,false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Boon Weapons
    public static WeaponItem makeHealGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BOON, 0, 10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Heal Gadget", onMap, 5000, SkillType.BOON, 1, 20, 1, 1, 300, InfluenceType.SELFINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeStaminaRegenGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BOON, 0, 5000, new ModifyStaminaRegenCommand(2), null);
        WeaponItem w = new WeaponItem ("Faster Stamina Regen Gadget", onMap, 5000, SkillType.BOON, 20, 1, 1, 1, 300, InfluenceType.SELFINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeStrongHealGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BOON, 0, 100, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Strong Heal Gadget", onMap, 5000, SkillType.BOON, 20, 1, 1, 1, 300, InfluenceType.SELFINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Bane Weapons
    public static WeaponItem makeLinearBaneGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, 0, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Linear Damage Gadget", onMap, 5000, SkillType.BANE, 15,  5, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, true);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeAngularBaneGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, 0, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Angular Damage Gadget", onMap, 5000, SkillType.BANE, 15,  5, 1, 1, 300, InfluenceType.ANGULARINFLUENCE, skill, true);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeCircularBaneGadget(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.BANE, 0, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Circular Damage Gadget", onMap, 5000, SkillType.BANE, 15,  5, 1, 1, 300, InfluenceType.CIRCULARINFLUENCE, skill, true);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Staff Weapons
    public static WeaponItem makeBadStaff(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.STAFF, 0, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Staff", onMap, 5000, SkillType.STAFF, 0,  1, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeStaff(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.STAFF, 0, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Staff", onMap, 5000, SkillType.STAFF, 0,  1, 1, 1, 300,InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodStaff(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.STAFF, 0, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Staff", onMap, 5000, SkillType.STAFF, 0,  1, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    //Sneak Weapons

    //Ranged Weapons
    public static WeaponItem makeBadGun(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.RANGEDWEAPON, 0, -10, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bad Gun", onMap, 5000, SkillType.RANGEDWEAPON, 15, 5, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, true);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGun(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.RANGEDWEAPON, 0, -15, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Gun", onMap, 5000, SkillType.RANGEDWEAPON, 15, 5, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, true);
        //must add world as observer
        w.registerObserver(world);

        return w;
    }

    public static WeaponItem makeGoodGun(World world, boolean onMap) {
        SkillCommand skill = new SkillCommand(SkillType.RANGEDWEAPON, 0, -20, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Good Gun", onMap, 5000, SkillType.RANGEDWEAPON, 15, 5, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, true);
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
