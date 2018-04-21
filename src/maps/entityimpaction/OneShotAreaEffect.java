package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;
import savingloading.Visitor;

public class OneShotAreaEffect extends AreaEffect {

    private boolean hasFired;

    public OneShotAreaEffect(Command command, boolean hasFired, String name) {
        super(command, name);
        this.hasFired = hasFired;
    }

    public void touch(Entity entity) {
        if(!hasFired) {
            trigger(entity);
            hasFired = true;
        }
    }

    @Override
    public boolean shouldBeRemoved() {
        return hasFired;
    }

    @Override
    public void accept(Visitor v) {
        v.visitOneShotAreaEffect(this);
    }
}
