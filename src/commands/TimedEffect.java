package commands;

import entity.entitymodel.Entity;
import commands.reversiblecommands.ReversibleCommand;

public class TimedEffect {

    private ReversibleCommand command;
    private long timeRemaining;

    public TimedEffect(ReversibleCommand command, long timeRemaining) {
        this.command = command;
        this.timeRemaining = timeRemaining;
    }

    public void trigger(Entity affectedEntity){
        command.trigger(affectedEntity);
    }

    public void decrementTimeRemaining() {
        if(!isExpired()){
            --timeRemaining;
        }
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
