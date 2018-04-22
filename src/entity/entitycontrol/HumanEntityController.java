package entity.entitycontrol;

import entity.entitycontrol.controllerActions.*;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import gameview.GamePanel;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitor;
import utilities.Coordinate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class HumanEntityController extends EntityController implements ControllerActionVisitor
{

    private GamePanel view;

    // KeyListener Sets:
    private Set<KeyListener> freeMoveKeyListeners;
    private Set<KeyListener> inventoryManagementKeyListeners;
    // todo: entity interaction may take a little more work since we can't pre-initialize the actee's keylisteners
    private Set<KeyListener> entityInteractionKeyListeners;
    private Set<KeyListener> shoppingKeyListeners;
    private Set<KeyListener> levelUpKeyListeners;

    // integer KeyCodes (modify these to implement rebinding)
    // todo: enforce correctness with getter methods?
    private int attackKeyCode = KeyEvent.VK_SPACE;
    private int bindWoundsKeyCode = KeyEvent.VK_B;
    private int creepKeyCode = KeyEvent.VK_CONTROL;
    private int dismountKeyCode = KeyEvent.VK_EQUALS;
    private int observeKeyCode = KeyEvent.VK_O;
    private int manageInventoryKeyCode = KeyEvent.VK_I;

    private Map<Direction, Integer> directionalMoveKeyCodes;
    private Map<Direction, Integer> altDirectionalMoveKeyCodes;

    private int moveKeyCode = KeyEvent.VK_SHIFT;
    // todo: finish adding more keycodes

    // Inventory Menu keycodes:
    private int useInventoryItemKeyCode = KeyEvent.VK_ENTER;

    public HumanEntityController(Entity entity, Equipment equipment, Coordinate entityLocation, GamePanel view) {
        super(entity, equipment, entityLocation, new ArrayList<>());
        this.view = view;

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

        if(view != null) {
            view.setFocusable(true);
            view.requestFocus();
        }

        initializeFreeMove(entity);
        initializeInventoryManagement(entity);
        initializeEntityInteraction(entity);
        initializeShopping(entity);
        initializeLevelUp(entity);

        notifyFreeMove(entity);
    }

    private void initializeLevelUp(Entity entity)
    {
        levelUpKeyListeners = new HashSet<>();
    }

    private void initializeShopping(Entity entity)
    {
        shoppingKeyListeners = new HashSet<>();
    }

    private void initializeEntityInteraction(Entity entity)
    {
        entityInteractionKeyListeners = new HashSet<>();

        //todo
    }

    private void initializeFreeMove(Entity entity)
    {
        freeMoveKeyListeners = new HashSet<>();

        // todo: maybe move this into a separate "default freemove key listeners" structure
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == manageInventoryKeyCode)
                    notifyInventoryManagment(entity);
            }
        });

        for(Direction d : Direction.values())
        {
            if(d != Direction.NULL)
                addAction(new DirectionalMoveAction(entity, d));
        }

        addAction(new BindWoundsAction(entity));
        addAction(new ObserveAction(entity));
    }

    public void initializeInventoryManagement(Entity entity)
    {
        inventoryManagementKeyListeners = new HashSet<>();

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
               if(e.getKeyCode() == manageInventoryKeyCode)
                   notifyFreeMove(entity);
           }
        });

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
               if(e.getKeyCode() == directionalMoveKeyCodes.get(Direction.N))
                   view.decrementInventoryDisplayableIndex();
           }
        });

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == directionalMoveKeyCodes.get(Direction.S))
                    view.incrementInventoryDisplayableIndex();
            }
        });

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
               if(e.getKeyCode() == useInventoryItemKeyCode)
               {
                   // todo: use item in inventory
               }

           }
        });
    }

    public void setControllerActions(Collection<ControllerAction> actions){
        super.setControllerActions(actions);

        if(view != null) {
            view.clearKeyListeners();
            for(ControllerAction action : actions)
            {
                action.accept(this);
            }
        }

    }

    @Override
    public void addAction(ControllerAction action) {
        super.addAction(action);
        if(view != null) {
            action.accept(this);
        }
    }


    @Override
    protected void processController() {
        //TODO
    }

    @Override
    public void interact(EntityController interacter) {
        //TODO
    }

    @Override
    public void notifyFreeMove(Entity e) {
        //TODO
        if(view != null) {
            view.clearKeyListeners();
            for (KeyListener k : freeMoveKeyListeners) {
                view.addKeyListener(k);
            }
        }
    }

    @Override
    public void notifyInventoryManagment(Entity e) {
        //TODO
        if(view != null) {
            for (KeyListener k : freeMoveKeyListeners) {
                view.removeKeyListener(k);
            }

            for (KeyListener k : inventoryManagementKeyListeners) {
                view.addKeyListener(k);
            }
            // Tell GamePanel to reinitialize KeyListeners
            // Tell GameDisplayState to remove temporary substate Displayables
            // Tell GameDisplayState to add Inventory Cursor to temporary substate Displayables
        }
    }

    public void visitAttackAction(AttackAction a)
    {
        freeMoveKeyListeners.add(new KeyAdapter()
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
        freeMoveKeyListeners.add(new KeyAdapter()
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
        freeMoveKeyListeners.add(new KeyAdapter()
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
        System.out.println("Visited dirmove action: " + a.getDirection());
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                //System.out.println("Got key press " + e.getKeyChar());
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
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == observeKeyCode)
                {
                    a.activate();
                }
            }
        });
    }

    public void visitSetDirectionAction(SetDirectionAction a)
    {

    }

    public void visitDismountAction (DismountAction a) {
        // todo: maybe there should be a separate in-vehicle input state instead of lumping htis into freemove
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == dismountKeyCode)
                {
                    a.activate();
                }
            }
        });
    }

    @Override
    public void notifyInteraction(Entity player, Entity interactee) {
        //TODO
    }

    @Override
    public void notifyShopping(Entity trader1, Entity trader2) {
        //TODO
    }

    @Override
    public void notifyLevelUp(Entity e) {
        //TODO
    }

    @Override
    public void notifyMainMenu(Entity e) {
        //TODO
    }

    @Override
    public void updateMap (Map<Coordinate, Tile> map) {
        update(map);
    }

    @Override
    public void enrage(Entity e) {}

    @Override
    public void pacify() {}

    @Override
    public void accept(Visitor v) {
        v.visitHumanEntityController(this);
    }
}
