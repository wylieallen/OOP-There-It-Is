package items.takeableitems;

import commands.Command;
import commands.skillcommands.SkillCommand;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import maps.Influence.StaticInfluenceArea;
import savingloading.Visitor;
import maps.Influence.InfluenceType;
import maps.Influence.expandingInfluenceArea;
import skills.SkillType;
import spawning.SpawnObservable;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class WeaponItem extends TakeableItem implements SpawnObservable {


    private long attackSpeed;
    private SkillType requiredSkill;
    private List<SpawnObserver> spawnObservers;
    private int maxRadius;
    private long expansionInterval;
    private long updateInterval;
    private long duration;
    private InfluenceType influenceType;
    private SkillCommand command;
    private boolean makesExpandingArea;



    public WeaponItem(String name, boolean onMap, long attackSpeed,
                      SkillType requiredSkill, int maxRadius, long expansionInterval,
                      long updateInterval, long duration, InfluenceType influenceType, SkillCommand command, boolean makesExpandingArea) {
        super(name, onMap);
        this.attackSpeed = attackSpeed;
        this.requiredSkill = requiredSkill;
        spawnObservers = new ArrayList<>();
        this.maxRadius = maxRadius;
        this.expansionInterval = expansionInterval;
        this.updateInterval = updateInterval;
        this.duration = duration;
        this.influenceType = influenceType;
        this.command = command;
        this.makesExpandingArea = makesExpandingArea;

    }

    @Override
    public void activate(Equipment e) {
        e.add(this);
    }

    public void attack(Entity attacker, Coordinate location) {
        if(!attacker.containsSkill(requiredSkill))
            return;

        int skillLevel = attacker.getSkillLevel(requiredSkill);
        System.out.println("attack with skill level " + skillLevel);
        command.setLevel(skillLevel);
        boolean canAttack = attacker.tryToAttack(attackSpeed);
        if(canAttack) {
            ArrayList<GameObject> whitelist = new ArrayList<>();
            if(influenceType != InfluenceType.SELFINFLUENCE)
                whitelist.add(attacker);
            InfluenceArea ia;
            if(makesExpandingArea) {
                ia = new expandingInfluenceArea(influenceType, attacker.getMovementDirection(),
                        maxRadius, location, whitelist, updateInterval, expansionInterval, command);
            }
            else{
                ia = new StaticInfluenceArea(influenceType,attacker.getMovementDirection(),
                        maxRadius,location,whitelist,updateInterval,duration,command);
            }
            notifyAllOfSpawn(ia);
        }
    }

    @Override
    public void notifyAllOfSpawn (InfluenceArea IA){
        for(SpawnObserver so: spawnObservers) {
            so.notifySpawn(IA, this);
        }
    }

    public void registerObserver (SpawnObserver SO){
        spawnObservers.add(SO);
    }

    public void deregisterObserver (SpawnObserver SO) {
        spawnObservers.remove(SO);
    }

    public void setSpawnObservers (List<SpawnObserver> newObservers) {
        spawnObservers.clear();
        spawnObservers.addAll(newObservers);
    }

    public SkillCommand getCommand() {
        return command;
    }


    public long getAttackSpeed(){
        return attackSpeed;
    }

    public SkillType getRequiredSkill() {
        return requiredSkill;
    }

    public int getMaxRadius(){
        return maxRadius;
    }

    public long getExpansionInterval(){
        return expansionInterval;
    }

    public long getUpdateInterval(){
        return updateInterval;
    }

    public InfluenceType getInfluenceType() {
        return influenceType;
    }

    @Override
    public void accept(Visitor v) {
        v.visitWeaponItem(this);
    }
}
