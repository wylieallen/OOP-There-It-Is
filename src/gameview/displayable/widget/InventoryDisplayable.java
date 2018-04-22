package gameview.displayable.widget;

import entity.entitymodel.Entity;
import entity.entitymodel.EquipSlot;
import entity.entitymodel.Equipment;
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

    public InventoryDisplayable(Point origin, Entity entity)
    {
        super(origin, 1);
        this.entity = entity;
        update();
    }

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
        super.update();
    }

}
