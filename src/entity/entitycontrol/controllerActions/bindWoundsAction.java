package entity.entitycontrol.controllerActions;

import commands.ModifyHealthCommand;
import commands.skillcommands.SkillCommand;
import entity.entitymodel.Entity;
import skills.SkillType;

public class bindWoundsAction extends ControllerAction {

    //This will use the Entity's bind wounds skill to heal them

    Entity controlledEntity;

    bindWoundsAction(Entity controlledEntity){
        if(controlledEntity.getSkillLevel(SkillType.BINDWOUNDS)==-1){
            throw new java.lang.RuntimeException("bindWoundsAction::Constructor : An attempt was made to create a bindWoundsAction for an entity that does not have SkillType.BINDWOUNDS");
        }
        else {
            this.controlledEntity = controlledEntity;
        }
    }

    @Override
    protected void execute() {
        ModifyHealthCommand bindWounds = new ModifyHealthCommand();
        SkillCommand skillCommand = new SkillCommand(SkillType.BINDWOUNDS,
                controlledEntity.getSkillLevel(SkillType.BINDWOUNDS), 10,
                bindWounds, null);
        skillCommand.trigger(controlledEntity);
    }
}
