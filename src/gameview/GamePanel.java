package gameview;

import gameview.util.ImageMaker;
import guiframework.DisplayPanel;
import guiframework.displayable.ColoredRectDisplayable;
import guiframework.displayable.ImageDisplayable;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.tile.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GamePanel extends DisplayPanel
{
    private GameDisplayState gameDisplayState;

    public GamePanel()
    {
        super();
        super.setDisplayState(gameDisplayState = new GameDisplayState());
        Tile tile = new OverWorldTile();
        tile.addMLC(Terrain.GRASS);
        for(Direction direction : Direction.values())
        {
            Tile newTile = new OverWorldTile();
            newTile.addMLC(Terrain.WATER);
            gameDisplayState.add(newTile, new Point(128 + direction.getPixelX(), 128 + direction.getPixelY()));
        }
        gameDisplayState.add(tile, new Point(128, 128));
    }
}
