package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import skills.SkillType;

public class SearchAction extends ControllerAction {

    //This will toggle the entity between creeping and not creeping

    private Entity controlledEntity;
    private boolean isSearching;
    private int cachedSpeedDifference = 0;//will get set to .98 * (the entities speed) when it starts creeping, so it can re-add
    //to the speed when the entity stops creeping


    public SearchAction(Entity controlledEntity, boolean isSearching, int cachedSpeedDifference) {
        if(controlledEntity.getSkillLevel(SkillType.DETECTANDREMOVETRAP) == -1) {
            throw new java.lang.RuntimeException("CreepAction::Constructor : An attempt was made to create a CreepAction for an entity that does not have SkillType.CREEP");
        }
        else{
            this.controlledEntity = controlledEntity;
            this.isSearching = isSearching;
            this.cachedSpeedDifference = cachedSpeedDifference;
        }
    }

    @Override
    protected void execute() {
        if(isSearching){
            controlledEntity.increaseBaseMoveSpeed(cachedSpeedDifference);
            controlledEntity.stopSearching();
        }
        else {
            cachedSpeedDifference = (int)(controlledEntity.getBaseMoveSpeed() * 0.98);
            controlledEntity.decreaseBaseMoveSpeed(cachedSpeedDifference);
            controlledEntity.startSearching();
        }

        isSearching = !isSearching;
    }

    @Override
    public void accept(ControllerActionVisitor v)
    {
        v.visitSearchAction(this);
    }
}
