package entity.entitycontrol;

import entity.entitycontrol.AI.AI;
import entity.entitycontrol.controllerActions.DirectionalMoveAction;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NpcEntityController extends EntityController {

    private AI activeAi;
    private AI aggroAi;
    private AI nonAggroAi;

    public NpcEntityController(Entity entity, Equipment equipment, Coordinate entityLocation, AI AggroAi, AI nonAggroAi,
                               boolean isAggro) {
        super(entity, equipment, entityLocation, new ArrayList<>());
        this.aggroAi = AggroAi;
        this.nonAggroAi = nonAggroAi;
        if(isAggro) {
            activeAi = aggroAi;
        } else {
            activeAi = nonAggroAi;
        }
        updateActeeInteractions();

        for(Direction d : Direction.values())
        {
            if(d != Direction.NULL)
                addAction(new DirectionalMoveAction(entity, d));
        }

    }

    @Override
    protected void processController() {

    }

    @Override
    public void interact(EntityController interacter) {

    }

    @Override
    public void notifyFreeMove(Entity e) {

    }

    @Override
    public void notifyInventoryManagment(Entity e) {

    }

    @Override
    public void notifyInteraction(Entity player, Entity interactee) {
    }

    @Override
    public List <EntityInteraction> getInteractionList () {
        return getEntity().getActorInteractions();
    }

    @Override
    public void notifyShopping(Entity trader) {
        //TODO
    }

    @Override
    public void notifyLevelUp(Entity e) {

    }

    @Override
    public void notifyUseItem (Entity player, EntityController interactee) {

    }

    @Override
    public void notifyMainMenu(Entity e) {

    }

    @Override
    public void updateMap (Map <Coordinate, Tile> map) {
        update(map);
        if (activeAi != null) {
            activeAi.nextAction(map, getControlledEntity(), getEntityLocation());
        }
    }

    @Override
    public void enrage(Entity e) {
        if (aggroAi != null) {
            activeAi = aggroAi;
            updateActeeInteractions();
        }
    }

    @Override
    public void pacify() {
        if (nonAggroAi != null) {
            activeAi = nonAggroAi;
            updateActeeInteractions();
        }
    }

    @Override
    public boolean isAggroed () {
        return isAggro();
    }

    public void updateActeeInteractions() {
        if (activeAi != null) {
            getControlledEntity().setActeeInteractions(activeAi.getInteractions());
        }
    }


    public void processAI(Map<Coordinate, LocalWorldTile> map, Entity e){

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
