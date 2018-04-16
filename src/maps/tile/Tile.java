package maps.tile;

import entity.entitymodel.Entity;
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

    public void setNeighbor(Direction direction, Tile tile)
    {
        neighbors.put(direction, tile);
    }
    public Tile getNeighbor(Direction direction) { return neighbors.getOrDefault(direction, null);}

    private void tryToMove(Tile tileFrom, Entity entity){
        if(isMoveLegal(entity)){
            tileFrom.moveFrom();
            setEntity(entity);
        }
        entity.resetMovementVector();
    }

    private void moveFrom(){
        remove(entity);
    }

    public void addMLC(MoveLegalityChecker mlc) { moveLegalityCheckers.add(mlc); }

    public Collection<MoveLegalityChecker> getMoveLegalityCheckers() { return moveLegalityCheckers; }

    public void do_update() {
        for(MoveLegalityChecker mlc: moveLegalityCheckers) {
            mlc.update();
        }
    }

    protected abstract void do_moves(Collection<MoveLegalityChecker> updated);

    protected void do_moves(Collection<MoveLegalityChecker> updated, Vector externalForce) {
        if(hasEntity()) {
            Vector entityVector = entity.getMovementVector();

            Vector total = new Vector();
            total.add(externalForce);
            total.add(entityVector);

            Tile toMoveTo = neighbors.get(total.getDirection());
            updated.add(entity);
            if(!total.isZeroVector()){
                toMoveTo.tryToMove(this, entity);
            } else {
                entity.resetMovementVector();
            }
        }
    }

    public abstract void do_interactions();

    public boolean isMoveLegal(Entity entity) {
        for(MoveLegalityChecker mlc: moveLegalityCheckers) {
            if(!mlc.canMoveHere(entity)) {
                return false;
            }
        }

        return true;
    }

    protected boolean hasEntity() {
        return entity != null;
    }

    protected Entity getEntity() {
        return entity;
    }

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
