package maps.world;

import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import gameobject.GameObject;
import gameobject.GameObjectContainer;
import utilities.Coordinate;

import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */
public class Game implements TransitionObserver {

    private World activeWorld;
    private OverWorld overWorld;
    private List<LocalWorld> localWorlds;
    private static long curTime;
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
        Game.curTime = curTime;
        this.player = player;
    }

    public static long getCurrentTime () {
        return curTime;
    }

    public void notifyTransition (Entity e, World target, Coordinate p) {
        if(isPlayer(e)) {
            updateWeaponItems(e, target);
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

    private void updateWeaponItems(Entity e, World target) {
        e.updateSpawnObservers(activeWorld, target);
    }

    public static void updateGameTime() {
        curTime = System.currentTimeMillis();
    }

    public void update()
    {
        updateGameTime();
        activeWorld.update();
    }

    public Entity getPlayer() { return player; }

    public void setPlayerController(EntityController controller)
    {
        player.setController(controller);
    }

    public Coordinate getCoordinate(Entity entity)
    {
        return activeWorld.getEntityCoordinate(entity);
    }
}
