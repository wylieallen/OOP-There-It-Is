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

    @Override
    public void interact(Entity actor, Entity actee) {

    }

    @Override
    public void accept(Visitor v) {
        v.visitTalkInteraction(this);
    }
}
