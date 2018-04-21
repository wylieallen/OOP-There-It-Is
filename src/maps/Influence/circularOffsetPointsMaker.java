package maps.Influence;

import maps.tile.Direction;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.List;

public class circularOffsetPointsMaker implements shapedOffsetPointsMaker {
    @Override
    public List<Coordinate> getOffsetPoints(Direction direction, int radius) {
        List<Coordinate> offsets = new ArrayList<Coordinate>();
        if(radius < 0){
            return offsets;
        }

        Coordinate origin = new Coordinate(0,0);
        for(int i = -radius ; i<= radius; i++){
            for(int j = -radius ; j<=radius; j++){
                Coordinate coord = new Coordinate(i,j);
                Vector vec = new Vector(origin,coord);
                if((vec.getDistance() < (radius+1))){
                    offsets.add(coord);
                }
            }
        }

        return offsets;
    }
}
