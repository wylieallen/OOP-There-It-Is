package savingloading;

import gameview.GamePanel;
import gameview.GameViewMaker;
import maps.world.Game;
import org.junit.Test;

import java.awt.*;
import java.io.FileNotFoundException;

public class LoadingParserTest {

    @Test
    public void LoadingParserTest() throws FileNotFoundException {

        // save a game
        Visitor v = new SaveVisitor("test");
        GameViewMaker g = new GameViewMaker();
        g.makeGameDisplayState(new GamePanel(new Dimension()));
        Game gameToSave = g.getGame();
        gameToSave.accept(v);

        // load the same game
        LoadingParser l = new LoadingParser();
        l.loadGame("test", new GamePanel(new Dimension()));

        // check if the games match up
        Game gameLoaded = l.getGame();


    }
}
