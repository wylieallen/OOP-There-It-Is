package maps.trajectorymodifier;

public class River implements TrajectoryModifier {

    private Vector force;

    public River(Vector force) {
        this.force = force;
    }

    @Override
    public Vector getVector() {
        return force;
    }
}
