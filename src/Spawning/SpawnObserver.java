package Spawning;

import GameObject.GameObject;

/**
 * Created by dontf on 4/14/2018.
 */
public interface SpawnObserver {

    public void notifySpawn (InfluenceArea IA, GameObject spawner);

}
