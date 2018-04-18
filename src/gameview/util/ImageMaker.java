package gameview.util;

import entity.entitymodel.Entity;
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
    private static int ENTITY_HEIGHT = 999;

    private static Shape hexShape = makeHexShape();

    private static Shape makeHexShape()
    {
        int[] x = {0, 16, 48, 64, 48, 16};
        int[] y = {32, 0, 0, 32, 64, 64};
        return new Polygon(x, y, 6);
    }

    public static Map<GameObject, Displayable> makeDefaultMap()
    {
        Map<GameObject, Displayable> map = new HashMap<>();
        map.put(Terrain.GRASS, makeGrassDisplayable());
        map.put(Terrain.MOUNTAIN, makeMountainDisplayable());
        map.put(Terrain.WATER, makeWaterDisplayable());
        return map;
    }

    private static Displayable makeGrassDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex(Color.GREEN), TERRAIN_HEIGHT);
    }

    private static Displayable makeMountainDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex(Color.GRAY), TERRAIN_HEIGHT);
    }

    private static Displayable makeWaterDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex(Color.BLUE), TERRAIN_HEIGHT);
    }

    public static Displayable makeEntityDisplayable(Entity e)
    {
        return new ImageDisplayable(new Point(16, 16), makeBorderedCircle(Color.MAGENTA), ENTITY_HEIGHT);
    }


    // Todo: split BufferedImage creation off into a separate class
    public static BufferedImage makeBorderedHex(Color color)
    {
        BufferedImage image = new BufferedImage(64 + 1, 64 + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fill(hexShape);
        g2d.setColor(Color.WHITE);
        g2d.draw(hexShape);
        return image;
    }

    public static BufferedImage makeBorderedCircle(Color color)
    {
        BufferedImage image = new BufferedImage(32 + 1, 32 + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(0, 0, 32, 32);
        g2d.setColor(Color.WHITE);
        g2d.drawOval(0, 0, 32, 32);
        return image;
    }

    public static BufferedImage makeBorderedRect(int width, int height, Color color)
    {
        BufferedImage image = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(0, 0, width, height);
        return image;
    }
}
