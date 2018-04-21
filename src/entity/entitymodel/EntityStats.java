package entity.entitymodel;

import maps.movelegalitychecker.Terrain;
import maps.world.Game;
import savingloading.Visitable;
import savingloading.Visitor;
import skills.SkillType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by dontf on 4/13/2018.
 */
public class EntityStats implements Visitable {

    private static final Set<Terrain> defaultCompatibleTerrains = new HashSet<>();
    static {
        defaultCompatibleTerrains.add(Terrain.GRASS);
    }

    private final int defaultValue = -1;
    private final int maxSkillLevel = 100;

    private Map <SkillType, Integer> skills;
    private int baseMoveSpeed;
    private int maxHealth;
    private int curHealth;
    private int maxMana;
    private int curMana;
    private int manaRegenRate;
    private int curXP;
    private int unspentSkillPoints;
    private int visibilityRadius; //how far out you can see
    private int concealment; //the max distance from which enemies can see you
    private double gold;
    private boolean isConfused;
    private boolean isSearching;
    private Set<Terrain> compatibleTerrains;
    private long lastAttackTime;
    private long lastMoveTime;

    public EntityStats()
    {
        this(new HashMap<>(), 1, 10, 10, 10, 10, 1,
                10, 0, 1, 1, 100, false, false, defaultCompatibleTerrains);
    }

    public EntityStats(Map<SkillType, Integer> skills,
                       int baseMoveSpeed,
                       int maxHealth,
                       int curHealth,
                       int maxMana,
                       int curMana,
                       int manaRegenRate,
                       int curXP,
                       int unspentSkillPoints,
                       int visibilityRadius,
                       int concealment,
                       double gold,
                       boolean isSearching,
                       boolean isConfused)
    {
        this(skills, baseMoveSpeed, maxHealth, curHealth, maxMana, curMana, manaRegenRate,
                curXP, unspentSkillPoints, visibilityRadius, concealment, gold, isSearching, isConfused, defaultCompatibleTerrains);
    }

    public EntityStats(Map<SkillType, Integer> skills,
                       int baseMoveSpeed,
                       int maxHealth,
                       int curHealth,
                       int maxMana,
                       int curMana,
                       int manaRegenRate,
                       int curXP,
                       int unspentSkillPoints,
                       int visibilityRadius,
                       int concealment,
                       double gold,
                       boolean isSearching,
                       boolean isConfused,
                       Set<Terrain> compatibleTerrains)
    {
        this.skills = skills;
        this.baseMoveSpeed = baseMoveSpeed;
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxMana = maxMana;
        this.curMana = curMana;
        this.manaRegenRate = manaRegenRate;
        this.curXP = curXP;
        this.unspentSkillPoints = unspentSkillPoints;
        this.visibilityRadius = visibilityRadius;
        this.concealment = concealment;
        this.gold = gold;
        this.isConfused = isConfused;
        this.isSearching = isSearching;
        this.compatibleTerrains = compatibleTerrains;
        this.lastAttackTime = 0;
        this.lastMoveTime = 0;
    }

    public int getBaseMoveSpeed() {
        return baseMoveSpeed;
    }

    public void setBaseMoveSpeed(int baseMoveSpeed) {
        this.baseMoveSpeed = baseMoveSpeed;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getCurMana() {
        return curMana;
    }

    public void setCurMana(int curMana) {
        this.curMana = curMana;
    }

    public int getCurXP() {
        return curXP;
    }

    public void setCurXP(int curXP) {
        this.curXP = curXP;
    }

    public int getUnspentSkillPoints() {
        return unspentSkillPoints;
    }

    public void setUnspentSkillPoints(int unspentSkillPoints) {
        this.unspentSkillPoints = unspentSkillPoints;
    }

    public int getVisibilityRadius() {
        return visibilityRadius;
    }

    public void setVisibilityRadius(int visibilityRadius) {
        this.visibilityRadius = visibilityRadius;
    }

    public int getConcealment() {
        return concealment;
    }

    public void setConcealment(int concealment) {
        this.concealment = concealment;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public boolean containsSkill (SkillType s) {
        return skills.containsKey(s);
    }

    public int getSkillLevel (SkillType s) {
        return skills.getOrDefault(s, defaultValue);
    }

    public void increaseSkillLevel (SkillType s, int amount) {
        int curLevel = skills.getOrDefault(s, defaultValue);

        if (curLevel != defaultValue) {
            curLevel = Math.min(maxSkillLevel, curLevel + amount);
            skills.replace(s, curLevel);
        }

    }

    public boolean getIsSearching() { return isSearching; }


    public void startSearching() {
        if(containsSkill(SkillType.DETECTANDREMOVETRAP)) {
            isSearching = true;
        }
    }

    public void stopSearching() {
        if(containsSkill(SkillType.DETECTANDREMOVETRAP)) {
            isSearching = false;
        }
    }

    public boolean isConfused() { return isConfused; }

    public void makeConfused() { isConfused = true; }

    public void makeUnconfused() { isConfused = false; }

    public void regenMana() {
        setCurMana(Math.min(maxMana, curMana + manaRegenRate));
    }

    public int getManaRegenRate() { return manaRegenRate; }

    public void setManaRegenRate(int newRate) { manaRegenRate = newRate; }

    public boolean isTerrainCompatible(Terrain t) {
        return compatibleTerrains.contains(t);
    }

    public Set <Terrain> getCompatibleTerrains () { return compatibleTerrains; }

    public Map<SkillType, Integer> getSkills() {
        return skills;
    }

    @Override
    public void accept(Visitor v) {
        v.visitEntityStats(this);
    }

    public void addCompatibleTerrain(Terrain t) { compatibleTerrains.add(t); }

    public boolean tryToAttack(long attackSpeed) {
        if(Game.getCurrentTime() - lastAttackTime > attackSpeed) {
            lastAttackTime = Game.getCurrentTime();
            return true;
        }
        return false;
    }

    public boolean tryToMove(long moveSpeed) {
        if(Game.getCurrentTime() - lastMoveTime > 1000 / moveSpeed) {
            lastMoveTime = Game.getCurrentTime();
            return true;
        }
        return false;
    }
}
