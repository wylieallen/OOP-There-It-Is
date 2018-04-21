package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import gameview.displayable.widget.DialogObservable;
import gameview.displayable.widget.DialogObserver;
import savingloading.Visitor;

import java.util.List;
import java.util.Random;

/**
 * Created by dontf on 4/13/2018.
 */
public class TalkInteraction implements EntityInteraction, DialogObservable {

    private List<String> messages;
    private Random random;

    public TalkInteraction(List<String> messages) {
        random = new Random();
        this.messages = messages;
    }

    @Override
    public boolean interact(Entity actor, Entity actee) {
        notifyAllObservers(messages.get(random.nextInt(messages.size())));
        return true;
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public void accept(Visitor v) {
        v.visitTalkInteraction(this);
    }

    @Override
    public void register(DialogObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(DialogObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers (String message) {
        for (DialogObserver o : observers) {
            o.notfiy(message);
        }
    }
}
