package command.SkillCommand;

public abstract class SkillCommand {
    private SkillType skillType;
    private int level;
    private int effectiveness;

    public abstract void trigger(Entity e, int distance);
    protected abstract void success(Entity e, int distance);
    protected abstract void fail(Entity e, int distance);

}
