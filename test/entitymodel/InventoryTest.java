package entitymodel;

import entity.entitymodel.Inventory;
import items.takeableitems.QuestItem;
import items.takeableitems.TakeableItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */
public class InventoryTest {

    private static List <TakeableItem> items;
    private static Inventory inventory;

    @Before
    public void setUpInventory () {
        items = new ArrayList<>();
        inventory = new Inventory(items);

        for (int i = 0; i < 14; ++i) {
            items.add (new QuestItem("bob: " + i, true, i));
        }
    }

    @Test
    public void addItemsToNonFullInventoryTest () {
        TakeableItem takeable = new QuestItem("Betty", true, 162);

        Assert.assertTrue(inventory.add(takeable));
        Assert.assertTrue(items.contains(takeable));
    }

    @Test
    public void addItemToFullInventoryTest () {
        TakeableItem takeable = new QuestItem("Betty", true, 162);
        inventory.add(takeable);

        takeable = new QuestItem("Bert", true, 69);

        Assert.assertFalse(inventory.add(takeable));
        Assert.assertFalse(items.contains(takeable));
    }

    @Test
    public void removeItemTest () {
        TakeableItem takeable = inventory.select(0);
        inventory.remove(takeable);

        Assert.assertFalse(items.contains(takeable));
    }
}
