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

    public LocalWorldTile()
    {
        trajectoryModifiers = new HashSet<>();
        entityImpactors = new HashSet<>();
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
        super.update(super.getMoveLegalityCheckers());
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

    protected void do_moves(Collection<MoveLegalityChecker> updated){
        Vector total = new Vector();
        for(TrajectoryModifier tm: trajectoryModifiers) {
            total.add(tm.getVector());
        }

        super.do_moves(updated, total);
    }

    protected void do_interactions(Entity entity){
        for(EntityImpactor ei: entityImpactors) {
            ei.touch(entity);
        }
    }
}
