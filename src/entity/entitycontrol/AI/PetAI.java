package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetAI extends AI {

    private final int maxDistanceFromMaster = 10;

    private Entity master;
    private int lastHealth;
    private boolean hasPath;
    private Coordinate mastersLastPosition;
    private Coordinate myLastPosition;

    public PetAI(List<EntityInteraction> interactions, Entity master, Map<Coordinate, Direction> path, boolean hasPath) {
        super(interactions, path);
        this.master = master;
        lastHealth = 0;
        mastersLastPosition = new Coordinate(0, 0);
        myLastPosition = new Coordinate(0, 0);
        this.hasPath = hasPath;
    }

    @Override
    public void nextAction(Map<Coordinate, LocalWorldTile> map, Entity e, Coordinate location) {

        Coordinate mastersPosition = findMaster(map);
        int distance = location.distance(mastersPosition);

        if (distance <= 2) { hasPath = false; }

        if (distance > maxDistanceFromMaster) {
            setPath(location, mastersPosition, e.getCompatibleTerrains(), map);
            hasPath = true;
        } else if (!hasPath || myLastPosition.equals(location)) {
            Coordinate targetPosition = findItem(map, location, maxDistanceFromMaster - distance);

            if (targetPosition == null) {
                targetPosition = getNextCoordinate(map.keySet(), e);
            }

            setPath(location, targetPosition, e.getCompatibleTerrains(), map);
            hasPath = true;
        }

        myLastPosition = location;
        e.setFacing(getNextDirection(location));

    }

    private Coordinate findItem (Map <Coordinate, LocalWorldTile> map, Coordinate myLocation, int searchDistance) {
        // check neighboring tiles for items only

        List <Coordinate> points = new ArrayList<>();

        for (Direction d : Direction.values()) {
            points.add(myLocation.getNeighbor(d));
        }

        for (Coordinate c : points) {
            if (map.containsKey(c) && map.get (c).hasImpactor()) {
                return c;
            }
        }

        return null;
    }

    private Coordinate findMaster (Map <Coordinate, LocalWorldTile> map) {
        for (Coordinate c : map.keySet()) {
            if (map.get(c).has(master)) {
                return c;
            }
        }

        return mastersLastPosition;
    }
}
