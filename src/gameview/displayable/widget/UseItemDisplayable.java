package gameview.displayable.widget;

import entity.entitymodel.Entity;
import guiframework.displayable.CompositeDisplayable;

import java.awt.*;

/**
 * Created by dontf on 4/22/2018.
 */
public class UseItemDisplayable extends CompositeDisplayable {

    InventoryDisplayable invDisp;
    Entity entity;
    int cursorIndex;
    
    public UseItemDisplayable(Point origin, Entity entity, InventoryDisplayable invDisp) {
        super(origin, 1);
        this.invDisp = invDisp;
        cursorIndex = invDisp.getCursorIndex();
        this.entity = entity;
        update();
    }
    
    @Override
    public void update () {
        if (!disabled ())
            invDisp.update();
    }

    public void adjustCursorIndex(int i) {
        int maxIndex = entity.getInventorySize();
        cursorIndex += i;

        if (cursorIndex == maxIndex) cursorIndex = 0;
        if (cursorIndex < 0) cursorIndex = maxIndex - 1;

        invDisp.setCursorIndex(cursorIndex);
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void disable() {
        cursorIndex = -1;
        invDisp.setCursorIndex(cursorIndex);
    }

    public void enable() {
        cursorIndex = 0;
        invDisp.setCursorIndex(0);
    }

    private boolean disabled () {
        return cursorIndex == -1;
    }
}
