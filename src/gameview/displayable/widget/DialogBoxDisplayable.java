package gameview.displayable.widget;

import entity.entitycontrol.EntityController;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.awt.*;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DialogBoxDisplayable extends CompositeDisplayable implements DialogObserver{

    private final int totalMessagesViewable = 5;

    private PriorityQueue <String> messagesOnHold;
    private LinkedList<String> currentMessages;
    private int messagesShowing;

    private Displayable backdrop = new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(300, 128, Color.WHITE), -1);

    public DialogBoxDisplayable(Point origin, EntityController player) {
        super(origin, 1);

        player.register(this);

        messagesShowing = 0;
        currentMessages = new LinkedList<>();
        messagesOnHold = new PriorityQueue<>();
        messagesOnHold.add("Hello World!");
        update ();
    }

    @Override
    public void update () {

        if (messagesOnHold.size() > 0) {
            if (currentMessages.size() >= 5){
                currentMessages.poll();
            }
            currentMessages.add(messagesOnHold.poll());
        }

        super.clear();
        add(backdrop);
        addAllMessagesToDisplay ();
        super.update();
    }

    private void addAllMessagesToDisplay () {
        int displayOffset = 1;
        for (String message : currentMessages) {
            StringDisplayable sd = new StringDisplayable(new Point(4, 16 * (displayOffset)), message, Color.black, 1);
            add(sd);
            ++displayOffset;
        }
    }

    @Override
    public void notfiy(String message) {
        messagesOnHold.add(message);
    }
}