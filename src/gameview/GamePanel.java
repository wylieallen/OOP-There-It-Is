package gameview;

import entity.entitymodel.Entity;
import entity.entitymodel.MovementObserver;
import guiframework.DisplayPanel;
import maps.world.TransitionObserver;
import maps.world.World;
import savingloading.LoadingParser;
import utilities.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends DisplayPanel implements TransitionObserver, MovementObserver
{
    private boolean initialized = false;
    private GameDisplayState gameDisplayState;

    public GamePanel(Dimension size)
    {
        super(size);
        super.setDisplayState(gameDisplayState = new GameViewMaker().makeGameDisplayState(this, "Smasher"));
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

        super.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                gameDisplayState.setSize(getSize());
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
            //long startTime = System.currentTimeMillis();
            gameDisplayState.update();
            repaint();
            //long endTime = System.currentTimeMillis();
            //System.out.println("Time taken: " + (endTime - startTime));
            timer.restart();
        });

        timer.start();

        resetCamera();

        this.requestFocus();

        initialized = true;
    }

    public boolean initialized() { return initialized; }

    // todo: these Observer interfaces should probably just be in GameDisplayState
    @Override
    public void notifyMovement()
    {
        resetCamera();
    }

    @Override
    public void notifyTransition(Entity entity, World world, Coordinate p)
    {
        gameDisplayState.transitionWorld(world);
        resetCamera();
    }

    public void resetCamera()
    {
        gameDisplayState.centerOnPlayer();
        gameDisplayState.setZoom(1);
    }

    // Controller Action Visitor methods:

    public void clearKeyListeners()
    {
        for(KeyListener k : getKeyListeners())
        {
            removeKeyListener(k);
        }
    }

    public int getInventoryCursorIndex()
    {
        return gameDisplayState.getInventoryDisplayableIndex();
    }

    public void decrementInventoryDisplayableIndex()
    {
        gameDisplayState.decrementInventoryDisplayableIndex();
    }

    public void incrementInventoryDisplayableIndex()
    {
        gameDisplayState.incrementInventoryDisplayableIndex();
    }

    public void disableInventoryCursor()
    {
        gameDisplayState.disableInventoryCursor();
    }

    public void decrementLevelUpDisplayableIndex()
    {
        gameDisplayState.decrementLevelUpDisplayableIndex();
    }

    public void incrementLevelUpDisplayableIndex()
    {
        gameDisplayState.incrementLevelUpDisplayableIndex();
    }

    public void disableLevelUpDisplayable()
    {
        gameDisplayState.disableLevelUpDisplayable();
    }

    public void enableLevelUpDisplayable()
    {
        gameDisplayState.enableLevelUpDisplayable();
    }

    public int getLevelUpCursorIndex() { return gameDisplayState.getLevelUpDisplayableIndex(); }

    public int getInteractionCursorIndex()
    {
        return gameDisplayState.getInteractionDisplayableIndex();
    }

    public void decrementInteractionDisplayableIndex()
    {
        gameDisplayState.decrementInteractionDisplayableIndex();
    }

    public void incrementInteractionDisplayableIndex()
    {
        gameDisplayState.incrementInteractionDisplayableIndex();
    }

    public void disableInteraction()
    {
        gameDisplayState.disableInteraction();
    }

    public void enableInteraction () { gameDisplayState.enableInteraction(); }

    public int getUseItemCursorIndex()
    {
        return gameDisplayState.getUseItemDisplayableIndex();
    }

    public void decrementUseItemDisplayableIndex()
    {
        gameDisplayState.decrementUseItemDisplayableIndex();
    }

    public void incrementUseItemDisplayableIndex()
    {
        gameDisplayState.incrementUseItemDisplayableIndex();
    }

    public void disableUseItem()
    {
        gameDisplayState.disableUseItem();
    }

    public void enableUseItem () { gameDisplayState.enableUseItem(); }




    public void startNewGame(String className)
    {
        super.setDisplayState(gameDisplayState = new GameViewMaker().makeGameDisplayState(this,className));
        resetCamera();
    }

    public void loadGame()
    {
        try
        {
            LoadingParser parser = new LoadingParser();
            parser.loadGame("test", this);
            this.setDisplayState(gameDisplayState = parser.getGameDisplayState());
            resetCamera();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveGame()
    {
        gameDisplayState.saveGame();
    }

}
