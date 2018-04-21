package entity.entitycontrol;

import entity.entitycontrol.AI.AI;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.Map;

public class NpcEntityController extends EntityController {

    private AI activeAi;
    private AI aggroAi;
    private AI nonAggroAi;

    public NpcEntityController(Entity entity, Equipment equipment, Coordinate entityLocation, AI AggroAi, AI nonAggroAi,
                               boolean isAggro) {
        super(entity, equipment, entityLocation, entity.getControllerActions());
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
    public void updateMap (Map <Coordinate, Tile> map) {
        update(map);
        activeAi.nextAction(map, getControlledEntity(), getEntityLocation());
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

    // TODO: make sure if there is a mount it gives a mount, not its entity
    public void processAI(Map<Coordinate, LocalWorldTile> map, Entity e){
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
