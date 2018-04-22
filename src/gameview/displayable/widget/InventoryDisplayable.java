package gameview.displayable.widget;

import entity.entitymodel.Entity;
import entity.entitymodel.EquipSlot;
import entity.entitymodel.Equipment;
import entity.entitymodel.Inventory;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;
import items.takeableitems.TakeableItem;

import java.awt.*;

public class InventoryDisplayable extends CompositeDisplayable
{
    private Displayable background = new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(128 + 32, 384, Color.WHITE), -1);
    private Entity entity;
    private int cursorIndex;

    public InventoryDisplayable(Point origin, Entity entity)
    {
        super(origin, 1);
        this.entity = entity;
        update();
        cursorIndex = -1;
    }

    public void adjustCursorIndex(int delta)
    {
        int maxIndex = entity.getInventory().getItems().size() + entity.getController().getEquipment().getWearables().size() - 1;
        cursorIndex += delta;
        if(cursorIndex < 0)
        {
            cursorIndex = maxIndex;
        }
        else if (cursorIndex > maxIndex)
        {
            cursorIndex = 0;
        }
    }
    public void setCursorIndex(int cursorIndex)
    {
        this.cursorIndex = cursorIndex;
    }
    public int getCursorIndex() { return cursorIndex; }

    @Override
    public void update()
    {
        super.clear();
        add(background);
        add(new StringDisplayable(new Point(4, 16), "Player Inventory:", Color.BLACK, 1));

        int numInventorySlots = entity.getInventory().getItems().size();
        for(int i = 0; i < numInventorySlots; i++)
        {
            int index = i;
            TakeableItem item = entity.getItem(index);
            String itemName = (item == null) ? "EMPTY" : item.getName();
            add(new StringDisplayable(new Point(4, 32 + (i * 16)), () -> "Slot " + index + ": " + itemName, Color.BLACK, 1));
        }
        add(new StringDisplayable(new Point(4, 48 + (numInventorySlots * 16)), "Player Equipment:", Color.BLACK, 1));
        Equipment equipment = entity.getController().getEquipment();
        int modifier = 0;
        for(EquipSlot equipSlot : equipment.getWearables().keySet())
        {
            add(new StringDisplayable(new Point(4, 64 + (numInventorySlots * 16) + (modifier * 16)),
                    () -> "" + equipSlot + ": " + equipment.getWearables().get(equipSlot).getName(),
                    Color.BLACK, 1));
            ++modifier;
        }
        if(cursorIndex > -1)
        {
            add(new ImageDisplayable(calculateCursorPoint(), ImageMaker.makeRightPointingTriangle(), 1000));
        }
        super.update();
    }

    private Point calculateCursorPoint()
    {
        int x = -4, y;

        Inventory inventory = entity.getInventory();
        int inventorySize = inventory.getItems().size();

        if(cursorIndex >= inventorySize)
        {
            y = 32 + 16 + (cursorIndex - inventorySize) * 16 + ((inventorySize) * 16);
        }
        else
        {
            y = cursorIndex * 16 + 16;
        }
        y += 8;

        return new Point(x, y);
    }


}
