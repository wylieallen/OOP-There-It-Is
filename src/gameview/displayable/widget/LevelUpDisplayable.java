package gameview.displayable.widget;

import entity.entitymodel.Entity;
import gameview.util.ImageMaker;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;
import skills.SkillType;

import java.awt.*;

public class LevelUpDisplayable extends CompositeDisplayable
{
    private Displayable background = new ImageDisplayable(new Point(0, 0), ImageMaker.makeBorderedRect(128 + 48, 256 - 12, Color.WHITE), -1);

    private int cursorIndex;
    private Entity entity;

    public LevelUpDisplayable(Point origin, Entity entity)
    {
        super(origin, 1);
        this.entity = entity;
        cursorIndex = -1;
        update();
    }

    @Override
    public void update()
    {
        super.clear();
        add(background);
        add(new StringDisplayable(new Point(4, 16), () -> entity.getUnusedSkillPoints() + " Skill Points remaining", Color.BLACK, 1));
        int i = 0;
        for(SkillType skill : SkillType.values())
        {
            if(skill != SkillType.NULL && entity.getSkillLevel(skill) >= 0)//entity.getSkillLevel(skill) >= 0)
            {
                int yMultiplier = i;
                add(new StringDisplayable(new Point(4, 16 + 16 + (yMultiplier * 16)),
                        () -> skill + ": " + entity.getSkillLevel(skill), Color.BLACK, 1));
                ++i;
            }
        }
        if(cursorIndex > -1)
        {
            add(new ImageDisplayable(calculateCursorPoint(), ImageMaker.makeRightPointingTriangle(), 1000));
        }
        super.update();
    }

    private Point calculateCursorPoint()
    {
        int x = -4, y;

        y = cursorIndex * 16 + 16 + 8;

        return new Point(x, y);
    }

    public void modifyIndex(int delta)
    {
        cursorIndex += delta;
        int cursorMax = SkillType.values().length - 1 - 1;
        if(cursorIndex < 0)
        {
            cursorIndex = cursorMax;
        }
        else if (cursorIndex > cursorMax)
        {
            cursorIndex = 0;
        }
    }

    public int getCursorIndex() { return cursorIndex; }
}
