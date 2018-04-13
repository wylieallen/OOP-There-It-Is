package Entity.EntityControl.AI;

public abstract class AI {

    private List<EntityInteraction> interactions;

    public abstract void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e);

    public void setInteractions(Entity e){
        //TODO
    }

}
