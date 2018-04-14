package savingloading;

import entity.entitymodel.Entity;
import maps.world.Game;
import gameview.GameDisplayState;

/**
 * Created by dontf on 4/13/2018.
 */
public class LoadingParser {

    private Game game;
    private Entity player;
    private GameDisplayState gameDisplay;

    public void loadGame () {

    }

    public GameDisplayState getGameDisplayState () {
        return gameDisplay;
    }

    public Game getGame () {
        return game;
    }

    public Entity getPlayer () {
        return player;
    }

}
