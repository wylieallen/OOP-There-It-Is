package EntityImpaction;

import EnitityModel.Entity;
import command.Command;

public class Trap implements EntityImpactor {

    private Command command;
    private boolean hasFired;
    private int strength;
    private boolean isVisible;

    public void touch(Entity entity) {
        //
    }

    public void attemptDisarm(Entity entity){
        //
    }
}
