package maps.tile;

import gameobject.GameObject;
import maps.entityimpaction.EntityImpactor;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.trajectorymodifier.TrajectoryModifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Override
    public List<GameObject> getGameObjects() {
        List<GameObject> list = new ArrayList<>();
        list.addAll(trajectoryModifiers);
        list.addAll(moveLegalityCheckers);
        list.addAll(entityImpactors);
        return list;
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
