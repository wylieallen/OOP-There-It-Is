package maps.Influence;

import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import commands.skillcommands.*;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.AI.PatrolAI;
import entity.entitycontrol.AI.PetAI;
import entity.entitycontrol.HumanEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.*;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import items.InteractiveItem;
import items.OneshotItem;
import items.takeableitems.ConsumableItem;
import items.takeableitems.QuestItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import savingloading.Visitor;
import maps.tile.Tile;
import utilities.Coordinate;
import maps.world.Game;
import maps.world.World;
import items.Item;

import java.util.List;

/**
 * Created by dontf on 4/14/2018.
 */
public class InfluenceArea implements Visitor {

    private InfluenceType influenceType;
    private Coordinate center;
    private List<GameObject> whiteList;
    private long lastUpdateTime;
    private List <Coordinate> offsetPoints;
    private long updateInterval;
    private SkillCommand skillCommand;



    public void update (int curTime) {

    }

    public void checkArea () {

    }

    public void gatherPoints (InfluenceType influenceType) {

    }

    @Override
    public void visitTile(Tile t) {

    }

    @Override
    public void visitEntity(Entity e) {

    }

    @Override
    public void visitHumanEntityController(HumanEntityController h) {

    }

    @Override
    public void visitVehicle(Vehicle vehicle) {

    }

    @Override
    public void visitFriendlyAI(FriendlyAI f) {

    }

    @Override
    public void visitHostileAI(HostileAI h) {

    }

    @Override
    public void visitPatrolAI(PatrolAI p) {

    }

    @Override
    public void visitPetAI(PetAI p) {

    }

    @Override
    public void visitEntityStats(EntityStats entityStats) {

    }

    @Override
    public void visitInventory(Inventory inventory) {

    }

    @Override
    public void visitMountInteraction(MountInteraction m) {

    }

    @Override
    public void visitBackStabInteraction(BackStabInteraction b) {

    }

    @Override
    public void visitPickPocketInteraction(PickPocketInteraction p) {

    }

    @Override
    public void visitTalkInteraction(TalkInteraction t) {

    }

    @Override
    public void visitTradeInteraction(TradeInteraction t) {

    }

    @Override
    public void visitUseItemInteraction(UseItemInteraction u) {

    }

    @Override
    public void visitItem(Item i) {

    }

    @Override
    public void visitInteractiveItem(InteractiveItem i) {

    }

    @Override
    public void visitOneShotItem(OneshotItem o) {

    }

    @Override
    public void visitQuestItem(QuestItem q) {

    }

    @Override
    public void visitConsumableItem(ConsumableItem c) {

    }

    @Override
    public void visitWeaponItem(WeaponItem w) {

    }

    @Override
    public void visitWearableItem(WearableItem w) {

    }

    @Override
    public void visitConfuseCommand(ConfuseCommand confuseCommand) {

    }

    @Override
    public void visitMakeFriendlyCommand(MakeFriendlyCommand makeFriendlyCommand) {

    }

    @Override
    public void visitModifyHealthCommand(ModifyHealthCommand modifyHealthCommand) {

    }

    @Override
    public void visitModifyStaminaRegenCommand(ModifyStaminaRegenCommand modifyStaminaRegenCommand) {

    }

    @Override
    public void visitObserveCommand(ObserveCommand observeCommand) {

    }

    @Override
    public void visitParalyzeCommand(ParalyzeCommand paralyzeCommand) {

    }

    @Override
    public void visitPickPocketCommand(PickPocketCommand pickPocketCommand) {

    }

    @Override
    public void visitMakeConfusedCommand(MakeConfusedCommand makeConfusedCommand) {

    }

    @Override
    public void visitMakeParalyzedCommand(MakeParalyzedCommand makeParalyzedCommand) {

    }

    @Override
    public void visitTimedStaminaRegenCommand(TimedStaminaRegenCommand timedStaminaRegenCommand) {

    }

    @Override
    public void visitWorld(World w) {

    }

    @Override
    public void visitOverWorld(OverWorld w) {

    }

    @Override
    public void visitLocalWorld(LocalWorld w) {

    }

    @Override
    public void visitGame(Game g) {

    }

}
