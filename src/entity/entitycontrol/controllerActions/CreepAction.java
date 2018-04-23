package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.BackStabInteraction;
import skills.SkillType;

public class CreepAction extends ControllerAction {

    //This will toggle the entity between creeping and not creeping

    private Entity controlledEntity;
    private boolean isCreeping;
    private BackStabInteraction backStab;
    private int cachedConcealmentDifference = 0;//will get set to the entities concealment when it starts creeping, so it can re-add
                                        //to the concealment when the entity stops creeping
    private int cachedSpeedDifference = 0;//will get set to .98 * (the entities speed) when it starts creeping, so it can re-add
                                  //to the speed when the entity stops creeping


    public CreepAction(Entity controlledEntity, boolean isCreeping, int cachedConcealmentDifference, int cachedSpeedDifference) {
        if(controlledEntity.getSkillLevel(SkillType.CREEP) == -1) {
            throw new java.lang.RuntimeException("CreepAction::Constructor : An attempt was made to create a CreepAction for an entity that does not have SkillType.CREEP");
        }
        else{
            this.controlledEntity = controlledEntity;
            this.isCreeping = isCreeping;
            this.cachedConcealmentDifference = cachedConcealmentDifference;
            this.cachedSpeedDifference = cachedSpeedDifference;
            this.backStab = new BackStabInteraction();
        }
    }

    @Override
    protected void execute() {
        System.out.println(isCreeping);
        if(isCreeping){
            isCreeping = false;
            controlledEntity.increaseConcealment(cachedConcealmentDifference);
            controlledEntity.increaseBaseMoveSpeed(cachedSpeedDifference);
            controlledEntity.addActorInteraction(backStab);
        }
        else if(!isCreeping){
            isCreeping = true;
            cachedConcealmentDifference = controlledEntity.getConcealment();
            controlledEntity.decreaseConcealment(cachedConcealmentDifference);
            cachedSpeedDifference = (int)(controlledEntity.getBaseMoveSpeed() * 0.98);
            controlledEntity.decreaseBaseMoveSpeed(cachedSpeedDifference);
            controlledEntity.removeActorInteraction(backStab);
        }
    }

    @Override
    public void accept(ControllerActionVisitor v)
    {
        v.visitCreepAction(this);
    }
}
