package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import savingloading.Visitable;
import utilities.Coordinate;
import utilities.Vector;

import java.util.*;

public abstract class Tile implements GameObjectContainer, Visitable {

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
    }

    public void setNeighbor(Direction direction, Tile tile)
    {
        neighbors.put(direction, tile);
    }
    public Tile getNeighbor(Direction direction) { return neighbors.getOrDefault(direction, null);}

    private void tryToMove(Tile tileFrom, Entity entity, int moveSpeed){
        if(isMoveLegal(entity) && entity.tryToMove(moveSpeed)){
            tileFrom.moveFrom();
            setEntity(entity);
        }
        entity.resetMovementVector();
    }

    private void moveFrom(){
        remove(entity);
    }

    // for testing purposes
    public Collection<MoveLegalityChecker> getMoveLegalityCheckers() { return moveLegalityCheckers; }

    public void do_update(Map <Coordinate, Tile> map) {
        moveLegalityCheckers.forEach(MoveLegalityChecker::update);

        if (entity != null) {
            entity.update(map);
        }

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
                toMoveTo.tryToMove(this, entity, (int)total.getMagnitude());
            } else {
                entity.resetMovementVector();
            }
        }
    }

    public abstract void do_interactions();

    public boolean isMoveLegal(Entity entity) {

        boolean isLegal = true;

        if (hasEntity()) {
            isLegal = this.entity.canMoveHere(entity);
        }

        for(MoveLegalityChecker mlc: moveLegalityCheckers) {
            if(!mlc.canMoveHere(entity)) {
                isLegal = false;
            }
        }

        if (!terrain.canMoveHere(entity)) {
            isLegal = false;
        }

        return isLegal;

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

    public boolean checkTerrainCompatibliity (Terrain t) {
        return terrain.equals(t);
    }

    @Override
    public Collection<GameObject> getGameObjects() {
        List<GameObject> list = new ArrayList<>();
        list.addAll(moveLegalityCheckers);
        list.add(terrain);
        if(hasEntity())
            list.add(entity);

        return list;
    }

}
