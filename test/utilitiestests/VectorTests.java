package utilitiestests;

import maps.tile.Direction;
import org.junit.Assert;
import org.junit.Test;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;

public class VectorTests {

    @Test
    public void testVectorAddition() {
        Vector vector1 = new Vector();

        Vector vector2 = new Vector(Direction.N, 1);

        Vector vector3 = new Vector();

        vector3.add(vector1);
        Assert.assertTrue(vector3.equals(vector1));

        vector3.add(vector2);

        Assert.assertTrue(vector3.equals(vector2));
    }

    @Test
    public void testBasicVectorDirections() {
        Vector vector1 = new Vector(Direction.N, 1);
        Assert.assertEquals(vector1.getDirection(), Direction.N);

        vector1 = new Vector(Direction.NE, 1);
        Assert.assertEquals(vector1.getDirection(), Direction.NE);

        vector1 = new Vector(Direction.SE, 1);
        Assert.assertEquals(vector1.getDirection(), Direction.SE);

        vector1 = new Vector(Direction.S, 1);
        Assert.assertEquals(vector1.getDirection(), Direction.S);

        vector1 = new Vector(Direction.SW, 1);
        Assert.assertEquals(vector1.getDirection(), Direction.SW);

        vector1 = new Vector(Direction.NW, 1);
        Assert.assertEquals(vector1.getDirection(), Direction.NW);
    }

    @Test
    public void testAdvancedVectorDirections() {
        Coordinate[] nCoordinates = {new Coordinate(0 ,-1), new Coordinate(0 ,-2),
                                    new Coordinate(-1 ,-1), new Coordinate(0 ,-3),
                                    new Coordinate(-1 ,-2), new Coordinate(1 ,-3)};
        for(Coordinate coordinate: nCoordinates){
            Vector vector = new Vector(new Coordinate(0, 0), coordinate);
            Assert.assertEquals(vector.getDirection(), Direction.N);
        }

        Coordinate[] neCoordinates = {new Coordinate(1, -1), new Coordinate(2, -2),
                                    new Coordinate(1, -2), new Coordinate(2, -1),
                                    new Coordinate(3, -3), new Coordinate(3, -2),
                                    new Coordinate(3, -2), new Coordinate(2, -3)};
        for(Coordinate coordinate: neCoordinates){
            Vector vector = new Vector(new Coordinate(0, 0), coordinate);
            Assert.assertEquals(vector.getDirection(), Direction.NE);
        }

        Coordinate[] seCoordinates = {new Coordinate(1, 0), new Coordinate(2, 0),
                                    new Coordinate(1, 1), new Coordinate(3, 0),
                                    new Coordinate(2, 1), new Coordinate(3, -1)};
        for(Coordinate coordinate: seCoordinates){
            Vector vector = new Vector(new Coordinate(0, 0), coordinate);
            Assert.assertEquals(vector.getDirection(), Direction.SE);
        }

        Coordinate[] sCoordinates = {new Coordinate(0, 1), new Coordinate(0 ,2),
                                    new Coordinate(1, 2), new Coordinate(0 ,3),
                                    new Coordinate(-1, 3), new Coordinate(1, 2)};
        for(Coordinate coordinate: sCoordinates){
            Vector vector = new Vector(new Coordinate(0, 0), coordinate);
            Assert.assertEquals(vector.getDirection(), Direction.S);
        }

        Coordinate[] swCoordinates = {new Coordinate(-1 ,1), new Coordinate(-2 ,2),
                                    new Coordinate(-3 ,3), new Coordinate(-2 ,3),
                                    new Coordinate(-3 ,2)};
        for(Coordinate coordinate: swCoordinates){
            Vector vector = new Vector(new Coordinate(0, 0), coordinate);
            Assert.assertEquals(vector.getDirection(), Direction.SW);
        }

        Coordinate[] nwCoordinates = {new Coordinate(-1, 0), new Coordinate(-2, 0),
                                    new Coordinate(-2, 1), new Coordinate(-3, 0),
                                    new Coordinate(-3, 1), new Coordinate(-2, -1)};
        for(Coordinate coordinate: nwCoordinates){
            Vector vector = new Vector(new Coordinate(0, 0), coordinate);
            Assert.assertEquals(vector.getDirection(), Direction.NW);
        }

    }
}
