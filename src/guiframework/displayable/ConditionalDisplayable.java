package guiframework.displayable;

import guiframework.util.TypedAbstractFunction;

import java.awt.*;
import java.util.LinkedHashSet;

public class ConditionalDisplayable extends AbstractDisplayable
{
    private Displayable defaultDisplayable;
    private LinkedHashSet<ConditionalTuple> conditionals;

    public ConditionalDisplayable(Point origin, int height, Displayable defaultDisplayable)
    {
        super(origin, new Dimension(defaultDisplayable.getSize()), height);
        this.defaultDisplayable = defaultDisplayable;
        this.conditionals = new LinkedHashSet<>();
    }

    public void add(TypedAbstractFunction<Boolean> condition, Displayable displayable)
    {
        conditionals.add(new ConditionalTuple(condition, displayable));
    }

    @Override
    public void update()
    {
        defaultDisplayable.update();
        conditionals.forEach(ConditionalTuple::update);
    }

    public void do_draw(Graphics2D g2d)
    {
        for(ConditionalTuple tuple : conditionals)
        {
            if(tuple.attemptToDraw(g2d))
            {
                return;
            }
        }
        defaultDisplayable.draw(g2d);
    }


    private class ConditionalTuple
    {
        private Displayable displayable;
        private TypedAbstractFunction<Boolean> condition;

        public ConditionalTuple(TypedAbstractFunction<Boolean> condition, Displayable displayable)
        {
            this.condition = condition;
            this.displayable = displayable;
        }

        public void update()
        {
            displayable.update();
        }

        public boolean attemptToDraw(Graphics2D g2d)
        {
            if(condition.execute())
            {
                displayable.draw(g2d);
                return true;
            }
            else return false;
        }
    }

}
