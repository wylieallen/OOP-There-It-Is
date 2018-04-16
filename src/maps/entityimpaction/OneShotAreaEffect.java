package maps.entityimpaction;

import entity.entitymodel.Entity;

public class OneShotAreaEffect extends AreaEffect {

    private boolean hasFired;

    public OneShotAreaEffect(boolean hasFired) {
        this.hasFired = hasFired;
    }

    public void touch(Entity entity) {
        if(!hasFired) {
            trigger(entity);
            hasFired = true;
        }
    }
}
