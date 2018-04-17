package savingloading;

import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import commands.reversiblecommands.ReversibleCommand;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import commands.skillcommands.*;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.AI.PatrolAI;
import entity.entitycontrol.AI.PetAI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.*;
import entity.entitymodel.interactions.*;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import items.InteractiveItem;
import items.OneshotItem;
import items.takeableitems.*;
import maps.tile.Tile;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import items.Item;
import org.json.*;
import skills.SkillType;
import utilities.Coordinate;

import java.util.*;

/**
 * Created by dontf on 4/13/2018.
 */
public class SaveVisitor implements Visitor {

    private String fileName;
    private JSONObject saveFileJson;
    private JSONObject playerJson;
    private JSONObject overWorldJson;
    private List<JSONObject> localWorldJsons;

    private JSONObject currentEntityJson;
    private Queue<JSONObject> itemJsonsQueue;
    private JSONObject currentCommand;

    public SaveVisitor(String saveFileName){
        this.fileName = "resources/savefiles/" + saveFileName + ".json";
        saveFileJson = new JSONObject();
        playerJson = new JSONObject();
        overWorldJson = new JSONObject();
        localWorldJsons = new ArrayList<JSONObject>();
        itemJsonsQueue = new ArrayDeque<>();
    }

    private JSONObject getCurrentLocalWorldJson(){
        if(!localWorldJsons.isEmpty())
            return localWorldJsons.get(localWorldJsons.size()-1);
        else
            return null;
    }

    @Override
    public void visitTile(Tile t) {

    }

    @Override
    public void visitEntity(Entity e) {
        e.getController().accept(this);
        currentEntityJson.put("DirectionFacing", e.getMovementDirection());
        e.getStats().accept(this);
        currentEntityJson.put("ActorInteractions", new JSONArray());
        for (EntityInteraction ei : e.getActorInteractions())
            ei.accept(this);
        currentEntityJson.put("ActeeInteractions", new JSONArray());
        for (EntityInteraction ei : e.getActeeInteractions())
            ei.accept(this);
        e.getInventory().accept(this);
    }

    public void visitVehicle(Vehicle v){
        addNonPlayerEntity("Vehicle");
    }

    @Override
    public void visitHumanEntityController(HumanEntityController h) {
        addPlayerEntity();
        addInVehicle(h.isInVehicle());
        addCoordinates(h.getEntityLocation());
        h.getEquipment().accept(this);
    }

    @Override
    public void visitNpcEntityController(NpcEntityController n) {
        n.getAi().accept(this);
        addInVehicle(n.isInVehicle());
        addCoordinates(n.getEntityLocation());
        n.getEquipment().accept(this);
    }

    @Override
    public void visitFriendlyAI(FriendlyAI f) {
        addNonPlayerEntity("Friendly");
    }

    @Override
    public void visitHostileAI(HostileAI h) {
        addNonPlayerEntity("Hostile");
    }

    @Override
    public void visitPatrolAI(PatrolAI p) {
        addNonPlayerEntity("Patrol");
    }

    @Override
    public void visitPetAI(PetAI p) {
        addNonPlayerEntity("Vehicle");
    }

    @Override
    public void visitEntityStats(EntityStats entityStats) {
        JSONObject statsJson = new JSONObject();
        JSONObject skillsJson = new JSONObject();
        Map<SkillType, Integer> skills = entityStats.getSkills();
        for (Map.Entry<SkillType, Integer> entry : skills.entrySet()) {
            skillsJson.put(entry.getKey().toString(), entry.getValue());
        }
        statsJson.put("Skills", skillsJson);
        statsJson.put("BaseMoveSpeed", entityStats.getBaseMoveSpeed());
        statsJson.put("MaxHealth", entityStats.getMaxHealth());
        statsJson.put("CurrentHealth", entityStats.getCurHealth());
        statsJson.put("MaxMana", entityStats.getMaxMana());
        statsJson.put("CurrentMana", entityStats.getCurMana());
        statsJson.put("CurrentXP", entityStats.getCurXP());
        statsJson.put("UnspentSkillPoints", entityStats.getUnspentSkillPoints());
        statsJson.put("VisibilityRadius", entityStats.getVisibilityRadius());
        statsJson.put("Concealment", entityStats.getConcealment());
        statsJson.put("Gold", entityStats.getGold());
        currentEntityJson.put("Stats", statsJson);
    }

    private void addPlayerEntity(){
        playerJson.put("Type", "Player");
        currentEntityJson = playerJson;
    }

    private void addNonPlayerEntity(String type){
        JSONArray entitiesJson = getCurrentLocalWorldJson().getJSONArray("Entities");
        JSONObject entityJson = new JSONObject();
        entityJson.put("Type",type);
        entitiesJson.put(entityJson);
        currentEntityJson = entityJson;
    }

