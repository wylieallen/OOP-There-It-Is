package gameview.displayable.widget;

import entity.entitycontrol.EntityController;
import entity.entitymodel.interactions.EntityInteraction;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.util.List;
import java.awt.*;

/**
 * Created by dontf on 4/22/2018.
 */
public class InteractionDisplayable extends CompositeDisplayable {

    private Displayable background = new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(160, 192, Color.WHITE), -1);
    int cursorIndex;
    EntityController controller;

    public InteractionDisplayable (Point origin, EntityController controller) {
        super(origin, 1);
        this.controller = controller;
        cursorIndex = -1;
    }

    public void adjustCursorIndex(int i) {
        cursorIndex += i;

        int maxIndex = controller.getInteractionList().size();

        if (cursorIndex == maxIndex) cursorIndex = 0;
        if (cursorIndex < 0) cursorIndex = maxIndex - 1;
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void enable () {
        cursorIndex = 0;
    }

    public void disable () {
        cursorIndex = -1;
    }

    @Override
    public void update () {

        super.clear();

        if (!isDisabled()) {
            add(background);
            setUpDisplay();
        }

        super.update();
    }

    private void setUpDisplay () {
        List <EntityInteraction> interactions = controller.getInteractionList();

        StringDisplayable title = new StringDisplayable(new Point(4, 16), "Interaction:", Color.black, 1);
        add(title);

        int i = 2;
        for (EntityInteraction ei : interactions) {
            StringDisplayable sd = new StringDisplayable(new Point(4, 16 * i), ei.name(), Color.black, 1);
            add(sd);
            ++i;
        }

        add(new ImageDisplayable(calculateCursorPoint(interactions.size()), ImageMaker.makeRightPointingTriangle(), 1000));
    }

    private boolean isDisabled () {
        return cursorIndex == -1;
    }

    private Point calculateCursorPoint (int size) {
        int x = 0;
        int y = 24 + (cursorIndex * 16);

        return new Point(x, y);
    }
}
