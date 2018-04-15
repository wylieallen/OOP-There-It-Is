package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.movelegalitychecker.MoveLegalityChecker;
import utilities.Vector;

import java.util.*;

public abstract class Tile implements GameObjectContainer {

    private Set<MoveLegalityChecker> moveLegalityCheckers;
    private Entity entity;
    private Map<Direction, Tile> neighbors;

    public Tile(Set<MoveLegalityChecker> mLCs, Entity entity) {
        this.moveLegalityCheckers = mLCs;
        this.entity = entity;
        this.neighbors = new HashMap<>();
    }

    public void setEntity(Entity entity){
        this.entity = entity;
        moveLegalityCheckers.add(entity);
    }

    public void setNeighbor(Direction direction, Tile tile){
        neighbors.put(direction, tile);
    }
    public Tile getNeighbor(Direction direction) { return neighbors.getOrDefault(direction, null);}

    public void tryToMove(Tile tileFrom, Entity entity){
        if(isMoveLegal(entity)){
            System.out.println("Moving");
            tileFrom.moveFrom();
            setEntity(entity);
        }
    }

    public void moveFrom(){
        remove(entity);
    }

    public void add(MoveLegalityChecker mlc) { moveLegalityCheckers.add(mlc); }

    public abstract List<GameObject> getGameObjects();

    public Collection<MoveLegalityChecker> getMoveLegalityCheckers() { return moveLegalityCheckers; }

    public void update() {
        for (MoveLegalityChecker mlc : moveLegalityCheckers) {
            mlc.update();
        }

        if (hasEntity())
            entity.update();

        do_moves();
        do_interactions();
    }

    protected abstract void do_moves();

    protected void do_moves(Vector externalForce) {
        if(hasEntity()) {
            Vector entityVector = entity.getMovementVector();

            Vector total = new Vector();
            total.add(externalForce);
            total.add(entityVector);

            Tile toMoveTo = neighbors.get(total.getDirection());
            System.out.println(this);
            System.out.println(toMoveTo);
            toMoveTo.tryToMove(this, entity);
        }
    }

    public boolean isMoveLegal(Entity entity) {
        for(MoveLegalityChecker mlc: moveLegalityCheckers) {
            System.out.println(mlc.toString());
            if(!mlc.canMoveHere(entity)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasEntity() {
        return entity != null;
    }

    protected abstract void do_interactions();

    public boolean has(Entity e) {
        return entity == e;
    }

    public boolean remove(Entity e) {
        if (entity == e) {
            entity = null;
            moveLegalityCheckers.remove(e);
            return true;
        }
        return false;
    }
}
