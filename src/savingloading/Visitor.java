package savingloading;

import entity.entitymodel.Entity;
import maps.tile.Tile;
import maps.world.World;
import maps.world.Game;
import items.Item;

/**
 * Created by dontf on 4/13/2018.
 */
public interface Visitor {

    void visitTile (Tile t);
    void visitEntity (Entity e);
    void visitItem (Item i);
    void visitWorld (World w);
    void visitGame (Game g);

}
