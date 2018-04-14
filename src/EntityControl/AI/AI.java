package EntityControl.AI;

import EnitityModel.Entity;
import EnitityModel.EntityInteraction;
import GameObject.GameObjectContainer;
import Utilities.Coordinate;

import java.util.List;
import java.util.Map;

public abstract class AI {

    private List<EntityInteraction> interactions;

    public abstract void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e);

    public void setInteractions(Entity e){
        //TODO
    }

}
