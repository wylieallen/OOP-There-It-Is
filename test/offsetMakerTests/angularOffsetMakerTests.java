package offsetMakerTests;

import maps.Influence.angularOffsetPointsMaker;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Coordinate;

import java.util.List;

public class angularOffsetMakerTests {


    @BeforeClass
    public static void setup() {}

    @Test
    public void testNorthAngleMaker() {
        angularOffsetPointsMaker angleMaker = new angularOffsetPointsMaker();

        Direction direction = Direction.N;

        int radius = 0;
        List<Coordinate> offsets = angleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertFalse(offsets.isEmpty());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = angleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));

        radius = 2;
        offsets = angleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(5,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate( 1,-2)));

        radius = 3;
        offsets = angleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(8,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate( 1,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-3)));
        Assert.assertTrue(offsets.contains(new Coordinate(1,-3)));

        radius = 4;
        offsets = angleMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(13,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate( 1,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-3)));
        Assert.assertTrue(offsets.contains(new Coordinate(1,-3)));
        Assert.assertTrue(offsets.contains(new Coordinate(-2,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate( -1,-3)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-4)));
        Assert.assertTrue(offsets.contains(new Coordinate(1,-4)));
        Assert.assertTrue(offsets.contains(new Coordinate(2,-4)));
    }
//
//    @Test
//    public void testNorthEastAngleMaker() {
//        angularOffsetPointsMaker angleMaker = new angularOffsetPointsMaker();
//
//        Direction direction = Direction.NE;
//
//        int radius = 0;
//        List<Coordinate> offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(1,offsets.size());
//        Assert.assertFalse(offsets.isEmpty());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//
//        radius = 1;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(4,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
//
//        radius = 2;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(7,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-2)));
//
//        radius = 3;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(12,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-2)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-2,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,-2)));
//        Assert.assertTrue(offsets.contains(new Coordinate(0,-3)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-3)));
//        Assert.assertTrue(offsets.contains(new Coordinate(2,-3)));
//
//    }

//    @Test
//    public void testNorthWestAngleMaker() {
//        angularOffsetPointsMaker angleMaker = new angularOffsetPointsMaker();
//
//        Direction direction = Direction.NW;
//
//        int radius = 0;
//        List<Coordinate> offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(1,offsets.size());
//        Assert.assertFalse(offsets.isEmpty());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//
//        radius = 1;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(2,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
//
//        radius = 2;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(3,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-2,0)));
//
//        radius = 3;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(4,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-2,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-3,0)));
//
//    }
//
//    @Test
//    public void testNorthEastAngleMaker() {
//        angularOffsetPointsMaker angleMaker = new angularOffsetPointsMaker();
//
//        Direction direction = Direction.NE;
//
//        int radius = 0;
//        List<Coordinate> offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(1,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//
//        radius = 1;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(2,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
//
//        radius = 2;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(3,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(2,-2)));
//
//        radius = 3;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(4,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(2,-2)));
//        Assert.assertTrue(offsets.contains(new Coordinate(3,-3)));
//
//    }
//
//    @Test
//    public void testSouthEastAngleMaker() {
//        angularOffsetPointsMaker angleMaker = new angularOffsetPointsMaker();
//
//        Direction direction = Direction.SE;
//
//        int radius = 0;
//        List<Coordinate> offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(1,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//
//        radius = 1;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(2,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,0)));
//
//        radius = 2;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(3,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(2,0)));
//
//        radius = 3;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(4,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(1,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(2,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(3,0)));
//
//    }
//
//    @Test
//    public void testSouthWestAngleMaker() {
//        angularOffsetPointsMaker angleMaker = new angularOffsetPointsMaker();
//
//        Direction direction = Direction.SW;
//
//        int radius = 0;
//        List<Coordinate> offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(1,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//
//        radius = 1;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(2,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,1)));
//
//        radius = 2;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(3,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-2,2)));
//
//        radius = 3;
//        offsets = angleMaker.getOffsetPoints(direction, radius);
//        Assert.assertEquals(4,offsets.size());
//        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-1,1)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-2,2)));
//        Assert.assertTrue(offsets.contains(new Coordinate(-3,3)));
//
//    }
}
