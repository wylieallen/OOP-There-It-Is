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
    public void nextAction(Map<Coordinate, Tile> map, Entity e, Coordinate location) {

        if (master != null) {
            Coordinate mastersPosition = findMaster(map);
            int distance = location.distance(mastersPosition);

            if (distance <= 2) {
                hasPath = false;
            }

            if (distance > maxDistanceFromMaster) {
                setPath(location, mastersPosition, e.getCompatibleTerrains(), map);
                hasPath = true;
            } else if (!hasPath || myLastPosition.equals(location)) {
                Coordinate targetPosition = getNextCoordinate(map.keySet(), e);
                setPath(location, targetPosition, e.getCompatibleTerrains(), map);
                hasPath = true;
            }
        } else {
            mastersLastPosition = findNewMaster (map);
            setPath(location, mastersLastPosition, e.getCompatibleTerrains(), map);
        }

        myLastPosition = location;
        e.setFacing(getNextDirection(location));
        e.setMoving();

    }

    private Coordinate findMaster (Map <Coordinate, Tile> map) {
        for (Coordinate c : map.keySet()) {
            if (map.get(c).has(master)) {
                return c;
            }
        }

        return mastersLastPosition;
    }

    private Coordinate findNewMaster (Map <Coordinate, Tile> map) {

        List <Coordinate> points = new ArrayList<>();

        for (Direction d : Direction.values()) {
            points.add(myLastPosition.getNeighbor(d));
        }

        for (Coordinate c : points) {
            if (map.containsKey(c)) {
                if ((master = map.get(c).getEntity()) != null) {
                    return c;
                }
            }
        }

        return points.get(0);
    }

    @Override
    public void accept(Visitor v) {
        v.visitPetAI(this);
    }
}
