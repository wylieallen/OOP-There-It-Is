package maps.entityimpaction;

import commands.Command;
import entity.entitymodel.Entity;
import maps.entityimpaction.AreaEffect;
import savingloading.Visitor;

public class InfiniteAreaEffect extends AreaEffect {

    public InfiniteAreaEffect(Command command) {
        super(command);
    }

    public void touch(Entity entity) {
        trigger(entity);
    }

    @Override
    public void accept(Visitor v) {
        v.visitInfiniteAreaEffect(this);
    }
}
