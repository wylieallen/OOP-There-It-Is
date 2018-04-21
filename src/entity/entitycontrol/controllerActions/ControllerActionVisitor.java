package entity.entitycontrol.controllerActions;

public interface ControllerActionVisitor
{
    void visitAttackAction(AttackAction a);
    void visitBindWoundsAction(BindWoundsAction a);
    void visitCreepAction(CreepAction a);
    void visitDirectionalMoveAction(DirectionalMoveAction a);
    void visitMoveAction(MoveAction a);
    void visitObserveAction(ObserveAction a);
    void visitSetDirectionAction(SetDirectionAction a);
    void visitDismountAction (DismountAction a);
}
