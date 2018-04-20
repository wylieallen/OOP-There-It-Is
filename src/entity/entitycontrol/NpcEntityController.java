package entity.entitycontrol;

import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitycontrol.AI.AI;
import entity.entitymodel.Equipment;
import entity.entitymodel.interactions.EntityInteraction;
import entity.entitymodel.interactions.TalkInteraction;
import entity.entitymodel.interactions.TradeInteraction;
import entity.entitymodel.interactions.UseItemInteraction;
import gameobject.GameObjectContainer;
import gameview.GamePanel;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NpcEntityController extends EntityController {

    private AI activeAi;
    private AI aggroAi;
    private AI nonAggroAi;

    public NpcEntityController(Entity entity, Equipment equipment, Coordinate entityLocation,
                               List<ControllerAction> actions, AI AggroAi, AI nonAggroAi,
                               boolean isAggro) {
        super(entity, equipment, entityLocation, actions);
        this.aggroAi = AggroAi;
        this.nonAggroAi = nonAggroAi;
        if(isAggro) {
            activeAi = aggroAi;
        } else {
            activeAi = nonAggroAi;
        }
        updateActeeInteractions();

    }

    @Override
    protected void processController() {
        //TODO
    }

    @Override
    public void interact(EntityController interacter) {
        //TODO
    }

    @Override
    public void notifyFreeMove(Entity e) {
        //TODO
    }

    @Override
    public void notifyInventoryManagment(Entity e) {
        //TODO
    }

    @Override
    public void notifyInteraction(Entity player, Entity interactee) {
        //TODO
    }

    @Override
    public void notifyShopping(Entity trader1, Entity trader2) {
        //TODO
    }

    @Override
    public void notifyLevelUp(Entity e) {
        //TODO
    }

    @Override
    public void notifyMainMenu(Entity e) {
        //TODO
    }

    @Override
    public void enrage(Entity e) {
        activeAi = aggroAi;
        updateActeeInteractions();
    }

    @Override
    public void pacify() {
        activeAi = nonAggroAi;
        updateActeeInteractions();
    }

    public void updateActeeInteractions() {
        getControlledEntity().setActeeInteractions(activeAi.getInteractions());
    }

    public void processAI(Map<Coordinate, GameObjectContainer> map, Entity e){
        //TODO
    }

    public boolean isAggro() {
        return activeAi == aggroAi;
    }

    public AI getAggroAi() {
        return aggroAi;
    }

    public AI getNonAggroAi(){
        return nonAggroAi;
    }

    @Override
    public void accept(Visitor v) {
        v.visitNpcEntityController(this);
    }
}
