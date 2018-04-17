package entity.entitycontrol.AI;

import entity.entitymodel.Entity;
import entity.entitymodel.interactions.EntityInteraction;
import gameobject.GameObjectContainer;
import savingloading.Visitor;
import utilities.Coordinate;

import java.util.List;
import java.util.Map;

public class PetAI extends AI {

    private Entity master;

    public PetAI(List<EntityInteraction> interactions, Entity master) {
        super(interactions);
        this.master = master;
    }

    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO: Make the pet follow the entity
    }

    @Override
    public void accept(Visitor v) {
        v.visitPetAI(this);
    }
}
