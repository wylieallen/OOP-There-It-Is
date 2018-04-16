package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public abstract class AI {

    private List<EntityInteraction> interactions;

    public AI(List<EntityInteraction> interactions) {
        this.interactions = interactions;
    }

    public abstract void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e);

    public void setInteractions(Entity e){
        //TODO
    }

    public List<EntityInteraction> getInteractions() {
        return interactions;
    }

}
