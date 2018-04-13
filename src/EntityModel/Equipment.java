package EnitityModel;

import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */
public class Equipment {

    private final int defaultWeaponsSize = 5;

    private Map <EquipSlot, WearableItem> wearables;
    private WeaponItem [] weapons;
    private int maxSize;
    private Inventory inventory;
    private Entity entity;

    public Equipment(int maxSize, Inventory inventory, Entity entity) {
        this.maxSize = maxSize;
        this.inventory = inventory;
        this.entity = entity;
        this.wearables = new Map <EquipSlot, WearableItem>();
        this.weapons = new WeaponItem[defaultWeaponsSize];
    }

    public Equipment(Map<EquipSlot, WearableItem> wearables,
                     WeaponItem[] weapons,
                     int maxSize,
                     Inventory inventory,
                     Entity entity)
    {
        this.wearables = wearables;
        this.weapons = weapons;
        this.maxSize = maxSize;
        this.inventory = inventory;
        this.entity = entity;
    }

    public void add (WearableItem wearable) {

    }

    public void add (WeaponItem weapon) {

    }

    public void consume (ConsumableItem consumable) {

    }

    public void remove (WearableItem wearable) {

    }

    public void remove (WeaponItem weapon) {

    }

    public void select (int indexOfInventory) {

    }

    public void useWeaponItem (int index, Coordinate point) {

    }

}
