package entity.entitymodel;

import items.takeableitems.TakeableItem;
import savingloading.Visitable;
import savingloading.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dontf on 4/13/2018.
 */
public class Inventory implements Visitable{

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

    public TakeableItem pickPocket(){
        if (!items.isEmpty())
            return items.get(0);
        else
            return null;
    }

    public List<TakeableItem> getItems() {
        return items;
    }

    @Override
    public void accept(Visitor v) {
        v.visitInventory(this);
    }
}
