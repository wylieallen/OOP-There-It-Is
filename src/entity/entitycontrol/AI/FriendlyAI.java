package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public class FriendlyAI extends AI {

    private boolean hasPath;

    public FriendlyAI(List<EntityInteraction> interactions, Map<Coordinate, Direction> path, boolean hasPath) {
        super(interactions, path);
        this.hasPath = hasPath;
    }

    @Override   // e is my entity
    public void nextAction(Map <Coordinate, Tile> map, Entity e, Coordinate location) {



        if (!hasPath() || getNextDirection(location) == Direction.NULL) {
            Coordinate end = getNextCoordinate (map.keySet(), e);
            setPath(location, end, e.getCompatibleTerrains(), map);
            hasPath = true;
        }

        e.setFacing(getNextDirection(location));
        e.setMoving();

    }

    private boolean hasPath () { return hasPath; }

    @Override
    public void accept(Visitor v) {
        v.visitFriendlyAI(this);
    }

}
