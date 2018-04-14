package Spawning;

import Influence.InfluenceArea;

/**
 * Created by dontf on 4/14/2018.
 */
public interface SpawnObservable {

    public void notifyAllOfSpawn (InfluenceArea IA);
    public void registerObserver (SpawnObserver SO);
    public void deregisterObserver (SpawnObserver SO);

}
