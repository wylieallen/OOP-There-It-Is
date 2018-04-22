package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.entityimpaction.EntityImpactor;
import maps.movelegalitychecker.Terrain;
import savingloading.Visitor;
import utilities.Coordinate;
import utilities.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
        Set<GameObject> set = new HashSet<>();
        set.addAll(super.getGameObjects());
        if(encounter != null)
            set.add(encounter);
        return set;
    }

    @Override
    public void do_update(Map<Coordinate, Tile> map) {
        super.do_update(map);

        if(encounter != null && encounter.expired()) {
            encounter = null;
        }
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

    public EntityImpactor getEncounter() {
        return encounter;
    }

    @Override
    public void accept(Visitor v) {
        v.visitOverWorldTile(this);
    }

    public void setEncounter(EntityImpactor ei) {
        encounter = ei;
    }
}
