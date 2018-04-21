package maps.Influence;

import maps.tile.Direction;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.List;

public class angularOffsetPointsMaker implements shapedOffsetPointsMaker {
    @Override
    public List<Coordinate> getOffsetPoints(Direction direction, int radius) {
        List<Coordinate> offsets = new ArrayList<Coordinate>();
        if(radius < 0){
            return offsets;
        }


        Coordinate origin = new Coordinate(0,0);
        Coordinate clockWiseExpander = origin;
        Coordinate counterClockWiseExpander = origin;
        Coordinate tempClockwise = clockWiseExpander;
        Coordinate tempCounterClockwise = counterClockWiseExpander;
        offsets.add(origin);
        ArrayList<Coordinate> outerRadius = new ArrayList<Coordinate>();
        ArrayList<Coordinate> tempRadius = new ArrayList<Coordinate>();
        outerRadius.add(origin);
        boolean expanding = false;
        for(int i = 0; i<radius; i++){
            tempRadius.clear();
            for(Coordinate c : outerRadius){
                tempRadius.add(new Coordinate(c.getNeighbor(direction)));
                if(c.equals(clockWiseExpander)){
                    tempClockwise = new Coordinate(c.getNeighbor(direction));
                    if(expanding) {
                        tempRadius.add(new Coordinate(c.getNeighbor(direction.getClockWiseAdjacent())));
                        tempClockwise = new Coordinate(c.getNeighbor(direction.getClockWiseAdjacent()));
                    }
                }
                if(c.equals(counterClockWiseExpander)){
                    tempCounterClockwise = new Coordinate(c.getNeighbor(direction));
                    if(expanding) {
                        tempRadius.add(new Coordinate(c.getNeighbor(direction.getCounterClockwiseAdjacent())));
                        tempCounterClockwise = new Coordinate(c.getNeighbor(direction.getCounterClockwiseAdjacent()));
                    }
                }
            }
            clockWiseExpander = tempClockwise;
            counterClockWiseExpander = tempCounterClockwise;
            outerRadius = new ArrayList<Coordinate>(tempRadius);
            for(Coordinate c : outerRadius){
                offsets.add(c);
            }
            expanding = !expanding;
        }


        return offsets;
    }
}
