package offsetMakerTests;

import maps.Influence.linearOffsetPointsMaker;
import maps.tile.Direction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.Coordinate;

import java.util.List;

public class linearOffsetMakerTests {


    @BeforeClass
    public static void setup() {}

    @Test
    public void testNorthLineMaker() {
        linearOffsetPointsMaker lineMaker = new linearOffsetPointsMaker();

        Direction direction = Direction.N;

        int radius = 0;
        List<Coordinate> offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertFalse(offsets.isEmpty());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));

        radius = 2;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(3,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));

        radius = 3;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(4,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,-3)));

    }

    @Test
    public void testSouthLineMaker() {
        linearOffsetPointsMaker lineMaker = new linearOffsetPointsMaker();

        Direction direction = Direction.S;

        int radius = 0;
        List<Coordinate> offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,1)));

        radius = 2;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(3,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,2)));

        radius = 3;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(4,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,1)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,2)));
        Assert.assertTrue(offsets.contains(new Coordinate(0,3)));

    }

    @Test
    public void testNorthWestLineMaker() {
        linearOffsetPointsMaker lineMaker = new linearOffsetPointsMaker();

        Direction direction = Direction.NW;

        int radius = 0;
        List<Coordinate> offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertFalse(offsets.isEmpty());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));

        radius = 2;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(3,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-2,0)));

        radius = 3;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(4,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-2,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-3,0)));

    }

    @Test
    public void testNorthEastLineMaker() {
        linearOffsetPointsMaker lineMaker = new linearOffsetPointsMaker();

        Direction direction = Direction.NE;

        int radius = 0;
        List<Coordinate> offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));

        radius = 2;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(3,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(2,-2)));

        radius = 3;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(4,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(1,-1)));
        Assert.assertTrue(offsets.contains(new Coordinate(2,-2)));
        Assert.assertTrue(offsets.contains(new Coordinate(3,-3)));

    }

    @Test
    public void testSouthEastLineMaker() {
        linearOffsetPointsMaker lineMaker = new linearOffsetPointsMaker();

        Direction direction = Direction.SE;

        int radius = 0;
        List<Coordinate> offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(1,0)));

        radius = 2;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(3,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(1,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(2,0)));

        radius = 3;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(4,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(1,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(2,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(3,0)));

    }

    @Test
    public void testSouthWestLineMaker() {
        linearOffsetPointsMaker lineMaker = new linearOffsetPointsMaker();

        Direction direction = Direction.SW;

        int radius = 0;
        List<Coordinate> offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(1,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));

        radius = 1;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(2,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,1)));

        radius = 2;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(3,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,1)));
        Assert.assertTrue(offsets.contains(new Coordinate(-2,2)));

        radius = 3;
        offsets = lineMaker.getOffsetPoints(direction, radius);
        Assert.assertEquals(4,offsets.size());
        Assert.assertTrue(offsets.contains(new Coordinate(0,0)));
        Assert.assertTrue(offsets.contains(new Coordinate(-1,1)));
        Assert.assertTrue(offsets.contains(new Coordinate(-2,2)));
        Assert.assertTrue(offsets.contains(new Coordinate(-3,3)));

    }
}
