package maps.Influence;

import maps.tile.Direction;
import utilities.Coordinate;

import java.util.List;

public interface shapedOffsetPointsMaker {

    public List<Coordinate> getOffsetPoints(Direction direction,int radius);

}
