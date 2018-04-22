package skills;

public enum SkillType {

    BINDWOUNDS(1,1,50,1,1), BARGAIN(1,0,100,0,0), OBSERVATION(1,1,100,0,0),
    ONEHANDEDWEAPON(1,1,75,1,1), TWOHANDEDWEAPON(1,1,65,1,1), BRAWLING(1,1,85,1,1),
    ENCHANTMENT(1,1,85,1,1), BOON(1,1,85,1,1), BANE(1,1,85,1,1), STAFF(1,1,85,1,1),
    PICKPOCKET(1,1,85,1,1), DETECTANDREMOVETRAP(0,0,75,5,1), CREEP(1,1,100,1,1), RANGEDWEAPON(1,1,85,1,1),
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
//        System.out.println("successrate + (level * LSM) - (distance * DSM)");
//        System.out.println("successrate = " + successRate);
//        System.out.println("level = " + level + " LSM = " + levelSuccessModifier);
//        System.out.println("distance = " + distance + " DSM = " + distanceSuccessModifier);
//        System.out.println("Rolled " + rand + ", adjustedSuccessRate " + adjustedSuccessRate);
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
