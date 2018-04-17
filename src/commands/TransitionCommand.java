package commands;

import entity.entitymodel.Entity;
import maps.world.TransitionObserver;
import maps.world.World;
import utilities.Coordinate;

public class TransitionCommand implements Command {

    private World targetWorld;
    private Coordinate startingCoordinate;
    private TransitionObserver transitionObserver;

    public TransitionCommand(World targetWorld, Coordinate startingCoordinate, TransitionObserver transitionObserver) {
        this.targetWorld = targetWorld;
        this.startingCoordinate = startingCoordinate;
        this.transitionObserver = transitionObserver;
    }

    public void trigger(Entity e) {
        transitionObserver.notifyTransition(e, targetWorld, startingCoordinate);
    }
}
