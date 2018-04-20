package savingloading;


import maps.world.Game;
import maps.world.LocalWorld;
import org.junit.Test;

import java.util.List;

public class SaveVistorTest {

    @Test
    public void SaveVisitorTest(){
        Visitor v = new SaveVisitor("test");
    }

}
