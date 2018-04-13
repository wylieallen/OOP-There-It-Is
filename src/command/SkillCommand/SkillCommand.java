package command.SkillCommand;

public abstract class SkillCommand {
    private SkillType skillType;
    private int level;
    private int effectiveness;

    public trigger(Entity e, int distance);
    protected success(Entity e, int distance);
    protected fail(Entity e, int distance);

}
