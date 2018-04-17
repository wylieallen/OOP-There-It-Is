package maps.world;

import entity.entitymodel.Entity;
import utilities.Coordinate;

import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */
public class Game implements TransitionObserver {

    private World activeWorld;
    private OverWorld overWorld;
    private List<LocalWorld> localWorlds;
    private static int curTime;
    private Entity player;

    public Game(World activeWorld,
                OverWorld overWorld,
                List<LocalWorld> localWorlds,
                int curTime,
                Entity player)
    {
        this.activeWorld = activeWorld;
        this.overWorld = overWorld;
        this.localWorlds = localWorlds;
        this.curTime = curTime;
        this.player = player;
    }

    public static int getCurrentTime () {
        return curTime;
    }

    public void notifyTransition (Entity e, World target, Coordinate p) {
        if(isPlayer(e)) {
            activeWorld.remove(e);
            setActiveWorld(target);
            activeWorld.add(p, e);
        }
    }

    private boolean isPlayer(Entity other) {
        return player == other;
    }

    private void setActiveWorld(World target) {
        activeWorld = target;
    }

    public void update()
    {
        activeWorld.update();
    }
}