    private void addInVehicle(boolean inVehicle){
        currentEntityJson.put("InVehicle", inVehicle);
    }

    private void addCoordinates(Coordinate coordinate){
        currentEntityJson.put("XCoordinate", coordinate.x());
        currentEntityJson.put("YCoordinate", coordinate.y());
    }

    @Override
    public void visitInventory(Inventory inventory) {
        JSONArray inventoryJson = new JSONArray();
        currentEntityJson.put("Inventory", inventoryJson);
        for(TakeableItem i : inventory.getItems())
            i.accept(this);
        while(!itemJsonsQueue.isEmpty())
            inventoryJson.put(itemJsonsQueue.remove());
    }

    @Override
    public void visitEquipment(Equipment equipment) {
        JSONObject equipmentJson = new JSONObject();
        currentEntityJson.put("Equipment", equipmentJson);
        JSONArray weaponItemsJson = new JSONArray();
        equipmentJson.put("Weapons", weaponItemsJson);
        JSONArray wearableItemsJson = new JSONArray();
        equipmentJson.put("Wearables", wearableItemsJson);
        for(WeaponItem i : equipment.getWeapons())
            i.accept(this);
        while(!itemJsonsQueue.isEmpty())
            weaponItemsJson.put(itemJsonsQueue.remove());
        for (Map.Entry<EquipSlot, WearableItem> entry : equipment.getWearables().entrySet())
            entry.getValue().accept(this);
        while(!itemJsonsQueue.isEmpty())
            wearableItemsJson.put(itemJsonsQueue.remove());
    }

    @Override
    public void visitMountInteraction(MountInteraction m) {
        if(currentEntityJson.has("ActeeInteractions"))
            addActeeInteraction("MountInteraction");
        else if(currentEntityJson.has("ActorInteractions"))
            addActorInteraction("MountInteraction");
    }

    @Override
    public void visitBackStabInteraction(BackStabInteraction b) {
        if(currentEntityJson.has("ActeeInteractions"))
            addActeeInteraction("BackStabInteraction");
        else if(currentEntityJson.has("ActorInteractions"))
            addActorInteraction("BackStabInteraction");
    }

    @Override
    public void visitPickPocketInteraction(PickPocketInteraction p) {
        if(currentEntityJson.has("ActeeInteractions"))
            addActeeInteraction("PickPocketInteraction");
        else if(currentEntityJson.has("ActorInteractions"))
            addActorInteraction("PickPocketInteraction");
    }

    @Override
    public void visitTalkInteraction(TalkInteraction t) {
        if(currentEntityJson.has("ActeeInteractions"))
            addActeeInteraction("TalkInteraction");
        else if(currentEntityJson.has("ActorInteractions"))
            addActorInteraction("TalkInteraction");
    }

    @Override
    public void visitTradeInteraction(TradeInteraction t) {
        if(currentEntityJson.has("ActeeInteractions"))
            addActeeInteraction("TradeInteraction");
        else if(currentEntityJson.has("ActorInteractions"))
            addActorInteraction("TradeInteraction");
    }

    @Override
    public void visitUseItemInteraction(UseItemInteraction u) {
        if(currentEntityJson.has("ActeeInteractions"))
            addActeeInteraction("UseItemInteraction");
        else if(currentEntityJson.has("ActorInteractions"))
            addActorInteraction("UseItemInteraction");
    }

    private void addActorInteraction(String type){
        JSONArray actorInteractionsJson = currentEntityJson.getJSONArray("ActorInteractions");
        actorInteractionsJson.put(type);
    }

    private void addActeeInteraction(String type){
        JSONArray acteeInteractionsJson = currentEntityJson.getJSONArray("ActorInteractions");
        acteeInteractionsJson.put(type);
    }

    @Override
    public void visitItem(Item i) {

    }

    @Override
    public void visitInteractiveItem(InteractiveItem i) {
        JSONObject interactiveItemJson = new JSONObject();
        interactiveItemJson.put("Type", "Interactive");
        interactiveItemJson.put("Name", i.getName());
        i.getCommand().accept(this);
        interactiveItemJson.put("Command", currentCommand);
        itemJsonsQueue.add(interactiveItemJson);
    }

    @Override
    public void visitOneShotItem(OneshotItem o) {
        JSONObject oneShotItemJson = new JSONObject();
        oneShotItemJson.put("Type", "OneShot");
        oneShotItemJson.put("Name", o.getName());
        o.getCommand().accept(this);
        oneShotItemJson.put("Command", currentCommand);
        oneShotItemJson.put("IsActive", o.isActive());
        itemJsonsQueue.add(oneShotItemJson);
    }

