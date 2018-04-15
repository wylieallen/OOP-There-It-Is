package maps.tile;

import entity.entitymodel.Entity;
import maps.movelegalitychecker.MoveLegalityChecker;
import gameobject.GameObject;
import maps.world.LocalWorld;
import utilities.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class OverWorldTile extends Tile {

    private LocalWorld localWorld;

    public OverWorldTile() {
        super(new HashSet<>(), null);
        localWorld = null;
    }

    public OverWorldTile(Set<MoveLegalityChecker> mLCs, Entity entity, LocalWorld localWorld) {
        super(mLCs, entity);
        this.localWorld = localWorld;
    }

    public List<GameObject> getGameObjects()
    {
        List<GameObject> list = new ArrayList<>(super.getMoveLegalityCheckers());
        return list;
    }

    protected void do_moves() {
        super.do_moves(new Vector());
    }

    protected void do_interactions() {

    }
}
