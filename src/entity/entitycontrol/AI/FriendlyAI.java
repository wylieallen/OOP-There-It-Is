package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import utilities.Coordinate;

import java.util.*;

public class FriendlyAI extends AI {

    private boolean hasPath;
    private Coordinate lastLocation;

    public FriendlyAI(List<EntityInteraction> interactions, Map<Coordinate, Direction> path, boolean hasPath) {
        super(interactions, path);
        this.hasPath = hasPath;
        this.lastLocation = new Coordinate(0, 0);
    }

    @Override   // e is my entity
    public void nextAction(Map<Coordinate, LocalWorldTile> map, Entity e, Coordinate location) {

        //TODO: make the entity allow for interactions with other entities

        if (!hasPath() || location.equals(lastLocation)) {
            Coordinate end = getNextCoordinate (map.keySet(), e);
            setPath(location, end, e.getCompatibleTerrains(), map);
            hasPath = true;
        }

        lastLocation = location;
        e.setFacing(getNextDirection(location));

    }

    private boolean hasPath () { return hasPath; }
}
