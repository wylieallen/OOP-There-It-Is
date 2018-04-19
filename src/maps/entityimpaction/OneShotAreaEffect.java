package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;

public class OneShotAreaEffect extends AreaEffect {

    private boolean hasFired;

    public OneShotAreaEffect(Command command, long triggerInterval, long lastTriggerTime, boolean hasFired) {
        super(command, triggerInterval, lastTriggerTime);
        this.hasFired = hasFired;
    }

    public void touch(Entity entity) {
        if(!hasFired) {
            trigger(entity);
            hasFired = true;
        }
    }

    @Override
    public boolean shouldBeRemoved() {
        return hasFired;
    }
}
