package maps.tile;

import maps.entityimpaction.EntityImpactor;
import maps.trajectorymodifier.TrajectoryModifier;

import java.util.HashSet;
import java.util.Set;

public class LocalWorldTile extends Tile {

    private Set<TrajectoryModifier> trajectoryModifiers;
    private Set<MoveLegalityChecker> moveLegalityCheckers;
    private Set<EntityImpactor> entityImpactors;

    public LocalWorldTile()
    {
        trajectoryModifiers = new HashSet<>();
        moveLegalityCheckers = new HashSet<>();
        entityImpactors = new HashSet<>();
    }

    public void addTM(TrajectoryModifier tm){
        //
    }

    public void addMLC(MoveLegalityChecker mlc){
        //
    }

    public void addEI(EntityImpactor ei){
        //
    }

    protected void do_moves(){
        //
    }

    protected void do_interactions(){
        //
    }
}
