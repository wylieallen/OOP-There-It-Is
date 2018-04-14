package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.movelegalitychecker.MoveLegalityChecker;

import java.util.*;

public abstract class Tile implements GameObjectContainer {

    private Set<MoveLegalityChecker> moveLegalityCheckers;
    private Entity entity;
    private Map<Direction, Tile> neighbors;

    public Tile()
    {
        moveLegalityCheckers = new HashSet<>();
    }

    public void setEntity(Entity entity){
        this.entity = entity;
    }

    public void setNeighbor(Direction direction, Tile tile){
        neighbors.put(direction, tile);
    }

    public void tryToMove(Tile tileFrom, Entity entity){
        //
    }

    public void moveFrom(){
        //
    }

    public void add(MoveLegalityChecker mlc) { moveLegalityCheckers.add(mlc); }

    public List<GameObject> getGameObjects() {
        return null;
    }

    public void update(){

    }

    protected abstract void do_moves();

    protected abstract void do_interactions();
}
