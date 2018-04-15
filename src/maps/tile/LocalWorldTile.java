package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.entityimpaction.EntityImpactor;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.trajectorymodifier.TrajectoryModifier;
import utilities.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalWorldTile extends Tile {

    private Set<TrajectoryModifier> trajectoryModifiers;
    private Set<EntityImpactor> entityImpactors;

    /*public LocalWorldTile()
    {
        trajectoryModifiers = new HashSet<>();
        moveLegalityCheckers = new HashSet<>();
        entityImpactors = new HashSet<>();
    }*/

    public LocalWorldTile(Set<MoveLegalityChecker> mlcs, Entity entity, Set<TrajectoryModifier> tms,
                          Set<EntityImpactor> eis) {
        super(mlcs, entity);
        trajectoryModifiers = tms;
        entityImpactors = eis;
    }

    @Override
    public List<GameObject> getGameObjects() {
        List<GameObject> list = new ArrayList<>();
        list.addAll(trajectoryModifiers);
        list.addAll(super.getMoveLegalityCheckers());
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
        Vector total = new Vector();
        for(TrajectoryModifier tm: trajectoryModifiers) {
            total.add(tm.getVector());
        }

        super.do_moves(total);
    }

    protected void do_interactions(){
        //
    }
}
