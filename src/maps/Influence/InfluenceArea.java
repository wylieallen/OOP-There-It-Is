package maps.Influence;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import savingloading.Visitor;
import maps.tile.Tile;
import utilities.Coordinate;
import maps.world.Game;
import maps.world.World;
import commands.skillcommands.SkillCommand;
import items.Item;

import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */
public class InfluenceArea implements Visitor {

    private InfluenceType influenceType;
    private Coordinate center;
    private List<GameObject> whiteList;
    private long lastUpdateTime;
    private List <Coordinate> offsetPoints;
    private long updateInterval;
    private SkillCommand skillCommand;



    public void update (int curTime) {

    }

    public void checkArea () {

    }

    public void gatherPoints (InfluenceType influenceType) {

    }

    @Override
    public void visitTile(Tile t) {

    }

    @Override
    public void visitEntity(Entity e) {

    }

    @Override
    public void visitItem(Item i) {

    }

    @Override
    public void visitWorld(World w) {

    }

    @Override
    public void visitGame(Game g) {

    }

}
