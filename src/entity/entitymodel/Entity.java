package entity.entitymodel;

import commands.TimedEffect;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitycontrol.controllerActions.DirectionalMoveAction;
import entity.entitymodel.interactions.EntityInteraction;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import items.takeableitems.TakeableItem;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitable;
import savingloading.Visitor;
import skills.SkillType;
import spawning.SpawnObserver;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

/**
 * Created by dontf on 4/13/2018.
 */
public class Entity implements GameObject, MoveLegalityChecker, Visitable
{

    private final int levelUpIncreament = 100;

    private Direction facing;
    private Vector movementVector;
    private EntityStats stats;
    private List<ControllerAction> actions = new ArrayList<>();//whenever an action gets added to this we need to notify the EntityController to add the same action
    private List<TimedEffect> effects;
    private List <EntityInteraction> actorInteractions;
    private List <EntityInteraction> acteeInteractions;
    private EntityController controller;
    private Inventory inventory;
    private boolean onMap;
    private String name = "default";

    private MovementObserver movementObserver = () -> {};

    public Entity()
    {
        this(new Vector(), new EntityStats(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new Inventory(), true);
        for(Direction d : Direction.values())
        {
            if(d != Direction.NULL)
                actions.add(new DirectionalMoveAction(this, d));
        }
    }

    public Entity(Vector movementVector,
                  EntityStats stats,
                  List<TimedEffect> effects,
                  List<EntityInteraction> actorInteractions,
                  //This will be set by the AI instead
                  //List<EntityInteraction> acteeInteractions,
                  Inventory inventory,
                  boolean onMap, String name)
    {
        this.movementVector = movementVector;
        this.stats = stats;
        this.effects = effects;
        this.actorInteractions = actorInteractions;
        this.actions = new ArrayList<>();
        //prevents errors until the AI sets the interactions
        this.acteeInteractions = new ArrayList<>();
        this.inventory = inventory;
        this.onMap = onMap;
        this.facing = movementVector.getDirection();
        this.name = name;
    }

    // I kept this constructor for testing, but actions will be set later now
    public Entity(Vector movementVector,
                  EntityStats stats,
                  List<ControllerAction> actions,
                  List<TimedEffect> effects,
                  List<EntityInteraction> actorInteractions,
                  //This will be set by the AI instead
                  //List<EntityInteraction> acteeInteractions,
                  Inventory inventory,
                  boolean onMap)
    {
        this.movementVector = movementVector;
        this.stats = stats;
        this.effects = effects;
        this.actions = actions;
        this.actorInteractions = actorInteractions;
        //prevents errors until the AI sets the interactions
        this.acteeInteractions = new ArrayList<>();
        this.inventory = inventory;
        this.onMap = onMap;
        this.facing = movementVector.getDirection();
    }

    public void setMovementObserver(MovementObserver o) { this.movementObserver = o; }

    public void notifyMovement() { movementObserver.notifyMovement(); }

    public void setControllerActions(List<ControllerAction> actions){
        this.actions = actions;
    }

    public void setController(EntityController newController) {
        this.controller = newController;
    }

    public Collection<ControllerAction> getControllerActions() { return actions; }

    public void update () {
        updateStats();
        for(TimedEffect effect: effects) {
            effect.decrementTimeRemaining();
            effect.triggerIfExpired(this);
        }
        effects.removeIf(TimedEffect::isExpired);
    }

    public void update(Map<Coordinate, Tile> map) {
        update();
        controller.updateMap (map);
    }


    private void updateStats() {
        stats.regenMana();
    }

    public void setFacing(Direction newDirection) {
        if(isConfused()) {
            facing = Direction.getRandom();
        } else {
            facing = newDirection;
        }
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
        System.out.println("I got Hit");
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

    public double getBaseMoveSpeed () {
        return stats.getBaseMoveSpeed();
    }

    public void increaseBaseMoveSpeed (double amount) {
        stats.setBaseMoveSpeed(getBaseMoveSpeed() + amount);
    }

    public void decreaseBaseMoveSpeed (double amount) {
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

    public int getVisibilityRadius() { return stats.getVisibilityRadius(); }

    public void increaseVisibilityRadious (int amount) {
        stats.setVisibilityRadius(getVisibilityRadius() + amount);
    }

    public void decreaseVisibilityRadious (int amount) {
        stats.setVisibilityRadius(Math.max(0, getVisibilityRadius() - amount));
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

    public Direction getFacing() { return facing; }

    public boolean isSearching() { return stats.getIsSearching(); }

    public void startSearching() { stats.startSearching(); }

    public void stopSearching() { stats.stopSearching(); }

    public void makeConfused() { stats.makeConfused(); }

    public void makeUnconfused() { stats.makeUnconfused(); }

    public void addTimedEffect(TimedEffect effect) {
        effects.add(effect);
        effect.trigger(this);
    }

    public void enrage(Entity target) {
        controller.enrage(target);
    }

    public void pacify() {
        controller.pacify();
    }

    public void setActeeInteractions(List<EntityInteraction> newInteractions) {
        this.acteeInteractions = newInteractions;
    }

    public boolean isConfused() { return stats.isConfused(); }

    public int getManaRegenRate() { return stats.getManaRegenRate(); }

    public void setManaRegenRate(int newRate) { stats.setManaRegenRate(newRate); }

    public void setCurMana(int newMana) { stats.setCurMana(newMana); }

    public void addItem(TakeableItem item) {
        inventory.add(item);
    }

    public Set<Terrain> getCompatibleTerrains () {
        return stats.getCompatibleTerrains ();
    }

    public boolean isTerrainCompatible(Terrain t) { return stats.isTerrainCompatible(t); }

    public EntityController getController() {
        return controller;
    }

    public EntityStats getStats() {
        return stats;
    }

    public List<EntityInteraction> getActeeInteractions() {
        return acteeInteractions;
    }

    public List<EntityInteraction> getActorInteractions() {
        return actorInteractions;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public String getName(){
        return name;
    }

    @Override
    public void accept(Visitor v) {
        v.visitEntity(this);
    }

    public boolean tryToAttack(long attackSpeed) {
        return stats.tryToAttack(attackSpeed);
    }

    public boolean tryToMove(double moveSpeed) {
        return stats.tryToMove(moveSpeed);
    }

    public boolean has(GameObject o) {
        if(controller.has(o)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateSpawnObservers(SpawnObserver oldObserver, SpawnObserver newObserver) {
        controller.updateSpawnObservers(oldObserver, newObserver);
    }

    public void addCompatibleTerrain(Terrain t) {
        stats.addCompatibleTerrain(t);
    }

}
