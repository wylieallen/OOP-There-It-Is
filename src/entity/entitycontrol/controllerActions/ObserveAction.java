package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import spawning.SpawnObservable;
import spawning.SpawnObserver;

import java.util.ArrayList;

public class ObserveAction extends ControllerAction implements SpawnObservable, GameObject{

    private ArrayList<SpawnObserver> observers;
    private Entity controlledEntity;

    public ObserveAction(Entity controlledEntity)
    {
        this.controlledEntity = controlledEntity;
    }

    @Override
    protected void execute() {
        //TODO: make sure to set the observe commands entitycontroller first;
    }

    @Override
    public void notifyAllOfSpawn(InfluenceArea IA) {
        for(SpawnObserver so : observers){
            so.notifySpawn(IA,this);
        }
    }

    @Override
    public void accept(ControllerActionVisitor v)
    {
        v.visitObserveAction(this);
    }

    @Override
    public void registerObserver(SpawnObserver SO) {

    }

    @Override
    public void deregisterObserver(SpawnObserver SO) {

    }
}
