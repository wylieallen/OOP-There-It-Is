package maps.entityimpaction;

import entity.entitymodel.Entity;
import commands.Command;
import maps.movelegalitychecker.MoveLegalityChecker;
import savingloading.Visitor;
import skills.SkillType;

public class Trap implements EntityImpactor, MoveLegalityChecker {

    private Command command;
    private boolean hasFired;
    private int strength;
    private boolean isVisible;

    public Trap(Command command, boolean hasFired, int strength, boolean isVisible) {
        this.command = command;
        this.hasFired = hasFired;
        this.strength = strength;
        this.isVisible = isVisible;
    }

    public boolean canMoveHere(Entity entity) {
        if(entity.isSearching()) {
            if(!isVisible) {
                isVisible = true;
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void touch(Entity entity) {
        if(!hasFired) {
            if(entity.isSearching()) {
                attemptDisarm(entity);
            } else {
                command.trigger(entity);
            }
        }
        hasFired = true;
    }

    public void attemptDisarm(Entity entity){
        isVisible = true;
        hasFired = true;
        boolean success = SkillType.DETECTANDREMOVETRAP.checkSuccess(entity.getSkillLevel(SkillType.DETECTANDREMOVETRAP), strength);
        if(!success) {

            command.trigger(entity);
        }
    }

    public boolean hasFired() {
        return hasFired;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }

    @Override
    public void accept(Visitor v) {
        v.visitTrap(this);
    }
}
