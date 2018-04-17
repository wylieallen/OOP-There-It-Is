package savingloading;

import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import commands.skillcommands.*;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.AI.PatrolAI;
import entity.entitycontrol.AI.PetAI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Equipment;
import entity.entitymodel.Inventory;
import entity.entitymodel.interactions.*;
import entity.vehicle.Vehicle;
import items.InteractiveItem;
import items.OneshotItem;
import items.takeableitems.ConsumableItem;
import items.takeableitems.QuestItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.tile.Tile;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import maps.world.Game;
import items.Item;

/**
 * Created by dontf on 4/13/2018.
 */
public interface Visitor {

    void visitTile (Tile t);

    void visitEntity (Entity e);
    void visitHumanEntityController(HumanEntityController h);
    void visitNpcEntityController(NpcEntityController npcEntityController);
    void visitVehicle(Vehicle vehicle);
    void visitFriendlyAI(FriendlyAI f);
    void visitHostileAI(HostileAI h);
    void visitPatrolAI(PatrolAI p);
    void visitPetAI(PetAI p);
    void visitEntityStats(EntityStats entityStats);
    void visitInventory(Inventory inventory);
    void visitEquipment(Equipment equipment);

    void visitMountInteraction(MountInteraction m);
    void visitBackStabInteraction(BackStabInteraction b);
    void visitPickPocketInteraction(PickPocketInteraction p);
    void visitTalkInteraction(TalkInteraction t);
    void visitTradeInteraction(TradeInteraction t);
    void visitUseItemInteraction(UseItemInteraction u);

    void visitItem (Item i);
    void visitInteractiveItem(InteractiveItem i);
    void visitOneShotItem(OneshotItem o);
    void visitQuestItem(QuestItem q);
    void visitConsumableItem(ConsumableItem c);
    void visitWeaponItem(WeaponItem w);
    void visitWearableItem(WearableItem w);

    void visitConfuseCommand(ConfuseCommand confuseCommand);
    void visitMakeFriendlyCommand(MakeFriendlyCommand makeFriendlyCommand);
    void visitModifyHealthCommand(ModifyHealthCommand modifyHealthCommand);
    void visitModifyStaminaRegenCommand(ModifyStaminaRegenCommand modifyStaminaRegenCommand);
    void visitObserveCommand(ObserveCommand observeCommand);
    void visitParalyzeCommand(ParalyzeCommand paralyzeCommand);
    void visitPickPocketCommand(PickPocketCommand pickPocketCommand);
    void visitMakeConfusedCommand(MakeConfusedCommand makeConfusedCommand);
    void visitMakeParalyzedCommand(MakeParalyzedCommand makeParalyzedCommand);
    void visitTimedStaminaRegenCommand(TimedStaminaRegenCommand timedStaminaRegenCommand);

    void visitWorld (World w);
    void visitOverWorld(OverWorld w);
    void visitLocalWorld(LocalWorld w);

    void visitGame (Game g);
}
