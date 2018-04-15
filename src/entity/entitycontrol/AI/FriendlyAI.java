package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import gameobject.GameObjectContainer;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.Map;

public class FriendlyAI extends AI {
    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO: make the entity allow for interactions with other entities
    }

    @Override
    public void accept(Visitor v) {
        v.visitFriendlyAI(this);
    }
}
