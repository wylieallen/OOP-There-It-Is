package savingloading;

import maps.world.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaveVisitorTest {

    @Test
    public void visitGameTest(){
        Visitor v = new SaveVisitor("test");
    }
}