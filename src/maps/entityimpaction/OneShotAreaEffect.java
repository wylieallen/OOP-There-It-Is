package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;

public class OneShotAreaEffect extends AreaEffect {

    private boolean hasFired;

    public OneShotAreaEffect(Command command, boolean hasFired) {
        super(command);
        this.hasFired = hasFired;
    }

    public void touch(Entity entity) {
        if(!hasFired) {
            trigger(entity);
            hasFired = true;
        }
    }
}
