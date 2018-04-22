package commands.reversiblecommands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class BuffHealthCommand extends ReversibleCommand
{
    private int amount;

    public BuffHealthCommand(int amount)
    {
        super(false);
        this.amount = amount;
    }

    @Override
    protected void apply(Entity e)
    {
        e.increaseMaxHealth(amount);
    }

    @Override
    protected void unapply(Entity e)
    {
        e.decreaseMaxHealth(amount);
    }

    @Override
    public void accept(Visitor v)
    {
        v.visitBuffHealthCommand(this);
    }

    public int getBuffAmount()
    {
        return amount;
    }
}
