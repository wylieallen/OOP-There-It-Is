package gameview.displayable.widget;

import entity.entitycontrol.EntityController;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.awt.*;
import java.util.PriorityQueue;

public class DialogBoxDisplayable extends CompositeDisplayable implements DialogObserver{

    private final int totalMessagesViewable = 5;

    private PriorityQueue <String> messagesOnHold;
    private String [] currentMessages;
    private int messagesShowing;

    private Displayable backdrop = new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(212, 128, Color.WHITE), -1);

    public DialogBoxDisplayable(Point origin, EntityController player) {
        super(origin, 1);

        player.register(this);

        messagesShowing = 0;
        currentMessages = new String [totalMessagesViewable];
        messagesOnHold = new PriorityQueue<>();
        messagesOnHold.add("Hello World!");
        update ();
    }

    @Override
    public void update () {

        if (messagesOnHold.size() > 0) {
            rotateMessagesLeft();
            currentMessages [messagesShowing] = messagesOnHold.poll();
            if (messagesShowing < totalMessagesViewable - 1) {
                ++messagesShowing;
            }
        }

        super.clear();
        add(backdrop);
        addAllMessagesToDisplay ();
        super.update();
    }

    private void rotateMessagesLeft () {
        for (int i = 0; i < messagesShowing; ++i) {
            currentMessages[i] = currentMessages [i + 1];
        }
    }

    private void addAllMessagesToDisplay () {
        for (int i = 0; i < messagesShowing; ++i) {
            StringDisplayable sd = new StringDisplayable(new Point(4, 16 * (i + 1)), currentMessages[i], Color.black, 1);
            add(sd);
        }
    }

    @Override
    public void notfiy(String message) {
        messagesOnHold.add(message);
    }
}