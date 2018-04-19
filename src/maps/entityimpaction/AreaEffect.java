package maps.entityimpaction;

import entity.entitymodel.Entity;
import commands.Command;
import maps.entityimpaction.EntityImpactor;
import maps.world.Game;

public abstract class AreaEffect implements EntityImpactor {

    private Command command;
    private long triggerInterval;
    private long lastTriggerTime;

    public AreaEffect(Command command, long triggerInterval, long lastTriggerTime) {
        this.command = command;
        this.triggerInterval = triggerInterval;
        this.lastTriggerTime = lastTriggerTime;
    }

    public abstract void touch(Entity entity);

    protected void trigger(Entity e) {
        if(Game.getCurrentTime() - lastTriggerTime > triggerInterval) {
            command.trigger(e);
            lastTriggerTime = Game.getCurrentTime();
        }
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }
}
