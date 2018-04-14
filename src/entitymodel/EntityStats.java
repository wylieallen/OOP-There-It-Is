package entitymodel;

import Skills.SkillType;

import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */
public class EntityStats {

    private Map <SkillType, Integer> skills;
    private int baseMoveSpeed;
    private int maxHealth;
    private int curHealth;
    private int maxMana;
    private int curMana;
    private int curXP;
    private int unspentSkillPoints;
    private int visibilityRadious;
    private int concealment;
    private int gold;

    public EntityStats(Map<SkillType, Integer> skills,
                       int baseMoveSpeed,
                       int maxHealth,
                       int curHealth,
                       int maxMana,
                       int curMana,
                       int curXP,
                       int unspentSkillPoints,
                       int visibilityRadious,
                       int concealment,
                       int gold)
    {
        this.skills = skills;
        this.baseMoveSpeed = baseMoveSpeed;
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxMana = maxMana;
        this.curMana = curMana;
        this.curXP = curXP;
        this.unspentSkillPoints = unspentSkillPoints;
        this.visibilityRadious = visibilityRadious;
        this.concealment = concealment;
        this.gold = gold;
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

    public int getVisibilityRadious() {
        return visibilityRadious;
    }

    public void setVisibilityRadious(int visibilityRadious) {
        this.visibilityRadious = visibilityRadious;
    }

    public int getConcealment() {
        return concealment;
    }

    public void setConcealment(int concealment) {
        this.concealment = concealment;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean containsSkill (SkillType s) {
        return skills.containsKey(s);
    }

    public int getSkillLevel (SkillType s) {
        return skills.getOrDefault(s, -1).intValue();
    }

}
