package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public class FriendlyAI extends AI {

    public FriendlyAI(List<EntityInteraction> interactions) {
        super(interactions);
    }

    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO: make the entity allow for interactions with other entities
    }
}
