package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import utilities.Coordinate;
import utilities.Vector;

import java.util.List;
import java.util.Map;

public class PatrolAI extends AI {

    private boolean onPath;
    private Coordinate lastPosition;
    private List<Coordinate> patrolOrder;

    public PatrolAI(List<EntityInteraction> interactions, List<Coordinate> patrolOrder, Map<Coordinate, Direction> path) {
        super(interactions, path);
        this.patrolOrder = patrolOrder;
        onPath = false;
        lastPosition = new Coordinate(0, 0);
    }

    @Override
    public void nextAction(Map<Coordinate, LocalWorldTile> map, Entity e, Coordinate location) {
        //TODO make it follow and patrol a route

        Direction face;

        if (patrolOrder.contains(location) && !lastPosition.equals(location)) {
            int curPos = patrolOrder.indexOf(location);
            Vector v = new Vector(location, patrolOrder.get(++curPos));
            face = v.getDirection();
            onPath = false;
        } else if (!isOnPath ()){
            Coordinate end = patrolOrder.get(0);
            setPath(location, end, e.getCompatibleTerrains(), map);
            face = getNextDirection(location);
            onPath = true;
        } else {
            face = getNextDirection(location);
        }

        lastPosition = location;

    }

    private boolean isOnPath () { return onPath; }
}
