package gameview;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.DisplayState;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;
import maps.world.Game;
import maps.world.World;
import utilities.Coordinate;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;
import java.util.PriorityQueue;

public class GameDisplayState extends DisplayState
{
    private Dimension size;
    private PriorityQueue<Displayable> widgets;
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
    public GameDisplayState(Dimension size, Game game, Map<GameObject, Displayable> spriteMap, Map<World, WorldDisplayable> worlds, World initialWorld)
    {
        super();
        this.size = size;
        this.game = game;
        GameDisplayState.spriteMap = spriteMap;
        this.camera = new Point(0, 0);
        this.worlds = worlds;
        super.add(activeWorldDisplayable = worlds.get(initialWorld));

        // Initialize GUI widgets:
        this.widgets = new PriorityQueue<>();
        CompositeDisplayable playerStatus = new CompositeDisplayable(new Point(16, 16), 1);
        Entity player = game.getPlayer();
        playerStatus.add(new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(128, 164, Color.WHITE), -1));
        playerStatus.add(new StringDisplayable(new Point(4, 16), "Player Status:", Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 32), () -> "Health: " + player.getCurrHealth() + " / " + player.getMaxHealth(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 48), () -> "Mana:   " + player.getCurMana() + " / " + player.getMaxMana(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 64), () -> "Exp:    " + player.getCurXP(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 80), () -> "Level:  " + player.getCurLevel(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 96), () -> "Dir:    " + player.getMovementDirection(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 112),() -> "Vis:    " + player.getVisibilityRadius(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 128),() -> "Cncl:   " + player.getConcealment(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 144),() -> "Speed:  " + player.getBaseMoveSpeed(), Color.BLACK, 1));
        playerStatus.add(new StringDisplayable(new Point(4, 160),() -> "Loc: " + game.getCoordinate(player), Color.BLACK, 1));
        widgets.add(playerStatus);
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
        // Draw generic Displayables based on camera position and zoom:
        AffineTransform t = g2d.getTransform();
        g2d.translate(-camera.x, -camera.y);
        g2d.scale(zoom, zoom);
        super.draw(g2d);

        // Draw GUI widgets in the same place regardless of camera zoom or position:
        g2d.setTransform(t);
        widgets.forEach(d -> d.draw(g2d));
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

    public void centerOnPlayer()
    {
        Point playerTileOrigin = game.getCoordinate(game.getPlayer()).toPixelPt();
        snapCamera(playerTileOrigin.x - size.width/2 + 32, playerTileOrigin.y - size.height/2 + 32 + 32);
    }

    public void translateCamera(int dx, int dy) { camera.translate(dx, dy); }
    public void snapCamera(int x, int y) { camera.setLocation(x, y); }
    public void scaleZoom(double dz) { zoom *= dz; }
    public void setZoom(double zoom) { this.zoom = zoom; }
}
