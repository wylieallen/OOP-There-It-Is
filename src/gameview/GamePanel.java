package gameview;

import gameview.util.ImageMaker;
import guiframework.DisplayPanel;
import guiframework.displayable.ColoredRectDisplayable;
import guiframework.displayable.ImageDisplayable;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        super.setDisplayState(gameDisplayState = new GameViewMaker().makeGameDisplayState(this));
        /*
        Tile tile = new OverWorldTile();
        tile.addMLC(Terrain.GRASS);
        
        gameDisplayState.add(tile, new Point(128, 128));

        for(Direction direction : Direction.values())
        {
            Tile newTile = new OverWorldTile();
            newTile.addMLC(Terrain.WATER);
            gameDisplayState.add(newTile, new Point(128 + direction.getPixelX(), 128 + direction.getPixelY()));
        }
        */

        super.addMouseWheelListener(e -> {
            double clicks = e.getPreciseWheelRotation();
            if(clicks < 0)
            {
                gameDisplayState.scaleZoom(-1.25f * clicks);
            }
            else if (clicks > 0)
            {
                gameDisplayState.scaleZoom(0.8f * clicks);
            }
        });
        //gameDisplayState.adjustZoom(e -> e.getPreciseWheelRotation() / -10));

        super.addMouseMotionListener(new MouseAdapter()
        {
            int prevX, prevY;

            public void mouseEntered(MouseEvent e)
            {
                updatePrevs(e);
            }

            public void mouseMoved(MouseEvent e)
            {
                updatePrevs(e);
            }

           public void mouseDragged(MouseEvent e)
           {
                translateCamera(e);
                updatePrevs(e);
           }

           private void translateCamera(MouseEvent e)
           {
               int dx = e.getX() - prevX;
               int dy = e.getY() - prevY;

               gameDisplayState.translateCamera(-dx, -dy);
           }

           private void updatePrevs(MouseEvent e)
           {
               prevX = e.getX();
               prevY = e.getY();
           }
        });

        Timer timer = new Timer(17, e -> {});

        // Remove the dummy ActionListener from the constructor:
        for(ActionListener a : timer.getActionListeners())
        {
            timer.removeActionListener(a);
        }

        timer.addActionListener(e -> {
            timer.stop();
            gameDisplayState.update();
            repaint();
            timer.restart();
        });

        timer.start();
    }

    public void resetCamera()
    {
        gameDisplayState.snapCamera(0, 0);
        gameDisplayState.setZoom(1);
    }
}
