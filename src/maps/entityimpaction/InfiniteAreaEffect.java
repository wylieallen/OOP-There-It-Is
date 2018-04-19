package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;
import maps.entityimpaction.AreaEffect;

public class InfiniteAreaEffect extends AreaEffect {

    public InfiniteAreaEffect(Command command, long triggerInterval, long lastTriggerTime) {
        super(command, triggerInterval, lastTriggerTime);
    }

    public void touch(Entity entity) {
        trigger(entity);
    }
}
