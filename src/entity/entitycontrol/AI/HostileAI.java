package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public class HostileAI extends AI {

    private Entity target;
    private Coordinate targetsLastPosition;
    private Coordinate myLastPosition;

    public HostileAI(List<EntityInteraction> interactions, Entity entity, Map<Coordinate, Direction> path){
        super(interactions, path);
        target = entity;
        targetsLastPosition = new Coordinate(0, 0);
        myLastPosition = null;
    }

    @Override
    public void nextAction(Map<Coordinate, LocalWorldTile> map, Entity e, Coordinate location) {
        //TODO: make it chase and attack the target

        Coordinate targetPosition = findTarget (map);

        if (isVisible (targetPosition, location) && (targetsLastPosition == null || !targetsLastPosition.equals(targetPosition))) {
            setPath(location, targetPosition, e.getCompatibleTerrains(), map);
            targetsLastPosition = targetPosition;
        } else if (targetsLastPosition == null || myLastPosition.equals(location)) {
            Coordinate end = getNextCoordinate(map.keySet(), e);
            setPath(location, end, e.getCompatibleTerrains(), map);
        }

        myLastPosition = location;
        e.setFacing(getNextDirection(location));

    }

    private boolean isVisible (Coordinate targetLoc, Coordinate myLoc) {
        return (target.getConcealment() <= myLoc.distance(targetLoc));
    }

    private Coordinate findTarget (Map <Coordinate, LocalWorldTile> map) {
        for (Coordinate c : map.keySet()) {
            if (map.get(c).has(target)) {
                return c;
            }
        }

        return targetsLastPosition;
    }
}
