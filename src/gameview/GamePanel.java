package gameview;

import entity.entitycontrol.controllerActions.*;
import entity.entitymodel.Entity;
import gameview.util.ImageMaker;
import guiframework.DisplayPanel;
import guiframework.displayable.ColoredRectDisplayable;
import guiframework.displayable.ImageDisplayable;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import maps.world.TransitionObserver;
import maps.world.World;
import utilities.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GamePanel extends DisplayPanel implements ControllerActionVisitor, TransitionObserver
{
    private GameDisplayState gameDisplayState;

    private int attackKeyCode = KeyEvent.VK_SPACE;
    private int bindWoundsKeyCode = KeyEvent.VK_B;
    private int creepKeyCode = KeyEvent.VK_CONTROL;

    private Map<Direction, Integer> directionalMoveKeyCodes;
    private Map<Direction, Integer> altDirectionalMoveKeyCodes;

    private int moveKeyCode = KeyEvent.VK_SHIFT;
    // todo: finish adding more keycodes


    public GamePanel(Dimension size)
    {
        super(size);
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
        directionalMoveKeyCodes = new HashMap<>();
        directionalMoveKeyCodes.put(Direction.N, KeyEvent.VK_W);
        directionalMoveKeyCodes.put(Direction.NE, KeyEvent.VK_E);
        directionalMoveKeyCodes.put(Direction.NW, KeyEvent.VK_Q);
        directionalMoveKeyCodes.put(Direction.S, KeyEvent.VK_S);
        directionalMoveKeyCodes.put(Direction.SE, KeyEvent.VK_D);
        directionalMoveKeyCodes.put(Direction.SW, KeyEvent.VK_A);

        altDirectionalMoveKeyCodes = new HashMap<>();
        altDirectionalMoveKeyCodes.put(Direction.N, KeyEvent.VK_UP);
        altDirectionalMoveKeyCodes.put(Direction.NE, KeyEvent.VK_PAGE_DOWN);
        altDirectionalMoveKeyCodes.put(Direction.NW, KeyEvent.VK_PAGE_UP);
        altDirectionalMoveKeyCodes.put(Direction.S, KeyEvent.VK_DOWN);
        altDirectionalMoveKeyCodes.put(Direction.SE, KeyEvent.VK_RIGHT);
        altDirectionalMoveKeyCodes.put(Direction.SW, KeyEvent.VK_LEFT);


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
            gameDisplayState.update();
            repaint();
            timer.restart();
        });

        timer.start();

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

    public void visitAttackAction(AttackAction a)
    {
        addKeyListener(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
                if(e.getKeyCode() == attackKeyCode)
                {
                    a.activate();
                }
           }
        });
    }

    public void visitBindWoundsAction(BindWoundsAction a)
    {
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == bindWoundsKeyCode)
                {
                    a.activate();
                }
            }
        });
    }

    public void visitCreepAction(CreepAction a)
    {
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == creepKeyCode)
                {
                    a.activate();
                }
            }
        });
    }

    public void visitDirectionalMoveAction(DirectionalMoveAction a)
    {
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                Direction d = a.getDirection();
                int movecode = directionalMoveKeyCodes.get(d);
                if(e.getKeyCode() == movecode || e.getKeyCode() == altDirectionalMoveKeyCodes.get(d))
                {
                    a.activate();
                }
            }
        });
    }

    public void visitMoveAction(MoveAction a)
    {

    }

    public void visitObserveAction(ObserveAction a)
    {

    }

    public void visitSetDirectionAction(SetDirectionAction a)
    {

    }
}
