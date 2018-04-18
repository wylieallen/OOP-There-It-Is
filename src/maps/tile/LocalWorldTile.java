package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.entityimpaction.EntityImpactor;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.trajectorymodifier.TrajectoryModifier;
import utilities.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LocalWorldTile extends Tile {

    private Set<TrajectoryModifier> trajectoryModifiers;
    private Set<EntityImpactor> entityImpactors;

    public LocalWorldTile(Set<MoveLegalityChecker> mlcs, Entity entity, Set<TrajectoryModifier> tms,
                          Set<EntityImpactor> eis) {
        super(mlcs, entity);
        trajectoryModifiers = tms;
        entityImpactors = eis;
    }

    @Override
    public Collection<GameObject> getGameObjects() {
        Set<GameObject> list = new HashSet<>();
        list.addAll(trajectoryModifiers);
        list.addAll(super.getMoveLegalityCheckers());
        list.addAll(entityImpactors);
        return list;
    }

    public void update()
    {
        super.do_update();
        trajectoryModifiers.forEach(GameObject::update);
        entityImpactors.forEach(GameObject::update);
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
    public void do_moves(Collection<MoveLegalityChecker> updated){
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
