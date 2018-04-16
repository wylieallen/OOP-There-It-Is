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
        trajectoryModifiers.add(tm);
    }

    public void addMLC(MoveLegalityChecker mlc){
        super.addMLC(mlc);
    }

    public void addEI(EntityImpactor ei){
        entityImpactors.add(ei);
    }

    @Override
    public void do_update() {
        super.do_update();
        for(TrajectoryModifier tm : trajectoryModifiers) {
            tm.update();
        }
        for(EntityImpactor ei: entityImpactors) {
            ei.update();
        }
    }

    @Override
    public void do_moves(Set<MoveLegalityChecker> updated){
        Vector total = new Vector();
        for(TrajectoryModifier tm: trajectoryModifiers) {
            total.add(tm.getVector());
        }

        super.do_moves(updated, total);
    }

    @Override
    public void do_interactions(){
        if(super.hasEntity()){
            for(EntityImpactor ei: entityImpactors) {
                ei.touch(super.getEntity());
            }
        }

    }
}
