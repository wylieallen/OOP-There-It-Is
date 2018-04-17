package maps.Influence;

import maps.tile.LocalWorldTile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public interface InfluenceArea {

    void update (Map<Coordinate, LocalWorldTile> tilesMap);//Tell the influence area to check its area against the tilesMap
    boolean isExpired();//Will return whether the InfluenceArea is ready to be deleted, as in its time has run out, it has reached its max radius, etc.
    List<Coordinate> getAffectedCoordinates();//Will return the list of coordinates this influence area is currently affecting

}
