package maps.tile;

import entity.entitymodel.Entity;
import maps.movelegalitychecker.MoveLegalityChecker;
import gameobject.GameObject;
import maps.movelegalitychecker.MoveLegalityChecker;
import utilities.Vector;
import java.util.*;


public class OverWorldTile extends Tile {

    public OverWorldTile(Set<MoveLegalityChecker> moveLegalityCheckers, Entity entity)
    {
        super(moveLegalityCheckers, entity);
    }

    public Collection<GameObject> getGameObjects()
    {
        Set<GameObject> set = new HashSet<>();
        set.addAll(super.getMoveLegalityCheckers());
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

    }
}
