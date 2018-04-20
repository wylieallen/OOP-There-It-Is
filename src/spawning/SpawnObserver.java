package spawning;

import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import utilities.Coordinate;

/**
 * Created by dontf on 4/14/2018.
 */
public interface SpawnObserver {

    void notifySpawn (InfluenceArea IA, GameObject spawner);

}
