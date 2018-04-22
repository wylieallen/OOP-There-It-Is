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

        GameViewMaker g = new GameViewMaker();
        g.makeGameDisplayState(new GamePanel(new Dimension()));
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        // save a game
        SaveVisitor v = new SaveVisitor("test");
        Game game = g.getGame();
        game.accept(v);

        // load the same game
        LoadingParser l = new LoadingParser();
        l.loadGame("test", new GamePanel(new Dimension()));

//        // save the game
//        Game gameLoaded = l.getGame();
//        gameLoaded.accept(v);
//
//        // load the game again
//        LoadingParser l2 = new LoadingParser();
//        l2.loadGame("test", new GamePanel(new Dimension()));
    }
}
