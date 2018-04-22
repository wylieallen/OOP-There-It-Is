package maps.Influence;

import maps.tile.Direction;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class selfOffsetPointsMaker implements shapedOffsetPointsMaker {
    @Override
    public List<Coordinate> getOffsetPoints(Direction direction, int radius) {
        ArrayList<Coordinate> list = new ArrayList<>();
        list.add(new Coordinate(0, 0));
        return list;
    }
}
