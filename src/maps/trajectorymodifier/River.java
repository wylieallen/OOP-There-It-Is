package maps.trajectorymodifier;

import savingloading.Visitor;
import utilities.Vector;

public class River implements TrajectoryModifier {

    private Vector force;

    public River(Vector force) {
        this.force = force;
    }

    @Override
    public Vector getVector() {
        return force;
    }

    @Override
    public void accept(Visitor v) {
        v.visitRiver(this);
    }
}
