package entity.entitymodel.interactions;

import entity.entitymodel.Entity;
import savingloading.Visitor;

import java.util.List;
import java.util.Random;

/**
 * Created by dontf on 4/13/2018.
 */
public class TalkInteraction implements EntityInteraction {

    private List<String> messages;
    private Random random;

    public TalkInteraction(List<String> messages) {
        random = new Random();
        this.messages = messages;
        messages.add("What in Object Orientation?");
    }

    @Override
    public boolean interact(Entity actor, Entity actee) {
        String m = actee.getName() + ": " + messages.get(random.nextInt(messages.size()));
        actor.getController().notifyAllObservers(m);
        actor.getController().notifyFreeMove(actor);
        return true;
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String name () { return "Talk"; }

    @Override
    public void accept(Visitor v) {
        v.visitTalkInteraction(this);
    }


}
