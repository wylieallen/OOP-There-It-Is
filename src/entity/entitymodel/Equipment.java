package entity.entitymodel;

import gameobject.GameObject;
import items.takeableitems.ConsumableItem;
import items.takeableitems.TakeableItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import savingloading.Visitable;
import savingloading.Visitor;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.util.*;

/**
 * Created by dontf on 4/13/2018.
 */
public class Equipment implements Visitable {

    private final int defaultWeaponsSize = 5;

    private Map <EquipSlot, WearableItem> wearables;
    private WeaponItem[] weapons;
    private int maxSize;
    private Inventory inventory;
    private Entity entity;
    private List<SpawnObserver> spawnObservers;

    public Equipment(int maxSize, Inventory inventory, Entity entity) {
        this.maxSize = maxSize;
        this.inventory = inventory;
        this.entity = entity;
        this.wearables = new HashMap<>();
        this.weapons = new WeaponItem[defaultWeaponsSize];
        this.spawnObservers = new ArrayList<>();
    }

    public Equipment(Map<EquipSlot,
                     WearableItem> wearables,
                     WeaponItem[] weapons,
                     int maxSize,
                     Inventory inventory,
                     Entity entity,
                     List<SpawnObserver> spawnObservers)
    {
        this.wearables = wearables;
        this.weapons = weapons;
        this.maxSize = maxSize;
        this.inventory = inventory;
        this.entity = entity;
        this.spawnObservers = spawnObservers;
    }

    public void add (WearableItem wearable) {

        // Must remove from inventory first as to assert that there is room to add, else the item is lost forever!!!
        inventory.remove(wearable);

        WearableItem current = wearables.getOrDefault(wearable.getEquipType(), null);
        if (current != null) {
            this.remove(current);
            current.applyEffect(entity);
        }

        wearables.put(wearable.getEquipType(), wearable);
        wearable.applyEffect(entity);

    }

    public void add (WeaponItem weapon) {

        inventory.remove(weapon);

        for (int i = 0; i < weapons.length; ++i) {
            if (weapons[i] == null) {
                weapons [i] = weapon;
                return;
            }
        }

        this.remove(weapons [weapons.length - 1]);
        weapons [weapons.length - 1] = weapon;
        weapon.setSpawnObservers(spawnObservers);

    }

    public void consume (ConsumableItem consumable) {
        inventory.remove(consumable);
        consumable.applyEffect(entity);
    }

    public void remove (WearableItem wearable) {
        inventory.add(wearable);
        wearables.remove(wearable.getEquipType());
    }

    public void remove (WeaponItem weapon) {
        inventory.add(weapon);

        for (int i = 0; i < weapons.length; ++i) {
            if (weapons [i] == weapon) {
                weapons [i] = null;
            }
        }

    }

    public void select (int indexOfInventory) {
       TakeableItem takeable = inventory.select(indexOfInventory);
       takeable.activate(this);
    }

    public void useWeaponItem (int index, Coordinate point) {

        if (index < weapons.length && weapons [index] != null) {
            weapons [index].attack(entity, point);
        } else if (index >= weapons.length) {
            System.out.println("Cannot use weapon index of " + index);
            assert false;
        }

    }

    // just converted it so external stuff doesn't depend on the internal representation
    public List<WeaponItem> getWeapons() {
        return new ArrayList<WeaponItem>(Arrays.asList(weapons));
    }

    public Map<EquipSlot, WearableItem> getWearables(){
        return wearables;
    }

    @Override
    public void accept(Visitor v) {
        v.visitEquipment(this);
    }
    public boolean has(GameObject o) {
        if(wearables.values().contains(o)) {
            return true;
        } else if (inventory.has(o)) {
            return true;
        }

        for(WeaponItem weapon : weapons) {
            if(weapon == o) {
                return true;
            }
        }

        return false;
    }

    public void updateSpawnObservers(SpawnObserver oldObserver, SpawnObserver newObserver) {
        spawnObservers.remove(oldObserver);
        spawnObservers.add(newObserver);

        for(WeaponItem weapon: weapons) {
            weapon.deregisterObserver(oldObserver);
            weapon.registerObserver(newObserver);
        }
    }

}
