package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;
import maps.entityimpaction.AreaEffect;
import maps.world.Game;
import savingloading.Visitor;

public class InfiniteAreaEffect extends AreaEffect {

    private long triggerInterval;
    private long lastTriggerTime;

    public InfiniteAreaEffect(Command command, long triggerInterval, long lastTriggerTime, String name) {
        super(command, name);
        this.triggerInterval = triggerInterval;
        this.lastTriggerTime = lastTriggerTime;
    }

    public void touch(Entity entity) {
        if(Game.getCurrentTime() - lastTriggerTime > triggerInterval) {
            trigger(entity);
            lastTriggerTime = Game.getCurrentTime();
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visitInfiniteAreaEffect(this);
    }
}
