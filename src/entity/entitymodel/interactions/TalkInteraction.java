package entity.entitymodel.interactions;

import entity.entitymodel.Entity;

import entity.entitymodel.interactions.EntityInteraction;
import savingloading.Visitor;

import java.util.Set;

/**
 * Created by dontf on 4/13/2018.
 */
public class TalkInteraction implements EntityInteraction {

    private Set<String> messages;

    public TalkInteraction(Set<String> messages){
        this.messages = messages;
    }

    @Override
    public void interact(Entity actor, Entity actee) {

    }

    public Set<String> getMessages() {
        return messages;
    }

    @Override
    public void accept(Visitor v) {
        v.visitTalkInteraction(this);
    }
}
