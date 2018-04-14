package EntityControl.AI;

import EnitityModel.Entity;
import GameObject.GameObjectContainer;
import Utilities.Coordinate;

import java.util.ArrayList;
import java.util.Map;

public class PatrolAI extends AI {

    private ArrayList<Coordinate> patrolOrder;

    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO make it follow and patrol a route
    }
}
