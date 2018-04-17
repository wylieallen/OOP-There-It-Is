package items.takeableitems;

import commands.Command;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import maps.Influence.InfluenceArea;
import skills.SkillType;
import spawning.SpawnObservable;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.util.List;

public class WeaponItem extends TakeableItem implements SpawnObservable {

    private Command command;
    private int damage;
    private int attackSpeed;
    private SkillType requiredSkill;
    private List<SpawnObserver> spawnObservers;

    public WeaponItem(String name, boolean onMap, Command command, int damage, int attackSpeed,
                      SkillType requiredSkill) {
        super(name, onMap);
        this.command = command;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.requiredSkill = requiredSkill;
    }

    @Override
    public void activate(Equipment e) {
        e.add(this);
    }

    public void attack(Entity attacker, Coordinate location) {

    }

    public void notifyAllOfSpawn (InfluenceArea IA){
        for(SpawnObserver so: spawnObservers) {
            so.notifySpawn(IA, this);
        }
    }

    public void registerObserver (SpawnObserver SO){
        spawnObservers.add(SO);
    }

    public void deregisterObserver (SpawnObserver SO) {
        spawnObservers.remove(SO);
    }
}
