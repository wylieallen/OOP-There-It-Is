package entity.entitymodel;

import commands.TimedEffect;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.interactions.EntityInteraction;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import items.takeableitems.TakeableItem;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.tile.Direction;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */

public class Entity implements GameObject, MoveLegalityChecker {

    private final int levelUpIncreament = 100;

    private Direction facing;
    private Vector movementVector;
    private EntityStats stats;
    private List<ControllerAction> actions;//whenever an action gets added to this we need to notify the EntityController to add the same action
    private List<TimedEffect> effects;
    private List <EntityInteraction> actorInteractions;
    private List <EntityInteraction> acteeInteractions;
    private EntityController controller;
    private Inventory inventory;
    private boolean onMap;

    public Entity(Vector movementVector,
                  EntityStats stats,
                  List<ControllerAction> actions,
                  List<TimedEffect> effects,
                  List<EntityInteraction> actorInteractions,
                  List<EntityInteraction> acteeInteractions,
                  Inventory inventory,
                  boolean onMap)
    {
        this.movementVector = movementVector;
        this.stats = stats;
        this.actions = actions;
        this.effects = effects;
        this.actorInteractions = actorInteractions;
        this.acteeInteractions = acteeInteractions;
        this.inventory = inventory;
        this.onMap = onMap;
        this.facing = movementVector.getDirection();
    }

    public void setController(EntityController newController) {
        this.controller = newController;
    }

    public void update () {}

    public void update(Map<Coordinate, GameObjectContainer> mapOfContainers) {
        //TODO: add additional logic;
        controller.update(mapOfContainers);
    }

    public void setFacing(Direction newDirection) {
        facing = newDirection;
    }

    public void setMoving() {
        movementVector = new Vector(facing, getBaseMoveSpeed());
    }

    public void resetMovementVector() {
        movementVector = new Vector();
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

    public int getCurLevel () {
        return getCurXP() / levelUpIncreament;
    }

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

    public double getGold () { return stats.getGold(); }

    public void increaseGold (double amount) {
        stats.setGold(getGold() + amount);
    }

    public boolean decreaseGold (double amount) {
        if (amount > getGold()) {
            return false;
        } else {
            stats.setGold(getGold() - amount);
            return true;
        }
    }

    public boolean addToInventory (TakeableItem takeableItem) {
        return inventory.add(takeableItem);
    }

    public void removeFromInventory (TakeableItem takeableItem) {
        inventory.remove(takeableItem);
    }

    // TODO: remove and switch to pickpocket
    public TakeableItem getRandomItem () { return inventory.getRandomItem (); }

    public TakeableItem getItem (int index) { return inventory.select(index); }

    public TakeableItem pickPocket() {
        return inventory.pickPocket();
    }


    public List <EntityInteraction> interact (Entity actor) {

        ArrayList <EntityInteraction> union = new ArrayList<>();
        union.addAll(acteeInteractions);
        union.addAll(actor.actorInteractions);

        return union;
    }

    public boolean containsSkill (SkillType s) { return stats.containsSkill(s); }

    public int getSkillLevel (SkillType s) { return stats.getSkillLevel(s); }

    public void increaseSkillLevel (SkillType s, int amount) { stats.increaseSkillLevel (s, amount); }

    public boolean isOnMap () {
        return onMap;
    }

    public void setOnMap (boolean onMap) {
        this.onMap = onMap;
    }

    public void setMount (Vehicle mount) {
        setOnMap(false);
        // TODO: add dismount action.
        controller.notifyMount(mount);
    }

    @Override   // assumes e is player.
    public boolean canMoveHere (Entity mover) {
        // notifyInteraction will need to get the list of interactions by calling interact on its entity.
        mover.controller.notifyInteraction(mover, this);
        return false;
    }

    public boolean wantsToMove() {
        return !movementVector.isZeroVector();
    }

    public Vector getMovementVector() {
        return movementVector;
    }

    public Direction getMovementDirection() {
        return movementVector.getDirection();
    }

    public boolean isSearching() { return stats.getIsSearching(); }

    public void startSearching() { stats.startSearching(); }
    public void stopSearching() { stats.stopSearching(); }

}
