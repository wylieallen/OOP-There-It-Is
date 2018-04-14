package gameview;

import gameview.util.ImageMaker;
import guiframework.DisplayPanel;
import guiframework.displayable.ColoredRectDisplayable;
import maps.movelegalitychecker.Terrain;

import java.awt.*;

public class GamePanel extends DisplayPanel
{
    private GameDisplayState gameDisplayState;

    public GamePanel()
    {
        super();
        super.setDisplayState(gameDisplayState = new GameDisplayState());
        gameDisplayState.add(new ColoredRectDisplayable(new Point(256 + 12, 256 + 12), 50, 50, 0, Color.RED));
        gameDisplayState.add(new ColoredRectDisplayable(new Point(256 + 25 + 12, 256 + 25 + 12), 50, 50, 1, Color.YELLOW));
        gameDisplayState.add(new ColoredRectDisplayable(new Point(256, 256), 100, 100, -1, Color.GREEN));
        gameDisplayState.add(ImageMaker.makeDefaultMap().get(Terrain.GRASS));
    }
}
