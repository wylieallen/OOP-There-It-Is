package savingloading;


import gameview.GamePanel;
import gameview.GameViewMaker;
import maps.world.Game;
import org.junit.Test;

import java.awt.*;

public class SaveVistorTest {

    @Test
    public void SaveVisitorTest(){
        Visitor v = new SaveVisitor("test");
        GameViewMaker g = new GameViewMaker();
        g.makeGameDisplayState(new GamePanel(new Dimension()));
        Game game = g.getGame();
        game.accept(v);
    }

}