    @Override
    public void visitQuestItem(QuestItem q) {
        JSONObject questItemJson = new JSONObject();
        questItemJson.put("Type", "Quest");
        questItemJson.put("Name", q.getName());
        questItemJson.put("QuestId", q.getQuestId());
        itemJsonsQueue.add(questItemJson);
    }

    @Override
    public void visitConsumableItem(ConsumableItem c) {
        JSONObject consumableItemJson = new JSONObject();
        consumableItemJson.put("Type", "Consumable");
        consumableItemJson.put("Name", c.getName());
        c.getCommand().accept(this);
        consumableItemJson.put("Command", currentCommand);
        itemJsonsQueue.add(consumableItemJson);
    }

    @Override
    public void visitWeaponItem(WeaponItem w) {
        JSONObject weaponItemJson = new JSONObject();
        weaponItemJson.put("Type", "Weapon");
        weaponItemJson.put("Name", w.getName());
        w.getCommand().accept(this);
        weaponItemJson.put("Command", currentCommand);
        weaponItemJson.put("Damage", w.getDamage());
        weaponItemJson.put("AttackSpeed", w.getAttackSpeed());
        weaponItemJson.put("RequiredSkill", w.getRequiredSkill());
        itemJsonsQueue.add(weaponItemJson);
    }

    @Override
    public void visitWearableItem(WearableItem w) {
        JSONObject wearableItemJson = new JSONObject();
        wearableItemJson.put("Type", "Wearable");
        wearableItemJson.put("Name", w.getName());
        w.getCommand().accept(this);
        wearableItemJson.put("ReversableCommand", currentCommand);
        wearableItemJson.put("EquipType", w.getEquipType());
        itemJsonsQueue.add(wearableItemJson);
    }

    @Override
    public void visitConfuseCommand(ConfuseCommand confuseCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "ConfuseCommand");
        addSkillCommand(confuseCommand);
    }

    @Override
    public void visitMakeFriendlyCommand(MakeFriendlyCommand makeFriendlyCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "MakeFriendly");
        addSkillCommand(makeFriendlyCommand);
    }

    @Override
    public void visitModifyHealthCommand(ModifyHealthCommand modifyHealthCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "ModifyHealth");
        addSkillCommand(modifyHealthCommand);
    }

    @Override
    public void visitModifyStaminaRegenCommand(ModifyStaminaRegenCommand modifyStaminaRegenCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "ModifyStaminaRegen");
        addSkillCommand(modifyStaminaRegenCommand);
    }

    @Override
    public void visitObserveCommand(ObserveCommand observeCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "Observe");
        addSkillCommand(observeCommand);
    }

    @Override
    public void visitParalyzeCommand(ParalyzeCommand paralyzeCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "Paralyze");
        addSkillCommand(paralyzeCommand);
    }

    @Override
    public void visitPickPocketCommand(PickPocketCommand pickPocketCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "PickPocket");
        addSkillCommand(pickPocketCommand);
    }

    @Override
    public void visitMakeConfusedCommand(MakeConfusedCommand makeConfusedCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "MakeConfused");
        currentCommand.put("VisibilityDecreaseAmount", makeConfusedCommand.getVisibilityDecreaseAmount());
        addReversibleCommand(makeConfusedCommand);
    }

    @Override
    public void visitMakeParalyzedCommand(MakeParalyzedCommand makeParalyzedCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "MakeParalyzed");
        addReversibleCommand(makeParalyzedCommand);
    }

    @Override
    public void visitTimedStaminaRegenCommand(TimedStaminaRegenCommand timedStaminaRegenCommand) {
        currentCommand = new JSONObject();
        currentCommand.put("Name", "TimedStaminaRegen");
        currentCommand.put("StaminaRegenDecrease", timedStaminaRegenCommand.getStaminaRegenDecrease());
        addReversibleCommand(timedStaminaRegenCommand);
    }

    private void addSkillCommand(SkillCommand skillCommand){
        currentCommand.put("Level", skillCommand.getLevel());
        currentCommand.put("SkillType", skillCommand.getSkillType());
        currentCommand.put("Effectiveness", skillCommand.getEffectiveness());
    }

    private void addReversibleCommand(ReversibleCommand reversibleCommand){
        currentCommand.put("IsApplied", reversibleCommand.isApplied());
    }

    @Override
    public void visitWorld(World w) {

    }

    public void visitOverWorld(OverWorld w){

    }

    public void visitLocalWorld(LocalWorld w){

    }

    @Override
    public void visitGame(Game g) {
        visitEntity(g.getPlayer());
        visitOverWorld(g.getOverWorld());
        for (LocalWorld localWorld : g.getLocalWorlds())
            visitLocalWorld(localWorld);
    }
}
