package Tiles;

import EntityImpaction.EntityImpactor;
import trajectorymodifiers.TrajectoryModifier;

import java.util.List;

public class LocalWorldTile extends Tile {

    private List<TrajectoryModifier> tMs;
    private List<MoveLegalityChecker> mLCs;
    private List<EntityImpactor> eIs;

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
