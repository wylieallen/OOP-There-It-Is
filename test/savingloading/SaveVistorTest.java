package savingloading;


import maps.world.Game;
import org.junit.Test;

public class SaveVistorTest {

    @Test
    public void SaveVisitorTest(){
        Visitor v = new SaveVisitor("test");
    }
}
