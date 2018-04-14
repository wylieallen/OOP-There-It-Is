package Influence;

import entitymodel.Entity;
import gameobject.GameObject;
import Saving_Loading.Visitor;
import Tiles.Tile;
import Utilities.Coordinate;
import World.Game;
import World.World;
import commands.skillcommands.SkillCommand;
import item.Item;

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
