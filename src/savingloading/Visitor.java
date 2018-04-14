package savingloading;

import entitymodel.Entity;
import tile.Tile;
import world.World;
import world.Game;
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
