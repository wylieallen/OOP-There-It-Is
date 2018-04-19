package maps.Influence;

import commands.skillcommands.SkillCommand;
import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.world.Game;
import utilities.Coordinate;
import utilities.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class expandingInfluenceArea implements InfluenceArea {

    //variables needed in the constructor
    private Coordinate center;
    private List <GameObject> whiteList;
    private long updateInterval;
    private long expansionInterval;
    private SkillCommand skillCommand;
    private int maxRadius;

    //variables derived or created upon construction
    private long startTime;
    private long lastUpdateTime;
    private long lastExpansionTime;
    private int currentRadius;
    private List <Coordinate> offsetPoints;
    private boolean isExpired = false;

    public expandingInfluenceArea(InfluenceType influenceType, Direction direction, int maxRadius, Coordinate center, List<GameObject> whiteList, long updateInterval, long expansionInterval, SkillCommand skillCommand) {
        this.center = center;
        this.whiteList = whiteList;
        this.updateInterval = updateInterval;
        this.expansionInterval = expansionInterval;
        this.skillCommand = skillCommand;
        this.maxRadius = maxRadius;

        startTime = Game.getCurrentTime();
        lastUpdateTime = startTime;
        lastExpansionTime = startTime;
        currentRadius = 0;
        offsetPoints = influenceType.getOffsets(direction,maxRadius);
    }

    @Override
    public void update(Map<Coordinate, LocalWorldTile> tilesMap) {
        if(!isExpired && Game.getCurrentTime() - lastExpansionTime >= expansionInterval){
            currentRadius++;
            if(currentRadius>maxRadius){
                isExpired = true;
                return;
            }
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
    public List<Coordinate> getAffectedCoordinates() {
        List<Coordinate> worldCoordinates = new ArrayList<>();
        Vector vec;
        double distance;
        for(Coordinate offset : offsetPoints){
            vec = new Vector(new Coordinate(0,0),offset);
            distance = vec.getDistance();
            if(distance >= currentRadius && distance < (currentRadius + 1)) {
                worldCoordinates.add(center.add(offset));
            }
        }
        return worldCoordinates;
    }

    private void checkArea(Map<Coordinate, LocalWorldTile> tilesMap){
        List<Coordinate> coords = getAffectedCoordinates();
        LocalWorldTile tile;
        Entity ent;
        for(Coordinate coord : coords){
            if(tilesMap.containsKey(coord)){
                tile = tilesMap.get(coord);
                ent = tile.getEntity();
                if(ent != null){
                    if(!whiteList.contains(ent)){
                        skillCommand.trigger(ent,currentRadius);
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

    @Override
    public void trigger(Entity e, Coordinate coordinate) {
        int distance = center.distance(coordinate);
        skillCommand.trigger(e, distance);
    }
}
