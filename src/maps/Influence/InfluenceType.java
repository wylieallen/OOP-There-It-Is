package maps.Influence;

import maps.tile.Direction;
import utilities.Coordinate;

import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */
public enum InfluenceType {

    LINEARINFLUENCE(new linearOffsetPointsMaker()),
    ANGULARINFLUENCE(new angularOffsetPointsMaker()),
    CIRCULARINFLUENCE(new circularOffsetPointsMaker());

    private final shapedOffsetPointsMaker offsetMaker;

    InfluenceType(shapedOffsetPointsMaker offsetMaker){
        this.offsetMaker = offsetMaker;
    }

    public List<Coordinate> getOffsets(Direction direction, int radius){
        return offsetMaker.getOffsetPoints(direction,radius);
    }

}
