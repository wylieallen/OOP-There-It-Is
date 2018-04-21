package maps.tile;

import entity.entitymodel.Entity;
import gameobject.GameObject;
import maps.Influence.InfluenceArea;
import maps.entityimpaction.EntityImpactor;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Terrain;
import maps.trajectorymodifier.TrajectoryModifier;
import savingloading.Visitor;
import utilities.Coordinate;
import utilities.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LocalWorldTile extends Tile {

    private Set<TrajectoryModifier> trajectoryModifiers;
    private Set<EntityImpactor> entityImpactors;

    public LocalWorldTile(Set<MoveLegalityChecker> mlcs, Terrain terrain, Entity entity, Set<TrajectoryModifier> tms,
                          Set<EntityImpactor> eis) {
        super(mlcs, terrain, entity);
        trajectoryModifiers = tms;
        entityImpactors = eis;
    }

    @Override
    public Collection<GameObject> getGameObjects() {
        Set<GameObject> list = new HashSet<>();
        list.addAll(trajectoryModifiers);
        list.addAll(super.getMoveLegalityCheckers());
        list.add(super.getTerrain());
        list.addAll(entityImpactors);
        return list;
    }

    public void addTM(TrajectoryModifier tm){
        trajectoryModifiers.add(tm);
    }

    public void addEI(EntityImpactor ei){
        entityImpactors.add(ei);
    }

    @Override
    public void do_update() {
        super.do_update();
        trajectoryModifiers.forEach(GameObject::update);
        entityImpactors.forEach(GameObject::update);
        //TODO: add logic to check if each TM and EI expired
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

        entityImpactors.removeIf(EntityImpactor::shouldBeRemoved);
    }

    public boolean has(EntityImpactor item){
        for(EntityImpactor ei: entityImpactors){
            if(item == ei)
                return true;
        }
        return false;
    }

    @Override
    public boolean has(GameObject o) {
        if(super.has(o)) {
            return true;
        } else if (trajectoryModifiers.contains(o)) {
            return true;
        } else if (entityImpactors.contains(o)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visitLocalWorldTile(this);
    }

    public boolean hasImpactor () { return entityImpactors.size() > 0; }
}
