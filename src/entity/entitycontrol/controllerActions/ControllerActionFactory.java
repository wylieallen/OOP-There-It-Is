package entity.entitycontrol.controllerActions;

import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import maps.tile.Direction;

import java.util.ArrayList;
import java.util.List;

public class ControllerActionFactory {

    public static List<ControllerAction> makeSmasherActions(Entity entity, EntityController controlledEntityController, Equipment equipment){
        List<ControllerAction> actions = makeBasicControllerActions(entity, controlledEntityController, equipment);
        return actions;
    }

    public static List<ControllerAction> makeSummonerActions(Entity entity, EntityController controlledEntityController, Equipment equipment){
        List<ControllerAction> actions = makeBasicControllerActions(entity, controlledEntityController, equipment);
        return actions;
    }

    public static List<ControllerAction> makeSneakActions(Entity entity, EntityController controlledEntityController, Equipment equipment){
        List<ControllerAction> actions = makeBasicControllerActions(entity, controlledEntityController, equipment);
        actions.add(new CreepAction(entity, false, 0, 0));
        return actions;
    }

    public static List<ControllerAction> makeBasicNPCActions(Entity entity, EntityController controlledEntityController, Equipment equipment){
        List<ControllerAction> actions = makeBasicControllerActions(entity, controlledEntityController, equipment);
        return actions;
    }

    private static List<ControllerAction> makeBasicControllerActions(Entity entity, EntityController controlledEntityController, Equipment equipment){
        List<ControllerAction> actions = new ArrayList<>();
        for (int i=0; i<Equipment.getDefaultWeaponsSize(); i++) {
            actions.add(new AttackAction(controlledEntityController, equipment, i));
        }
        actions.add(new BindWoundsAction(entity));
        for (Direction direction : Direction.values()) {
            actions.add(new DirectionalMoveAction(entity, direction));
            actions.add(new SetDirectionAction(entity, direction));
        }
        actions.add(new MoveAction(entity));
        actions.add(new ObserveAction(entity));
        return actions;
    }

}
