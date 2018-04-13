package command;

public abstract class ReversibleCommand implements Command {

    private boolean isApplied;

    public abstract void trigger(Entity e);
    protected abstract void apply(Entity e);
    protected abstract void unapply(Entity e);

}
