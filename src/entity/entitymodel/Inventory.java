package entity.entitymodel;

import items.takeableitems.TakeableItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */
public class Inventory {

    private final int maxSize = 15;

    private List <TakeableItem> items;

    public Inventory (List<TakeableItem> items) {
        this.items = items;
    }

    public Inventory () {
        this.items = new ArrayList<>();
    }

    public boolean add (TakeableItem takeable) {

        if (items.size() < 15) {
            items.add(takeable);
            return true;
        }

        return false;
    }

    public void remove (TakeableItem takeable) {
        items.remove(takeable);
    }

    public TakeableItem select (int index) {
        return items.get(index);
    }

}
