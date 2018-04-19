package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import utilities.Vector;

import java.util.*;

public abstract class Tile implements GameObjectContainer {

    private Set<MoveLegalityChecker> moveLegalityCheckers;
    private Terrain terrain;
    private Entity entity;
    private Map<Direction, Tile> neighbors;

    public Tile(Set<MoveLegalityChecker> mLCs, Terrain terrain, Entity entity) {
        this.moveLegalityCheckers = mLCs;
        this.terrain = terrain;
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
        moveLegalityCheckers.forEach(MoveLegalityChecker::update);
        //TODO: add logic to check if each MLC expired
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
        return terrain.canMoveHere(entity);
    }

    protected boolean hasEntity() {
        return entity != null;
    }

    public Entity getEntity() {
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

    public boolean has(GameObject o) {
        if(entity == o) {
            return true;
        } else if(moveLegalityCheckers.contains(o)) {
            return true;
        } else if(hasEntity() && entity.has(o)) {
            return true;
        } else {
            return false;
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

}
