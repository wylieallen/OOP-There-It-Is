package tile;

import entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;

import java.util.List;
import java.util.Map;

public abstract class Tile implements GameObjectContainer {

    private List<MoveLegalityChecker> mLCs;
    private Entity entity;
    private Map<Direction, Tile> neighbors;

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

    public List<GameObject> getGameObjects() {
        return null;
    }

    public void update(){

    }

    protected abstract void do_moves();

    protected abstract void do_interactions();
}
