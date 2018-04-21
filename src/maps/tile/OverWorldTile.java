package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import savingloading.Visitor;
import utilities.Coordinate;
import utilities.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class OverWorldTile extends Tile {

    public OverWorldTile(Set<MoveLegalityChecker> moveLegalityCheckers, Terrain terrain, Entity entity)
    {
        super(moveLegalityCheckers, terrain, entity);
    }

    public Collection<GameObject> getGameObjects()
    {
        Set<GameObject> set = new HashSet<>(super.getMoveLegalityCheckers());
        set.add(super.getTerrain());
        return set;
    }

    @Override
    public void do_update(Map<Coordinate, Tile> map) {
        super.do_update(map);
    }

    @Override
    public void do_moves(Collection<MoveLegalityChecker> updated) {
        super.do_moves(updated, new Vector());
    }

    @Override
    public void do_interactions() {

    }

    public void add(Terrain terrain) {
    }

    @Override
    public void accept(Visitor v) {
        v.visitOverWorldTile(this);
    }
}
