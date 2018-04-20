package entity.entitycontrol.AI.aiutilities;

import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

/**
 * Created by dontf on 4/15/2018.
 */
public class PathFinder {


    private static class pathItem {
        Coordinate current;
        Coordinate previous;
        Vector direction;

        public pathItem (Coordinate cur, Coordinate prev) {
            current = cur;
            previous = prev;

            if (prev != null && cur != null) {
                direction = new Vector(prev, cur);
            }
        }
    }

    public static HashMap <Coordinate, Direction> createLocalPath (Coordinate start, Coordinate end, List <Terrain> compatibleTerrains, Map <Coordinate, LocalWorldTile> map) {
        HashMap <Coordinate, Coordinate> path = new HashMap<>();
        Queue <pathItem> Q = new LinkedList<>();

        if (start == null || end == null || compatibleTerrains == null || map == null) return new HashMap<>();
        if (!map.containsKey(start) || !map.containsKey(end) || compatibleTerrains.isEmpty() || map.isEmpty()) return new HashMap<>();

        path.put (start, null);
        Q.add(new pathItem(start, null));

        boolean foundEnd = false;
        pathItem item;
        while (!Q.isEmpty() && !foundEnd) {
            item = Q.poll();

            for (Direction d : Direction.values()) {
                if (d != Direction.NULL && !path.containsKey(item.current.getNeighbor(d))) {
                    Coordinate neighbor = item.current.getNeighbor(d);

                    if (isCompatible(compatibleTerrains, map.get(neighbor))) {
                        Q.add(new pathItem(neighbor, item.current));
                        path.put(neighbor, item.current);
                    }

                    if (neighbor.equals(end)) {
                        foundEnd = true;
                        break;
                    }

                }
            }


        }

        return getDirectionsFromPath (end, path, map);
    }

    private static HashMap <Coordinate, Direction> getDirectionsFromPath (Coordinate end, Map <Coordinate, Coordinate> path, Map <Coordinate, LocalWorldTile> map) {

        HashMap <Coordinate, Direction> finalPath = new HashMap<>();

        if (!path.containsKey(end)) return finalPath;

        finalPath.put(end, Direction.NULL);

        Coordinate next = end;
        while (path.get(next) != null) { // if the previous node is null do not continue;
            Direction current = direction(path.get(next), next);
            finalPath.put(path.get(next), current);
            next = path.get(next);
        }

        return finalPath;
    }

    private static boolean isCompatible (List <Terrain> compatiblle, LocalWorldTile tile) {

        if (tile != null) {
            for (Terrain t : compatiblle) {
                if (tile.getMoveLegalityCheckers().contains(t)) {
                    return true;
                }
            }
        }

        return false;
    }

    // TODO can use this to take into account for rivers!!!
    private static Direction updateDirectionForTrajectoryEffects (Coordinate from, Coordinate to, Map <Coordinate, LocalWorldTile> map) {


        return Direction.NULL;
    }

    // get direction from start to end
    private static Direction direction (Coordinate start, Coordinate end) {
        Vector v = new Vector(start, end);
        return v.getDirection();
    }
}
