package entity.entitycontrol.AI;

import entity.entitycontrol.AI.aiutilities.PathFinder;
import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitable;
import utilities.Coordinate;

import java.util.*;

public abstract class AI implements Visitable {

    private Random rand;
    private List<EntityInteraction> interactions;
    private Map <Coordinate, Direction> path;

    public AI(List<EntityInteraction> interactions, Map<Coordinate, Direction> path) {
        this.interactions = interactions;
        this.path = path;
        rand = new Random();
    }

    public abstract void nextAction(Map <Coordinate, Tile> map, Entity e, Coordinate location);

    public void setInteractions(Entity e){
        interactions = e.getActorInteractions();
    }

    public List<EntityInteraction> getInteractions() {
        return interactions;
    }

    protected Direction getNextDirection (Coordinate c) {
        return path.getOrDefault(c, Direction.NULL);
    }

    protected final void setPath (Coordinate start, Coordinate end, Set <Terrain> compatible, Map <Coordinate, Tile> map) {

        if (!map.containsKey(end) || !map.containsKey(start)) {
            System.out.println("Incorrect End or Start Point, check your AI's.");
            assert false;
        }

        path = PathFinder.createLocalPath (start, end, compatible, map);

        for (Coordinate c : path.keySet()) {
            System.out.println("x: " + c.x() + " z: " + c.z());
        }
        System.out.println();
    }

    protected Coordinate getNextCoordinate (Set<Coordinate> points, Entity e) {
        List <Coordinate> temp = getArray (points);
        return temp.get (rand.nextInt(temp.size()));
    }

    // cannot get random element from collection
    private List <Coordinate> getArray (Set <Coordinate> points) {
        List <Coordinate> arr = new LinkedList<>();

        for (Coordinate c : points) {
            arr.add(c);
        }

        return arr;
    }

}
