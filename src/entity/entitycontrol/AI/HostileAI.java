package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.ArrayList;
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
    public void nextAction(Map <Coordinate, Tile> map, Entity e, Coordinate location) {
        //TODO: make it chase and attack the target

        Coordinate targetPosition;

        if (target != null) {
            targetPosition = findTarget(map);
            if (isVisible(targetPosition, location) || (targetsLastPosition == null)) {
                setPath(location, targetPosition, e.getCompatibleTerrains(), map);
                targetsLastPosition = targetPosition;
            }
        } else {
           targetPosition = findNewTarget (map);
           setPath(location, targetPosition, e.getCompatibleTerrains(), map);
        }

        if (targetIsNeghbor (location)) {
            e.setFacing(location.direction(targetsLastPosition));
            e.setMoving();
            // TODO: this is terrible, replace when controller supports attack!!!
            System.out.println("I is attacking!!!");
            e.getController().getEquipment().useWeaponItem(0, location);
        }

        myLastPosition = location;
        e.setFacing(getNextDirection(location));
        e.setMoving();

    }

    private boolean isVisible (Coordinate targetLoc, Coordinate myLoc) {
        return (target.getConcealment() <= myLoc.distance(targetLoc));
    }

    private Coordinate findTarget (Map <Coordinate, Tile> map) {
        for (Coordinate c : map.keySet()) {
            if (map.get(c).has(target)) {
                return c;
            }
        }

        return targetsLastPosition;
    }

    private Coordinate findNewTarget (Map <Coordinate, Tile> map) {

        List <Coordinate> points = new ArrayList<>();

        for (Direction d : Direction.values()) {
            points.add(myLastPosition.getNeighbor(d));
        }

        for (Coordinate c : points) {
            if (map.containsKey(c)) {
                if ((target = map.get(c).getEntity()) != null) {
                    return c;
                }
            }
        }

        return points.get(0);
    }

    private boolean targetIsNeghbor (Coordinate myLoc) {
        int distance = myLoc.distance(targetsLastPosition);

        return (distance <= 2 && distance > 0);
    }

    @Override
    public void accept(Visitor v) {
        v.visitHostileAI(this);
    }
}
