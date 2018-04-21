package maps.tile;

import entity.entitymodel.Entity;
import maps.entityimpaction.EntityImpactor;
import maps.movelegalitychecker.MoveLegalityChecker;
import gameobject.GameObject;
import maps.movelegalitychecker.Terrain;
import savingloading.Visitor;
import utilities.Vector;
import java.util.*;


public class OverWorldTile extends Tile {

    private EntityImpactor encounter;

    public OverWorldTile(Set<MoveLegalityChecker> moveLegalityCheckers, Terrain terrain, Entity entity)
    {
        super(moveLegalityCheckers, terrain, entity);
        encounter = null;
    }

    public OverWorldTile(Set<MoveLegalityChecker> moveLegalityCheckers, Terrain terrain, Entity entity, EntityImpactor encounter)
    {
        super(moveLegalityCheckers, terrain, entity);
        this.encounter = encounter;
    }

    public Collection<GameObject> getGameObjects()
    {
        Set<GameObject> set = new HashSet<>(super.getMoveLegalityCheckers());
        set.add(super.getTerrain());
        return set;
    }

    @Override
    public void do_update() {
        super.do_update();
    }

    @Override
    public void do_moves(Collection<MoveLegalityChecker> updated) {
        super.do_moves(updated, new Vector());
    }

    @Override
    public void do_interactions() {
        if(super.hasEntity() && hasEncounter()){
            encounter.touch(super.getEntity());
        }
    }

    private boolean hasEncounter() {
        return encounter != null;
    }

    @Override
    public void accept(Visitor v) {
        v.visitOverWorldTile(this);
    }
}
