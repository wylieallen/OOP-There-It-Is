package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public class PatrolAI extends AI {

    private List<Coordinate> patrolOrder;

    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO make it follow and patrol a route
    }

    @Override
    public void accept(Visitor v) {
        v.visitPatrolAI(this);
    }
}
