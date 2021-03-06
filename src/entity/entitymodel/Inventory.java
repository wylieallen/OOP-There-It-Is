package entity.entitymodel;

import gameobject.GameObject;
import items.takeableitems.TakeableItem;
import savingloading.Visitable;
import savingloading.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private boolean hasItems () { return items.size() > 0; }

    public int getSize () { return items.size(); }

    public TakeableItem select (int index) {
        if (index < items.size()) {
            return items.get(index);
        }

        System.out.println("Cant use Item index of " + index);

        return null;
    }

    public TakeableItem getRandomItem () {
        Random rand = new Random();

        if (hasItems()) {
            TakeableItem item = items.get(rand.nextInt(items.size()));
            items.remove(item);
            return item;
        }

        return null;
    }

    public TakeableItem pickPocket(){
        TakeableItem item = getRandomItem();
        return item;
    }

    public String getRandomItemName() {
        TakeableItem item = getRandomItem();
        if(item != null)
            return item.getName();
        else
            return "";
    }

    public boolean contains(TakeableItem item) {
        for(TakeableItem i: items) {
            if(item == i)
                return true;
        }
        return false;
    }

    public boolean has(GameObject o) {
        return items.contains(o);
    }

    public List<TakeableItem> getItems() {
        return items;
    }

    @Override
    public void accept(Visitor v) {
        v.visitInventory(this);
    }
}
