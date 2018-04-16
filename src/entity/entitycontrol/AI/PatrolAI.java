package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public class PatrolAI extends AI {

    private List<Coordinate> patrolOrder;

    public PatrolAI(List<EntityInteraction> interactions, List<Coordinate> patrolOrder) {
        super(interactions);
        this.patrolOrder = patrolOrder;
    }

    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO make it follow and patrol a route
    }
}
