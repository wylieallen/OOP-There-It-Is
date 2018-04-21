package gameview.util;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import maps.movelegalitychecker.Terrain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageMaker
{
    // Standard z-values are defined here:
    private static int TERRAIN_HEIGHT = 0;
    private static int ITEM_HEIGHT = 500;
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
        map.put(Terrain.SPACE, makeSpaceDisplayable());
        return map;
    }

    private static final Displayable NULL_DISPLAYABLE = new ImageDisplayable(new Point(16, 16), makeBorderedCircle(Color.WHITE), 1);

    public static Displayable getNullDisplayable()
    {
        return NULL_DISPLAYABLE;
    }

    private static Displayable makeGrassDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex("assets/terrains/aliengrass.png"), TERRAIN_HEIGHT);
    }

    private static Displayable makeMountainDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex("assets/terrains/alienmountain.png"), TERRAIN_HEIGHT);
    }

    private static Displayable makeWaterDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex("assets/terrains/alienwater.jpg"), TERRAIN_HEIGHT);
    }

    private static Displayable makeSpaceDisplayable()
    {
        return new ImageDisplayable(new Point(0, 0), makeBorderedHex("assets/terrains/space.png"), TERRAIN_HEIGHT);
    }

    public static Displayable makeEntityDisplayable(Entity e)
    {
        return new ImageDisplayable(new Point(22, 5), loadImage("assets/entities/entity1.png"), ENTITY_HEIGHT);
    }

    public static Displayable makeEntityDisplayable2(Entity e)
    {
        return new ImageDisplayable(new Point(22, 5), loadImage("assets/entities/entity2.png"), ENTITY_HEIGHT);
    }

    public static Displayable makeTeleporterDisplayable()
    {
        return new ImageDisplayable(new Point(22, 5), loadImage("assets/items/interactives/transitionitem.png"), ITEM_HEIGHT);
    }

    public static Displayable makeEncounterDisplayable1()
    {
        return new ImageDisplayable(new Point(8, 8), loadImage("assets/items/interactives/encounter1.png"), ITEM_HEIGHT);
    }

    public static Displayable makeEncounterDisplayable2()
    {
        return new ImageDisplayable(new Point(8, 8), loadImage("assets/items/interactives/encounter2.png"), ITEM_HEIGHT);
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

    public static BufferedImage makeBorderedHex(String path)
    {
        BufferedImage image = new BufferedImage(64 + 1, 64 + 1, BufferedImage.TYPE_INT_ARGB);
        BufferedImage graphics = loadImage(path);
        Graphics2D g2d = image.createGraphics();
        g2d.setClip(hexShape);
        g2d.drawImage(graphics, 0, 0, 64, 64, null);
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

    private static BufferedImage nullImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    private static BufferedImage loadImage(String path)
    {
        try
        {
            BufferedImage image = ImageIO.read(new File(path));
            if(image == null)
            {
                System.out.println("ERROR! File at " + path + " resulted in null image");
                image = nullImage;
            }
            return image;
        }
        catch(Exception e)
        {
            System.out.println("ERROR! Failed to read file at " + path);
            e.printStackTrace();
            return nullImage;
        }
    }
}
