package skills;

public enum SkillType {

    BINDWOUNDS(0,0,0,0,0), BARGAIN(0,0,0,0,0), OBSERVATION(0,0,0,0,0),
    ONEHANDEDWEAPON(5,0,75,5,0), TWOHANDEDWEAPON(0,0,0,0,0), BRAWLING(0,0,0,0,0),
    ENCHANTMENT(0,0,0,0,0), BOON(0,0,0,0,0), BANE(0,0,0,0,0), STAFF(0,0,0,0,0),
    PICKPOCKET(0,0,0,0,0), DETECTANDREMOVETRAP(0,0,75,5,1), CREEP(0,0,0,0,0), RANGEDWEAPON(0,0,0,0,0),
    NULL(0,0,0,0,0);

    public final double levelEffectivenessModifier;
    public final double distanceEffectivenessModifier;
    public final int successRate;
    public final double levelSuccessModifier;
    public final double distanceSuccessModifier;

    SkillType(double levelEffectivenessModifier, double distanceEffectivenessModifier,
              int successRate, double levelSuccessModifier,
              double distanceSuccessModifier){
        this.levelEffectivenessModifier = levelEffectivenessModifier;
        this.distanceEffectivenessModifier = distanceEffectivenessModifier;
        this.successRate = successRate;
        this.levelSuccessModifier = levelSuccessModifier;
        this.distanceSuccessModifier = distanceSuccessModifier;
    }

    public boolean checkSuccess(int level, int distance) {
        int rand = (int)( (Math.random() * 100) + 1 );
        int adjustedSuccessRate = (int)( successRate
                                    + (level * levelSuccessModifier)
                                    - (distance * distanceSuccessModifier) );
        return rand < adjustedSuccessRate;
    }

    public int calculateModification(int baseEffectiveness, int distance, int level) {
        if(baseEffectiveness < 0) {
            return baseEffectiveness
                    - (int)( level * levelEffectivenessModifier)
                    + (int)( distance * distanceEffectivenessModifier);
        } else {
            return baseEffectiveness
                    + (int)( level * levelEffectivenessModifier)
                    - (int)( distance * distanceEffectivenessModifier);
        }

    }

}
