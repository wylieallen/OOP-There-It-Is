package commands;

import entity.entitymodel.Entity;
import commands.reversiblecommands.ReversibleCommand;
import maps.world.Game;

public class TimedEffect {

    private ReversibleCommand command;
    private long timeRemaining;
    private long lastUpdateTime;

    public TimedEffect(ReversibleCommand command, long timeRemaining, long lastUpdateTime) {
        this.command = command;
        this.timeRemaining = timeRemaining;
        this.lastUpdateTime = lastUpdateTime;
    }

    public void trigger(Entity affectedEntity){
        command.trigger(affectedEntity);
        lastUpdateTime = Game.getCurrentTime();
    }

    public void decrementTimeRemaining() {
        timeRemaining -= Game.getCurrentTime() - lastUpdateTime;
        if(timeRemaining < 0) {
            timeRemaining = 0;
        }
        lastUpdateTime = Game.getCurrentTime();
    }

    public boolean isExpired() {
        return timeRemaining == 0;
    }

    public void triggerIfExpired(Entity entity) {
        if(isExpired()){
            trigger(entity);
        }
    }

}
