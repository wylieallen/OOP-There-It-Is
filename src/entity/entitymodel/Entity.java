package entity.entitymodel;

import commands.TimedEffect;
import entity.entitycontrol.ControllerAction;
import entity.entitycontrol.EntityController;
import entity.entitymodel.interactions.EntityInteraction;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import items.takeableitems.TakeableItem;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */
public class Entity implements GameObject
{

    private final int levelUpIncreament = 100;

    private Vector vector;
    private EntityStats stats;
    private List <ControllerAction> actions;
    private List <TimedEffect> effects;
    private List <EntityInteraction> actorInteractions;
    private List <EntityInteraction> acteeInteractions;
    private EntityController controller;
    private Inventory inventory;
    private boolean onMap;

    public Entity(Vector vector,
                  EntityStats stats,
                  List<ControllerAction> actions,
                  List<TimedEffect> effects,
                  List<EntityInteraction> actorInteractions,
                  List<EntityInteraction> acteeInteractions,
                  EntityController controller,
                  Inventory inventory,
                  boolean onMap)
    {
        this.vector = vector;
        this.stats = stats;
        this.actions = actions;
        this.effects = effects;
        this.actorInteractions = actorInteractions;
        this.acteeInteractions = acteeInteractions;
        this.controller = controller;
        this.inventory = inventory;
        this.onMap = onMap;
    }

    public void update () {}

    public void update (Map<Coordinate, GameObjectContainer> mapOfContainers) {
        //TODO: add additional logic;
        controller.update(mapOfContainers);
    }

    public boolean expired() {
        return stats.getCurHealth() <= 0;
    }

    public int getMaxHealth () {
        return stats.getMaxHealth();
    }

    public void healEntity (int amount) {
        stats.setCurHealth(Math.min(getMaxHealth(), getCurrHealth() + amount));
    }

    //true results in killing the entity, can be used to give skill points to attacking entity;
    public boolean hurtEntity (int amount) {
        stats.setCurHealth(Math.max(0, getCurrHealth() - amount));
        return getCurrHealth() <= 0;
    }

    public int getCurrHealth () {
        return stats.getCurHealth();
    }

    public int getMaxMana () { return stats.getMaxMana(); }

    public void addMana (int amount) {
        stats.setCurMana(Math.min(getMaxMana(), getCurMana() + amount));
    }

    public boolean useMana (int amount) {
        if (getCurMana() - amount < 0)
            return false;

        stats.setCurMana(getCurMana() - amount);
        return true;
    }

    public int getCurMana () {
        return stats.getCurMana();
    }

    public int getBaseMoveSpeed () {
        return stats.getBaseMoveSpeed();
    }

    public void increaseBaseMoveSpeed (int amount) {
        stats.setBaseMoveSpeed(getBaseMoveSpeed() + amount);
    }

    public void decreaseBaseMoveSpeed (int amount) {
        stats.setBaseMoveSpeed(Math.max (0, getBaseMoveSpeed() - amount));
    }

    public int getCurXP () { return stats.getCurXP(); }

    public void increaseXP (int amount) {

        boolean leveled = (getCurXP() % levelUpIncreament) + amount >= levelUpIncreament;
        stats.setCurXP(getCurXP() + amount);

        if (leveled)
            controller.notifyLevelUp(this);

    }

    public int getUnusedSkillPoints () { return stats.getUnspentSkillPoints(); }

    public void increaseSkillPoints (int amount) {
        stats.setUnspentSkillPoints(getUnusedSkillPoints() + amount);
    }

    public void decreaseSkillPoints (int amount) {
        stats.setUnspentSkillPoints(Math.max(0, getUnusedSkillPoints() - amount));
    }

    public int getVisibilityRadious () { return stats.getVisibilityRadious(); }

    public void increaseVisibilityRadious (int amount) {
        stats.setVisibilityRadious(getVisibilityRadious() + amount);
    }

    public void decreaseVisibilityRadious (int amount) {
        stats.setVisibilityRadious(Math.max(0, getVisibilityRadious() - amount));
    }

    public int getConcealment () { return stats.getConcealment(); }

    public void increaseConcealment (int amount) {
        stats.setConcealment(getConcealment() + amount);
    }

    public void decreaseConcealment (int amount) {
        stats.setConcealment(Math.max(0, getConcealment() - amount));
    }

    public int getGold () { return stats.getGold(); }

    public void increaseGold (int amount) {
        stats.setGold(getGold() + amount);
    }

    public void decreaseGold (int amount) {
        stats.setGold(Math.max(0, getGold() - amount));
    }

    public boolean addToInventory (TakeableItem takeableItem) {
        return inventory.add(takeableItem);
    }

    public void removeFromInventory (TakeableItem takeableItem) {
        inventory.remove(takeableItem);
    }

    public List <EntityInteraction> interact (Entity actor) {

        ArrayList <EntityInteraction> union = new ArrayList<EntityInteraction>();
        union.addAll(acteeInteractions);
        union.addAll(actor.actorInteractions);

        return union;
    }

    public boolean isOnMap () {
        return onMap;
    }

    public void setOnMap (boolean onMap) {
        this.onMap = onMap;
    }

}
