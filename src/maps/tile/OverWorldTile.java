package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.movelegalitychecker.MoveLegalityChecker;
import utilities.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OverWorldTile extends Tile {

    public OverWorldTile()
    {
        super();
    }

    public Collection<GameObject> getGameObjects()
    {
        Set<GameObject> set = new HashSet<>();
        set.addAll(super.getMoveLegalityCheckers());
        return set;
    }

    protected void do_moves(Collection<MoveLegalityChecker> updated) {
        super.do_moves(updated, new Vector());
    }

    protected void do_interactions(Entity entity) {

    }
}
