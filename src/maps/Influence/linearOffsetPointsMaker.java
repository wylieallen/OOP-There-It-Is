package maps.Influence;

import maps.tile.Direction;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class linearOffsetPointsMaker implements shapedOffsetPointsMaker {

    @Override
    public List<Coordinate> getOffsetPoints(Direction direction, int radius) {
        List<Coordinate> offsets = new ArrayList<Coordinate>();
        if(radius < 0){
            return offsets;
        }

        Coordinate current = new Coordinate(0,0);
        offsets.add(current);
        for(int i = 0; i < radius; i++) {
            offsets.add(new Coordinate(current.getNeighbor(direction)));
            current = new Coordinate(current.getNeighbor(direction));
        }

        return offsets;
    }
}
