package entity.entitycontrol.controllerActions;

import commands.ObserveCommand;
import commands.skillcommands.SkillCommand;
import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import maps.Influence.InfluenceType;
import maps.Influence.expandingInfluenceArea;
import maps.tile.Direction;
import skills.SkillType;
import spawning.SpawnObservable;
import spawning.SpawnObserver;

import java.util.ArrayList;
import java.util.List;

public class ObserveAction extends ControllerAction implements SpawnObservable, GameObject{

    private ArrayList<SpawnObserver> observers;
    private Entity controlledEntity;
    private EntityController controller;

    public ObserveAction(Entity controlledEntity)
    {
        observers = new ArrayList<>();
        this.controlledEntity = controlledEntity;
    }

    @Override
    protected void execute() {
        //TODO: make sure to set the observe commands entitycontroller first;
        //TODO: make influence area

        int level = controlledEntity.getSkillLevel(SkillType.OBSERVATION);
        List <GameObject> whiteList = new ArrayList<>();

        whiteList.add(controlledEntity);

        ObserveCommand observe = new ObserveCommand(level);
        observe.setEntityController(controller);

        SkillCommand skillCommand = new SkillCommand(SkillType.OBSERVATION, level, 100, observe, null);

        InfluenceArea ia = new expandingInfluenceArea(InfluenceType.CIRCULARINFLUENCE, Direction.N, 2, controller.getEntityLocation(), whiteList, 1, 1, skillCommand);
        notifyAllOfSpawn(ia);
    }

    @Override
    public void notifyAllOfSpawn(InfluenceArea IA) {
        for(SpawnObserver so : observers){
            so.notifySpawn(IA,this);
        }
    }

    public void setController (EntityController controller) {
        this.controller = controller;
    }

    @Override
    public void accept(ControllerActionVisitor v)
    {
        v.visitObserveAction(this);
    }

    @Override
    public void registerObserver(SpawnObserver SO) {
        observers.add(SO);
    }

    @Override
    public void deregisterObserver(SpawnObserver SO) {
        observers.remove(SO);
    }
}
