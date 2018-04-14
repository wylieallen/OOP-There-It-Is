package gameview.util;

import gameobject.GameObject;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import maps.movelegalitychecker.Terrain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageMaker
{
    // Standard z-values are defined here:
    private static int TERRAIN_HEIGHT = 0;

    private static Shape hexShape = makeHexShape();

    private static Shape makeHexShape()
    {
        int[] x = {0, 16, 48, 32, 64, 64};//{0, 1, 3, 4, 1, 3};
        int[] y = {32, 0, 0, 32, 64, 64};//{2, 0, 0, 2, 4, 4};
        return new Polygon(x, y, 6);
    }

    public static Map<GameObject, Displayable> makeDefaultMap()
    {
        Map<GameObject, Displayable> map = new HashMap<>();
        map.put(Terrain.GRASS, getGrassDisplayable());
        map.put(Terrain.MOUNTAIN, getMountainDisplayable());
        map.put(Terrain.WATER, getWaterDisplayable());
        return map;
    }

    private static Displayable getGrassDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex(64,  Color.GREEN), TERRAIN_HEIGHT);
    }

    private static Displayable getMountainDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex(64,  Color.GRAY), TERRAIN_HEIGHT);
    }

    private static Displayable getWaterDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex(64,  Color.BLUE), TERRAIN_HEIGHT);
    }

    private static BufferedImage makeBorderedHex(int size, Color color)
    {
        //double scaleFactor = ((double) size) / 4;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        //g2d.scale(scaleFactor, scaleFactor);
        g2d.setColor(color);
        g2d.fill(hexShape);
        g2d.setColor(Color.WHITE);
        g2d.draw(hexShape);
        return image;
    }
}
