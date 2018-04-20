package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;
import savingloading.Visitor;

public class InfiniteAreaEffect extends AreaEffect {

    public InfiniteAreaEffect(Command command, String name) {
        super(command, name);
    }

    public void touch(Entity entity) {
        trigger(entity);
    }

    @Override
    public void accept(Visitor v) {
        v.visitInfiniteAreaEffect(this);
    }
}
