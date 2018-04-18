package commands;

import entity.entitymodel.Entity;
import maps.world.TransitionObserver;
import maps.world.World;
import savingloading.Visitor;
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

    public World getTargetWorld() {
        return targetWorld;
    }

    public Coordinate getStartingCoordinate() {
        return startingCoordinate;
    }

    @Override
    public void accept(Visitor v) {
        v.visitTransitionCommand(this);
    }
}
