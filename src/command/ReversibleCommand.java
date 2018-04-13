package command;

public abstract class ReversibleCommand {

    private boolean isApplied;

    public trigger(Entity e);
    protected apply(Entity e);
    protected unapply(Entity e);

}
