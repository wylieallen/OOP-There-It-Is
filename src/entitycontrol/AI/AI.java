package entitycontrol.AI;

import entitymodel.Entity;
import entitymodel.EntityInteraction;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public abstract class AI {

    private List<EntityInteraction> interactions;

    public abstract void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e);

    public void setInteractions(Entity e){
        //TODO
    }

}
