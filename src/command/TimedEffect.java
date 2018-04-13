package command;

public class TimedEffect {

    private ReversibleCommand command;
    private long timeRemaining;

    public TimedEffect(ReversibleCommand command, long timeRemaining) {
        this.command = command;
        this.timeRemaining = timeRemaining;
    }

    public void trigger(Entity affectedEntity){
        command.trigger(affectedEntity);
    };

}
