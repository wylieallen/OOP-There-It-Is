package maps.tile;

import gameobject.GameObject;
import maps.world.LocalWorld;

import java.util.ArrayList;
import java.util.List;

public class OverWorldTile extends Tile{

    public List<GameObject> getGameObjects()
    {
        List<GameObject> list = new ArrayList<>();
        list.addAll(super.getMoveLegalityCheckers());
        return list;
    }

    protected void do_moves() {

    }

    protected void do_interactions() {

    }
}
