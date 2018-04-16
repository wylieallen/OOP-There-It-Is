package entity.entitymodel;

import skills.SkillType;

import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */
public class EntityStats {

    private final int defaultValue = -1;
    private final int maxSkillLevel = 100;

    private Map <SkillType, Integer> skills;
    private int baseMoveSpeed;
    private int maxHealth;
    private int curHealth;
    private int maxMana;
    private int curMana;
    private int curXP;
    private int unspentSkillPoints;
    private int visibilityRadius; //how far out you can see
    private int concealment; //the max distance from which enemies can see you
    private double gold;
    private boolean isConfused;
    private boolean isSearching;

    public EntityStats(Map<SkillType, Integer> skills,
                       int baseMoveSpeed,
                       int maxHealth,
                       int curHealth,
                       int maxMana,
                       int curMana,
                       int curXP,
                       int unspentSkillPoints,
                       int visibilityRadius,
                       int concealment,
                       double gold,
                       boolean isSearching,
                       boolean isConfused)
    {
        this.skills = skills;
        this.baseMoveSpeed = baseMoveSpeed;
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxMana = maxMana;
        this.curMana = curMana;
        this.curXP = curXP;
        this.unspentSkillPoints = unspentSkillPoints;
        this.visibilityRadius = visibilityRadius;
        this.concealment = concealment;
        this.gold = gold;
        this.isConfused = isConfused;
        this.isSearching = isSearching;
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
}
