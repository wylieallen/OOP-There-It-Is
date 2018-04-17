package gameview;

import gameobject.GameObject;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.DisplayState;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import maps.tile.Tile;
import maps.world.Game;
import maps.world.World;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameDisplayState extends DisplayState
{
    private Game game;
    private Point camera;
    private double zoom = 1.0;
    private static Map<GameObject, Displayable> spriteMap;
    private Map<World, WorldDisplayable> worlds;
    private WorldDisplayable activeWorldDisplayable;

    private static final int RENDERING_FRAMES_PER_GAME_TICK = 10;
    private int gameTickCountdown = RENDERING_FRAMES_PER_GAME_TICK;

    // todo: the Map<World, WorldDisplayable> doesn't need to be a constructor parameter
    // GameDisplayState can just instantiate new WorldDisplayables as we need them
    public GameDisplayState(Game game, Map<GameObject, Displayable> spriteMap, Map<World, WorldDisplayable> worlds, World initialWorld)
    {
        this.game = game;
        GameDisplayState.spriteMap = spriteMap;
        this.camera = new Point(0, 0);
        this.worlds = worlds;
        super.add(activeWorldDisplayable = worlds.get(initialWorld));
    }

    public static Displayable getSprite(GameObject o) { return spriteMap.get(o); }

    public static void registerSprite(GameObject o, Displayable d) { spriteMap.put(o, d); }

    public void transitionWorld(World nextWorld)
    {
        remove(activeWorldDisplayable);
        add(activeWorldDisplayable = worlds.get(nextWorld));
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.translate(-camera.x, -camera.y);
        g2d.scale(zoom, zoom);
        super.draw(g2d);
    }

    @Override
    public void update()
    {
        if(--gameTickCountdown <= 0)
        {
            game.update();
            gameTickCountdown = RENDERING_FRAMES_PER_GAME_TICK;
        }
        super.update();

        // todo: this could potentially be enough of a performance drain that we should just skip it and let memory leak
        for(GameObject o : spriteMap.keySet())
        {
            if(o.expired())
                spriteMap.remove(o);
        }
    }

    public void translateCamera(int dx, int dy) { camera.translate(dx, dy); }
    public void snapCamera(int x, int y) { camera.setLocation(x, y); }
    public void adjustZoom(double dz) { zoom += dz; }
    public void scaleZoom(double dz) { zoom *= dz; }
    public void setZoom(double zoom) { this.zoom = zoom; }
}
