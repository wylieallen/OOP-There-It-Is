package commands;

import entity.entitymodel.Entity;
import savingloading.Visitor;

public class LevelUpCommand implements Command{
    public LevelUpCommand() {
    }

    @Override
    public void trigger(Entity e) {
        e.levelUp();
    }

    @Override
    public void trigger(Entity e, int amount) {
        trigger(e);
    }

    @Override
    public void accept(Visitor v) {
        v.visitLevelUpCommand(this);
    }
}
