package offsetMakerTests;

import maps.Influence.circularOffsetPointsMaker;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Coordinate;

import java.util.List;

public class circularOffsetMakerTests {


    @BeforeClass
    public static void setup() {}

    @Test
    public void testCircleMaker() {
        circularOffsetPointsMaker circleMaker = new circularOffsetPointsMaker();

        Direction direction = Direction.N;

        int radius = 0;
        List<Coordinate> offsets = circleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertTrue(containsRadiusZeroPoints(offsets));

        radius = 1;
        offsets = circleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(7,offsets.size());
        Assert.assertTrue(containsRadiusZeroPoints(offsets));
        Assert.assertTrue(containsRadiusOnePoints(offsets));


        radius = 2;
        offsets = circleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(19,offsets.size());
        Assert.assertTrue(containsRadiusZeroPoints(offsets));
        Assert.assertTrue(containsRadiusOnePoints(offsets));
        Assert.assertTrue(containsRadiusTwoPoints(offsets));


    }

    private boolean containsRadiusZeroPoints(List<Coordinate> offsets) {
        return offsets.contains(new Coordinate(0,0));
    }

    private boolean containsRadiusOnePoints(List<Coordinate> offsets){
        boolean result = true;
        if(!offsets.contains(new Coordinate(1,0))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(1,-1))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(0,-1))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-1,0))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-1,1))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(0,1))){
            result = false;
        }
        return result;
    }

    private boolean containsRadiusTwoPoints(List<Coordinate> offsets){
        boolean result = true;
        if(!offsets.contains(new Coordinate(2,0))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(2,-1))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(2,-2))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(1,-2))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(0,-2))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-1,-1))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-2,0))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-2,1))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-2,2))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(-1,2))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(0,2))){
            result = false;
        }
        if(!offsets.contains(new Coordinate(1,1))){
            result = false;
        }
        return result;
    }


}
