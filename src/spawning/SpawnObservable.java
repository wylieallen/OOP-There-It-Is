package spawning;

import maps.Influence.InfluenceArea;
import utilities.Coordinate;

/**
 * Created by dontf on 4/14/2018.
 */
public interface SpawnObservable {

    void notifyAllOfSpawn (InfluenceArea IA);
    void registerObserver (SpawnObserver SO);
    void deregisterObserver (SpawnObserver SO);

}
