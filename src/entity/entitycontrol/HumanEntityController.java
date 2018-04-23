package entity.entitycontrol;

import entity.entitycontrol.controllerActions.*;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.EntityInteraction;
import gameview.GamePanel;
import items.takeableitems.TakeableItem;
import items.takeableitems.WearableItem;
import maps.tile.Direction;
import maps.tile.Tile;
import savingloading.Visitor;
import skills.SkillType;
import spawning.SpawnObservable;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class HumanEntityController extends EntityController implements ControllerActionVisitor
{
    private Collection<SpawnObservable> spawnObservableActions;

    private GamePanel view;
    private Set <KeyListener> activeListeners;

    // KeyListener Sets:
    private Set<KeyListener> freeMoveKeyListeners;
    private Set<KeyListener> inventoryManagementKeyListeners;
    private Set<KeyListener> useItemKeyListeners;
    private Set<KeyListener> entityInteractionKeyListeners;
    private Set<KeyListener> controlConfigKeyListeners;
    private List <EntityInteraction> listOfInteractions;
    private Entity currentInteractee;

    private Set<KeyListener> shoppingKeyListeners;
    private Set<KeyListener> levelUpKeyListeners;

    public int getUseInventoryItemKeyCode() { return useInventoryItemKeyCode; }
    public int getSelectInteractionKeyCode() { return selectInteractionKeyCode; }
    public Map<Direction, KeyRole> getDirectionalMoveKeyRoles() { return directionalMoveKeyRoles; }
    public Map<Integer, KeyRole> getWeaponSlotKeyRoles() { return weaponSlotKeyRoles; }

    public void setUseInventoryItemKeyCode(int useInventoryItemKeyCode) { this.useInventoryItemKeyCode = useInventoryItemKeyCode; }
    public void setSelectInteractionKeyCode(int selectInteractionKeyCode) { this.selectInteractionKeyCode = selectInteractionKeyCode; }

    private Map<Direction, KeyRole> directionalMoveKeyRoles;
    private Map<Integer, KeyRole> weaponSlotKeyRoles;
    // Inventory Menu keycodes:
    private int useInventoryItemKeyCode = KeyEvent.VK_ENTER;
    private int selectInteractionKeyCode = KeyEvent.VK_ENTER;


    // TODO: give entity bonuses on enrage and anti-bonuses on pacify
    private boolean isAggroed;

    public HumanEntityController(Entity entity, Equipment equipment, Coordinate entityLocation, GamePanel view) {
        super(entity, equipment, entityLocation, new ArrayList<>());
        this.spawnObservableActions = new ArrayList<>();
        this.view = view;

        isAggroed = false;

        directionalMoveKeyRoles = new HashMap<>();
        directionalMoveKeyRoles.put(Direction.N, KeyRole.MOVE_N);
        directionalMoveKeyRoles.put(Direction.NE, KeyRole.MOVE_NE);
        directionalMoveKeyRoles.put(Direction.NW, KeyRole.MOVE_NW);
        directionalMoveKeyRoles.put(Direction.S, KeyRole.MOVE_S);
        directionalMoveKeyRoles.put(Direction.SE, KeyRole.MOVE_SE);
        directionalMoveKeyRoles.put(Direction.SW, KeyRole.MOVE_SW);


        weaponSlotKeyRoles = new HashMap<>();
        weaponSlotKeyRoles.put(0, KeyRole.ATTACK1);
        weaponSlotKeyRoles.put(1, KeyRole.ATTACK2);
        weaponSlotKeyRoles.put(2, KeyRole.ATTACK3);
        weaponSlotKeyRoles.put(3, KeyRole.ATTACK4);
        weaponSlotKeyRoles.put(4, KeyRole.ATTACK5);

        if(view != null) {
            view.setFocusable(true);
            view.requestFocus();
        }

        activeListeners = new HashSet<>();
        listOfInteractions = new ArrayList<>();

        initializeFreeMove(entity);
        initializeInventoryManagement(entity);
        initializeEntityInteraction(entity);
        initializeShopping(entity);
        initializeLevelUp(entity);
        initializeUseItem(entity);
        initializeConfigControls(entity);

        notifyFreeMove(entity);
    }

    private void initializeConfigControls(Entity entity)
    {
        controlConfigKeyListeners = new HashSet<>();

        // Config state keys are hardcoded to prevent gamebreaking scenarios
        // e.g., I unbound the keys for movement and actions...
        controlConfigKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                    view.decrementConfigDisplayableIndex();
            }
        });

        controlConfigKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    view.incrementConfigDisplayableIndex();
            }
        });

        controlConfigKeyListeners.add(new KeyAdapter()
        {
           @Override
           public void keyPressed(KeyEvent e)
           {
              if(e.getKeyCode() == KeyEvent.VK_LEFT)
                   view.togglePrimarySecondary();
           }
        });

        controlConfigKeyListeners.add(new KeyAdapter()
        {
           @Override
           public void keyPressed(KeyEvent e)
           {
               if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                   view.togglePrimarySecondary();
           }
        });

        controlConfigKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    KeyRole keyrole = view.getSelectedKeyRole();
                    boolean primarySelected = view.primaryKeyBindIsSelected();
                    view.addKeyListener(new KeyAdapter()
                    {
                       @Override
                       public void keyPressed(KeyEvent e)
                       {
                           if(primarySelected) keyrole.setPrimaryKeycode(e.getKeyCode());
                           else keyrole.setSecondaryKeycode(e.getKeyCode());
                           view.removeKeyListener(this);
                       }
                    });
                }
            }
        });

        controlConfigKeyListeners.add(new KeyAdapter()
        {
           @Override
           public void keyPressed(KeyEvent e)
           {
               if(KeyRole.MANAGE_CONTROLS.triggersOn(e.getKeyCode()))
               {
                   for(KeyListener k : controlConfigKeyListeners)
                       view.removeKeyListener(k);

                   view.disableConfigWidget();

                   notifyFreeMove(entity);
               }
           }
        });
    }

    private void initializeLevelUp(Entity entity)
    {
        levelUpKeyListeners = new HashSet<>();

        levelUpKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.N).triggersOn(e.getKeyCode()))
                    view.decrementLevelUpDisplayableIndex();
            }
        });

        levelUpKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.S).triggersOn(e.getKeyCode()))
                    view.incrementLevelUpDisplayableIndex();
            }
        });

        levelUpKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.MANAGE_CONTROLS.triggersOn(e.getKeyCode()))
                {
                    view.disableLevelUpDisplayable();
                    notifyFreeMove(entity);
                }

            }
        });

        levelUpKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == useInventoryItemKeyCode)
                {
                    // Determine selected skill
                    // Attempt to spend skillpoint if possible
                    int index = view.getLevelUpCursorIndex();
                    SkillType selectedSkill = SkillType.values()[index];
                    if(entity.getSkillLevel(selectedSkill) > -1 && entity.getUnusedSkillPoints() > 0)
                    {
                        entity.increaseSkillLevel(selectedSkill, 1);
                        entity.decreaseSkillPoints(1);
                    }
                }
            }
        });
    }

    private void initializeShopping(Entity entity)
    {
        shoppingKeyListeners = new HashSet<>();

        shoppingKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.N).triggersOn(e.getKeyCode()))
                    view.decrementTradeIndex();
            }
        });

        shoppingKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.S).triggersOn(e.getKeyCode()))
                    view.incrementTradeIndex();
            }
        });

        shoppingKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                int code = e.getKeyCode();
                if(KeyRole.MOVE_NE.triggersOn(code) || KeyRole.MOVE_NW.triggersOn(code)
                        || KeyRole.MOVE_SE.triggersOn(code) || KeyRole.MOVE_SW.triggersOn(code))
                {
                    view.toggleActiveTradeInventory();
                }
            }
        });

        shoppingKeyListeners.add(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == useInventoryItemKeyCode)
                {
                    // Determine active inventory (player or NPC?)
                    // Determine selected item based on cursor
                    // Check if possible to complete the transaction
                        // i.e., if buyer has gold and inventory space
                    // If so, perform the transaction
                    Entity buyer = view.getBuyer();
                    Entity seller = view.getSeller();
                    TakeableItem item = seller.getItem(view.getTradingCursorIndex());
                    double price = 10.0 *((double)seller.getSkillLevel(SkillType.BARGAIN) / (double) buyer.getSkillLevel(SkillType.BARGAIN));
                    if(item != null && buyer.getGold() > price && buyer.addToInventory(item))
                    {
                        seller.removeFromInventory(item);
                        buyer.decreaseGold(price);
                        seller.increaseGold(price);
                    }
                }
            }
        });

        shoppingKeyListeners.add(new KeyAdapter(){
           @Override
           public void keyPressed(KeyEvent e)
           {
               if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
               {
                   for(KeyListener k : shoppingKeyListeners)
                   {
                       view.removeKeyListener(k);
                   }
                   view.disableTradingDisplayables();
                   notifyFreeMove(entity);
               }
           }
        });
    }

    private void initializeEntityInteraction(Entity entity)
    {
        entityInteractionKeyListeners = new HashSet<>();

        entityInteractionKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.MOVE_N.triggersOn(e.getKeyCode()))
                    view.decrementInteractionDisplayableIndex();
            }
        });

        entityInteractionKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.S).triggersOn(e.getKeyCode()))
                    view.incrementInteractionDisplayableIndex();
            }
        });

        entityInteractionKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == selectInteractionKeyCode)
                {
                    int cursorIndex = view.getInteractionCursorIndex();
                    if (listOfInteractions.size() > 0 && cursorIndex < listOfInteractions.size() && currentInteractee != null) {
                        EntityInteraction interaction = listOfInteractions.get(cursorIndex);
                        interaction.interact(getEntity(), getCurrentInteractee());
                    } else if (currentInteractee == null){
                        System.out.println("Interactee is null");
                        notifyFreeMove(getControlledEntity());
                    } else if (cursorIndex >= listOfInteractions.size()){
                        System.out.println("bad index on interactions");
                        notifyFreeMove(getControlledEntity());
                    }

                }
            }
        });
    }

    private void initializeFreeMove(Entity entity)
    {
        freeMoveKeyListeners = new HashSet<>();

        // todo: maybe move this into a separate "default freemove key listeners" structure
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.MANAGE_INVENTORY.triggersOn(e.getKeyCode()))
                    notifyInventoryManagment(entity);
            }
        });

        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.MANAGE_SKILLS.triggersOn(e.getKeyCode()))
                    notifyLevelUp(entity);
            }
        });

        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.MANAGE_CONTROLS.triggersOn(e.getKeyCode()))
                    notifyConfig(entity);
            }
        });

        for(Direction d : Direction.values())
        {
            if(d != Direction.NULL)
                addAction(new DirectionalMoveAction(entity, d));
        }

        if(getEquipment() != null) {
            for (int i = 0; i < getEquipment().getNumWeaponSlots(); ++i) {
                addAction(new AttackAction(this, getEquipment(), i));
            }
        }

        addAction(new BindWoundsAction(entity));

        if(entity.containsSkill(SkillType.CREEP)){
            addAction(new CreepAction(entity, false, entity.getConcealment(), 0));
        }

        ObserveAction obs = new ObserveAction(getControlledEntity());
        obs.setController(this);
        addAction(obs);

        if(entity.containsSkill(SkillType.DETECTANDREMOVETRAP)){
            addAction(new SearchAction(entity, false, 0));
        }

        addAction(new DismountAction(this));
    }

    private void notifyConfig(Entity entity)
    {
        for(KeyListener k : freeMoveKeyListeners)
            view.removeKeyListener(k);

        view.enableConfigWidget();

        for(KeyListener k : controlConfigKeyListeners)
            view.addKeyListener(k);
    }

    public void initializeInventoryManagement(Entity entity)
    {
        inventoryManagementKeyListeners = new HashSet<>();

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
               if(KeyRole.MANAGE_INVENTORY.triggersOn(e.getKeyCode()))
                   notifyFreeMove(entity);
           }
        });

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
               if(directionalMoveKeyRoles.get(Direction.N).triggersOn(e.getKeyCode()))
                   view.decrementInventoryDisplayableIndex();
           }
        });

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.S).triggersOn(e.getKeyCode()))
                    view.incrementInventoryDisplayableIndex();
            }
        });

        inventoryManagementKeyListeners.add(new KeyAdapter()
        {
           public void keyPressed(KeyEvent e)
           {
               if(e.getKeyCode() == useInventoryItemKeyCode)
               {
                   int cursorIndex = view.getInventoryCursorIndex();
                   Inventory inventory = entity.getInventory();
                   if(cursorIndex >= inventory.getItems().size())
                   {
                       cursorIndex -= inventory.getItems().size();
                       if(cursorIndex >= getEquipment().getWearables().size())
                       {
                           cursorIndex -= getEquipment().getWearables().size();
                           getEquipment().getWeapons().get(cursorIndex).activate(getEquipment());
                       }
                       else
                       {
                           WearableItem[] wearables = new WearableItem[0];
                           wearables = getEquipment().getWearables().values().toArray(wearables);
                           wearables[cursorIndex].activate(getEquipment());
                       }
                   }
                   else
                   {
                       inventory.select(cursorIndex).activate(getEquipment());
                   }
               }
           }
        });
    }

    public void initializeUseItem (Entity e) {
        useItemKeyListeners = new HashSet<>();

        useItemKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.N).triggersOn(e.getKeyCode()))
                    view.decrementUseItemDisplayableIndex();
            }
        });

        useItemKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(directionalMoveKeyRoles.get(Direction.S).triggersOn(e.getKeyCode()))
                    view.incrementUseItemDisplayableIndex();
            }
        });

        useItemKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == useInventoryItemKeyCode)
                {
                    int cursorIndex = view.getUseItemCursorIndex();
                    if (getControlledEntity().getInventorySize() > 0 && cursorIndex < getControlledEntity().getInventorySize () && currentInteractee != null) {
                        TakeableItem use = getControlledEntity().getItem(cursorIndex);
                        getControlledEntity().removeFromInventory(use);
                        use.activate(currentInteractee.getController().getEquipment());
                    }
                    notifyFreeMove(getControlledEntity());
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

            refreshActiveList();
        }

    }

    @Override
    public void addAction(ControllerAction action) {
        super.addAction(action);
        if(view != null) {
            action.accept(this);
            refreshActiveList();
        }
    }

    private void refreshActiveList () {
        view.clearKeyListeners();

        for (KeyListener k : activeListeners) {
            view.addKeyListener(k);
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
            if(view.initialized())
            {
                view.disableInventoryCursor();
                view.disableInteraction();
                view.disableUseItem();
            }
            view.clearKeyListeners();
            for (KeyListener k : freeMoveKeyListeners) {
                view.addKeyListener(k);
            }
        }
        activeListeners = freeMoveKeyListeners;
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
            view.incrementInventoryDisplayableIndex();
        }

        activeListeners = inventoryManagementKeyListeners;
    }

    public void visitAttackAction(AttackAction a)
    {
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(weaponSlotKeyRoles.get(a.getWeaponSlot()).triggersOn(e.getKeyCode()))
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
                if(KeyRole.BIND_WOUNDS.triggersOn(e.getKeyCode()))
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
                if(KeyRole.TOGGLE_CREEP.triggersOn(e.getKeyCode()))
                {
                    a.activate();
                }
            }
        });
    }

    public void visitSearchAction(SearchAction a)
    {
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.TOGGLE_SEARCH.triggersOn(e.getKeyCode()))
                {
                    a.activate();
                }
            }
        });
    }

    public void visitDirectionalMoveAction(DirectionalMoveAction a)
    {
        //System.out.println("Visited dirmove action: " + a.getDirection());
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {

                //System.out.println("Got key press " + e.getKeyChar());
                Direction d = a.getDirection();
                if(directionalMoveKeyRoles.get(d).triggersOn(e.getKeyCode()))
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
                if(KeyRole.OBSERVE.triggersOn(e.getKeyCode()))
                {
                    a.activate();
                }
            }
        });

        spawnObservableActions.add(a);
    }

    public void visitSetDirectionAction(SetDirectionAction a)
    {

    }

    public void visitDismountAction (DismountAction a) {
        // todo: maybe there should be a separate in-vehicle input state instead of lumping this into freemove
        freeMoveKeyListeners.add(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(KeyRole.DISMOUNT.triggersOn(e.getKeyCode()))
                {
                    a.activate();
                }
            }
        });
    }

    @Override
    public void updateSpawnObservers(SpawnObserver oldObserver, SpawnObserver newObserver) {
        super.updateSpawnObservers(oldObserver, newObserver);
        for(SpawnObservable so: spawnObservableActions) {
            so.deregisterObserver(oldObserver);
            so.registerObserver(newObserver);
        }
    }

    @Override
    public void notifyInteraction(Entity player, Entity interactee) {

        currentInteractee = interactee;
        listOfInteractions = interactee.interact(player);

        for (KeyListener k : activeListeners) {
            view.removeKeyListener(k);
        }

        for (KeyListener k : entityInteractionKeyListeners) {
            view.addKeyListener(k);
        }

        activeListeners = entityInteractionKeyListeners;
        view.enableInteraction();

    }

    private Entity getCurrentInteractee () { return currentInteractee; }

    @Override
    public List<EntityInteraction> getInteractionList () { return listOfInteractions; }

    @Override
    public void notifyShopping(Entity trader) {
        //TODO set active list to shopping list
        if(view != null)
        {
            view.disableInteraction();
            for(KeyListener k : entityInteractionKeyListeners)
                view.removeKeyListener(k);

            view.enableTradingDisplayable(trader);
            for(KeyListener k : shoppingKeyListeners)
                view.addKeyListener(k);
        }
    }

    @Override
    public void notifyLevelUp(Entity e) {
        //TODO set active list to level up list
        if(view != null) {
            for (KeyListener k : freeMoveKeyListeners) {
                view.removeKeyListener(k);
            }

            for (KeyListener k : levelUpKeyListeners) {
                view.addKeyListener(k);
            }
            view.enableLevelUpDisplayable();
        }

        activeListeners = inventoryManagementKeyListeners;

    }

    @Override
    public void notifyMainMenu(Entity e) {
        //TODO set active list to main menu list
    }

    @Override
    public void notifyUseItem (Entity player, EntityController interactee) {
        currentInteractee = interactee.getControlledEntity();

        for (KeyListener k : activeListeners) {
            view.removeKeyListener(k);
        }

        for (KeyListener k : useItemKeyListeners) {
            view.addKeyListener(k);
        }

        activeListeners = useItemKeyListeners;
        view.disableInteraction();
        view.enableUseItem();
    }

    @Override
    public void updateMap (Map<Coordinate, Tile> map) {
        update(map);
    }

    @Override
    public void enrage(Entity e) {
        if (!isAggroed) {
            getControlledEntity().healEntity(10);
        }
        isAggroed = true;
    }

    @Override
    public void pacify() {
        isAggroed = false;
    }

    @Override
    public boolean isAggroed () {
        return isAggroed;
    }

    @Override
    public void accept(Visitor v) {
        v.visitHumanEntityController(this);
    }
}
