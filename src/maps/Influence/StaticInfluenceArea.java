package maps.Influence;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import utilities.Coordinate;
import maps.world.Game;
import commands.skillcommands.SkillCommand;
import utilities.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dontf on 4/14/2018.
 */
public class StaticInfluenceArea implements InfluenceArea {

    //variables needed in the constructor
    private Coordinate center;//The influence area's center in gameWorld coordinates
    private List<GameObject> whiteList;//List of objects to ignore
    private long updateInterval;//The frequency at which the area is checked and visited
    private long duration;//The amount of time this area should live for, -1 means it will live foReEVeR
    private SkillCommand skillCommand;//the thing this area does to entities when it visits them

    //variables derived or created upon construction
    private long startTime;//The time this are was created
    private long lastUpdateTime;//Last time the area was checked and visited
    private boolean isExpired = false;//see name    private List <Coordinate> offsetPoints;//the list of points this area is responsible for checking
    private List <Coordinate> offsetPoints;//the list of points this area is responsible for checking
    private boolean hasUpdatedForTheFirstTime = false;

    public StaticInfluenceArea(InfluenceType influenceType, Direction direction, int radius, Coordinate center, List<GameObject> whiteList, long updateInterval, long duration, SkillCommand skillCommand) {
        this.center = center;
        this.whiteList = whiteList;
        this.updateInterval = updateInterval;
        this.duration = duration;
        this.skillCommand = skillCommand;
        offsetPoints = influenceType.getOffsets(direction,radius);
        startTime = Game.getCurrentTime();
        lastUpdateTime = startTime;
    }


    @Override
    public void update(Map<Coordinate, LocalWorldTile> tilesMap) {
        if(!hasUpdatedForTheFirstTime){
            hasUpdatedForTheFirstTime = true;
            startTime = Game.getCurrentTime();
            lastUpdateTime = Game.getCurrentTime()-updateInterval;
        }
        if(!isExpired && duration != -1 && Game.getCurrentTime()-startTime >= duration){
            isExpired = true;
            return;
        }
        if(!isExpired && Game.getCurrentTime()-lastUpdateTime >= updateInterval){
            lastUpdateTime = Game.getCurrentTime();
            checkArea(tilesMap);
        }
    }

    @Override
    public boolean isExpired() {
        return isExpired;
    }

    @Override
    public boolean expired(){return isExpired;}

    @Override
    public List<Coordinate> getAffectedCoordinates() {
        List<Coordinate> worldCoordinates = new ArrayList<>();
        for(Coordinate offset : offsetPoints){
            worldCoordinates.add(center.add(offset));
        }
        return worldCoordinates;
    }

    private void checkArea (Map<Coordinate, LocalWorldTile> tilesMap) {
        Coordinate coord;
        LocalWorldTile tile;
        Entity ent;
        Vector vec;
        for(Coordinate offset : offsetPoints){
            coord = center.add(offset);
            if(tilesMap.containsKey(coord)){
                vec = new Vector(new Coordinate(0,0),offset);
                int currentCheckingRadius = (int)vec.getDistance();
                tile = tilesMap.get(coord);
                ent = tile.getEntity();
                if(ent != null){
                    if(!whiteList.contains(ent)){
                        skillCommand.trigger(ent,currentCheckingRadius);
                        whiteList.add(ent);
                    }
                }
            }
        }
    }

    @Override
    public void setCenter(Coordinate coordinate) {
        center = coordinate;
    }

}
