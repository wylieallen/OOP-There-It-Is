package entity.entitymodel;

import items.takeableitems.TakeableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public TakeableItem getRandomItem () {
        Random rand = new Random();

        if (hasItems()) {
            return items.get(rand.nextInt(items.size()));
        }

        return null;
    }

    private boolean hasItems () { return items.size() > 0; }

    public TakeableItem select (int index) {
        if (index < items.size()) {
            return items.get(index);
        }

        System.out.println("Cant use Item index of " + index);
        assert false;

        return null;
    }

    public TakeableItem pickPocket(){
        Random random = new Random();
        if (!items.isEmpty()) {
            int randIndex = random.nextInt(items.size());
            TakeableItem toReturn = items.get(randIndex);
            items.remove(randIndex);
            return toReturn;
        } else {
            return null;
        }
    }

    public boolean contains(TakeableItem item) {
        for(TakeableItem i: items) {
            if(item == i)
                return true;
        }
        return false;
    }

}
