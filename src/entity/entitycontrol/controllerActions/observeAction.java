package entity.entitycontrol.controllerActions;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import spawning.SpawnObservable;
import spawning.SpawnObserver;

import java.util.ArrayList;

public class observeAction extends ControllerAction implements SpawnObservable, GameObject{

    ArrayList<SpawnObserver> observers;
    Entity controlledEntity;


    @Override
    protected void execute() {

    }

    @Override
    public void notifyAllOfSpawn(InfluenceArea IA) {
        for(SpawnObserver so : observers){
            so.notifySpawn(IA,this);
        }
    }

    @Override
    public void registerObserver(SpawnObserver SO) {

    }

    @Override
    public void deregisterObserver(SpawnObserver SO) {

    }
}
