package maps.tile;

import maps.movelegalitychecker.MoveLegalityChecker;
import gameobject.GameObject;
import utilities.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public void do_update() {
        super.do_update();
    }

    @Override
    public void do_moves(Set<MoveLegalityChecker> updated) {
        super.do_moves(updated, new Vector());
    }

    @Override
    public void do_interactions() {

    }
}
