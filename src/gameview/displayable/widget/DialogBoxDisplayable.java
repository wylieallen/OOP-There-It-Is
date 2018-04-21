package gameview.displayable.widget;

import guiframework.displayable.CompositeDisplayable;

import java.awt.*;
import java.util.PriorityQueue;

public class DialogBoxDisplayable extends CompositeDisplayable implements DialogObserver{

    private PriorityQueue <String> messages;

    public DialogBoxDisplayable(Point origin, int height) {
        super(origin, height);
        messages = new PriorityQueue<>();
    }

    @Override
    public void notfiy(String message) {
        messages.add(message);
    }
}