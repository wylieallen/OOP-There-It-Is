package savingloading;

import com.sun.jdi.IntegerType;
import commands.TransitionCommand;
import commands.reversiblecommands.MakeConfusedCommand;
import commands.reversiblecommands.MakeParalyzedCommand;
import commands.reversiblecommands.ReversibleCommand;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import commands.skillcommands.*;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.AI.PatrolAI;
import entity.entitycontrol.AI.PetAI;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.*;
import entity.entitymodel.interactions.*;
import entity.vehicle.Vehicle;
import items.InteractiveItem;
import items.OneshotItem;
import items.takeableitems.*;
import maps.entityimpaction.Trap;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Obstacle;
import maps.movelegalitychecker.Terrain;
import maps.tile.LocalWorldTile;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import items.Item;
import org.json.*;
import skills.SkillType;
import utilities.Coordinate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by dontf on 4/13/2018.
 */
public class SaveVisitor implements Visitor {

    private String fileName;
    private JSONObject saveFileJson = new JSONObject();
    private JSONObject playerJson = new JSONObject();
    private JSONObject overWorldJson = new JSONObject();
    private List<JSONObject> localWorldJsons = new ArrayList<JSONObject>();

    private JSONObject currentEntityJson;
    private JSONObject currentCommandJson;
    private JSONObject currentTileJson;
    private Queue<JSONObject> itemJsonsQueue = new ArrayDeque<JSONObject>();
    private Map<TransitionCommand, JSONObject> transitionCommandJsons = new HashMap<TransitionCommand, JSONObject>();

    public SaveVisitor(String saveFileName){
        this.fileName = "resources/savefiles/" + saveFileName + ".json";
    }

    private JSONObject getCurrentLocalWorldJson(){
        if(!localWorldJsons.isEmpty())
            return localWorldJsons.get(localWorldJsons.size()-1);
        else
            return null;
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

    @Override
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
        statsJson.put("ManaRegenRate", entityStats.getManaRegenRate());
        statsJson.put("CurrentXP", entityStats.getCurXP());
        statsJson.put("UnspentSkillPoints", entityStats.getUnspentSkillPoints());
        statsJson.put("VisibilityRadius", entityStats.getVisibilityRadius());
        statsJson.put("Concealment", entityStats.getConcealment());
        statsJson.put("Gold", entityStats.getGold());
        statsJson.put("IsConfused", entityStats.isConfused());
        statsJson.put("IsSearching", entityStats.getIsSearching());
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
    public void visitInteractiveItem(InteractiveItem i) {
        JSONObject interactiveItemJson = new JSONObject();
        interactiveItemJson.put("Type", "Interactive");
        interactiveItemJson.put("Name", i.getName());
        i.getCommand().accept(this);
        interactiveItemJson.put("Command", currentCommandJson);
        itemJsonsQueue.add(interactiveItemJson);
    }

    @Override
    public void visitOneShotItem(OneshotItem o) {
        JSONObject oneShotItemJson = new JSONObject();
        oneShotItemJson.put("Type", "OneShot");
        oneShotItemJson.put("Name", o.getName());
        o.getCommand().accept(this);
        oneShotItemJson.put("Command", currentCommandJson);
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
        consumableItemJson.put("Command", currentCommandJson);
        itemJsonsQueue.add(consumableItemJson);
    }

    @Override
    public void visitWeaponItem(WeaponItem w) {
        JSONObject weaponItemJson = new JSONObject();
        weaponItemJson.put("Type", "Weapon");
        weaponItemJson.put("Name", w.getName());
        w.getCommand().accept(this);
        weaponItemJson.put("Command", currentCommandJson);
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
        wearableItemJson.put("ReversableCommand", currentCommandJson);
        wearableItemJson.put("EquipType", w.getEquipType());
        itemJsonsQueue.add(wearableItemJson);
    }

    @Override
    public void visitConfuseCommand(ConfuseCommand confuseCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "ConfuseCommand");
        addSkillCommand(confuseCommand);
    }

    @Override
    public void visitMakeFriendlyCommand(MakeFriendlyCommand makeFriendlyCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "MakeFriendly");
        addSkillCommand(makeFriendlyCommand);
    }

    @Override
    public void visitModifyHealthCommand(ModifyHealthCommand modifyHealthCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "ModifyHealth");
        addSkillCommand(modifyHealthCommand);
    }

    @Override
    public void visitModifyStaminaRegenCommand(ModifyStaminaRegenCommand modifyStaminaRegenCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "ModifyStaminaRegen");
        addSkillCommand(modifyStaminaRegenCommand);
    }

    @Override
    public void visitObserveCommand(ObserveCommand observeCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Observe");
        addSkillCommand(observeCommand);
    }

    @Override
    public void visitParalyzeCommand(ParalyzeCommand paralyzeCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Paralyze");
        addSkillCommand(paralyzeCommand);
    }

    @Override
    public void visitPickPocketCommand(PickPocketCommand pickPocketCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "PickPocket");
        addSkillCommand(pickPocketCommand);
    }

    @Override
    public void visitMakeConfusedCommand(MakeConfusedCommand makeConfusedCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "MakeConfused");
        addReversibleCommand(makeConfusedCommand);
    }

    @Override
    public void visitMakeParalyzedCommand(MakeParalyzedCommand makeParalyzedCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "MakeParalyzed");
        addReversibleCommand(makeParalyzedCommand);
    }

    @Override
    public void visitTimedStaminaRegenCommand(TimedStaminaRegenCommand timedStaminaRegenCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "TimedStaminaRegen");
        currentCommandJson.put("StaminaRegenDecrease", timedStaminaRegenCommand.getStaminaRegenDecrease());
        addReversibleCommand(timedStaminaRegenCommand);
    }

    @Override
    public void visitTransitionCommand(TransitionCommand transitionCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Transition");
        currentCommandJson.put("TargetX", transitionCommand.getStartingCoordinate().x());
        currentCommandJson.put("TargetY", transitionCommand.getStartingCoordinate().y());
        transitionCommandJsons.put(transitionCommand, currentCommandJson);
        // addTransitionCommandTargetWorld() called at end of visitGame()
    }

    private void addTransitionCommandTargetWorlds(List<World> worlds) {
        for (Map.Entry<TransitionCommand, JSONObject> entry : transitionCommandJsons.entrySet()) {
            World targetWorld = entry.getKey().getTargetWorld();
            for (int i = 0; i < worlds.size(); i++) {
                if (targetWorld == worlds.get(i)) {
                    if (i == 0)
                        entry.getValue().put("TargetWorld", "OverWorld");
                    else
                        entry.getValue().put("TargetWorld", getLocalWorldID(i));
                }
            }
        }
    }

    private void addSkillCommand(SkillCommand skillCommand){
        currentCommandJson.put("Level", skillCommand.getLevel());
        currentCommandJson.put("SkillType", skillCommand.getSkillType());
        currentCommandJson.put("Effectiveness", skillCommand.getEffectiveness());
    }

    private void addReversibleCommand(ReversibleCommand reversibleCommand){
        currentCommandJson.put("IsApplied", reversibleCommand.isApplied());
    }

    public void visitOverWorld(OverWorld w){
        JSONArray tilesJson = new JSONArray();
        overWorldJson.put("Tiles", tilesJson);
        for (Map.Entry<Coordinate, OverWorldTile> entry : w.getTiles().entrySet()) {
            Coordinate c = entry.getKey();
            entry.getValue().accept(this);
            currentTileJson.put("X", c.x());
            currentTileJson.put("Y", c.y());
            tilesJson.put(currentTileJson);
        }
        // TODO: height and width?
    }

    public void visitLocalWorld(LocalWorld w){
        JSONObject localWorldJson = new JSONObject();
        localWorldJsons.add(localWorldJson);
        JSONArray tilesJson = new JSONArray();
        localWorldJson.put("Tiles", tilesJson);
        localWorldJson.put("Entities", new JSONArray());
        for (Map.Entry<Coordinate, LocalWorldTile> entry : w.getTiles().entrySet()) {
            Coordinate c = entry.getKey();
            entry.getValue().accept(this);
            currentTileJson.put("X", c.x());
            currentTileJson.put("Y", c.y());
            tilesJson.put(currentTileJson);
        }
        // TODO: height and width?
    }

    private String getLocalWorldID(int localWorldNumber){
        if(localWorldNumber < 10)
            return "000" + Integer.toString(localWorldNumber);
        else if (localWorldNumber < 100)
            return "00" + Integer.toString(localWorldNumber);
        else if (localWorldNumber < 1000)
            return "0" + Integer.toString(localWorldNumber);
        else
            return Integer.toString(localWorldNumber);
    }

    @Override
    public void visitOverWorldTile(OverWorldTile overWorldTile) {
        addTile(overWorldTile);
    }

    @Override
    public void visitLocalWorldTile(LocalWorldTile localWorldTile) {
        addTile(localWorldTile);
        addItemsToTile();
        // TODO: do InfluenceAreas need to be saved? Or are they assumed to be created when certain items, etc. are made?
        // TODO: do SpawnEvents need to be saved?
    }

    private void addTile(Tile tile){
        currentTileJson = new JSONObject();
        Collection<MoveLegalityChecker> mlcs = tile.getMoveLegalityCheckers();
        // TODO: will the enitity on the Tile be in the entity variable and MLC Collection?
        for (MoveLegalityChecker mlc : mlcs){
            mlc.accept(this);
        }
    }

    private void addItemsToTile(){
        if (!itemJsonsQueue.isEmpty()) {
            JSONArray itemsJson = new JSONArray();
            currentTileJson.put("Items", itemsJson);
            while (!itemJsonsQueue.isEmpty()) {
                itemsJson.put(itemJsonsQueue.remove());
            }
        }
    }

    @Override
    public void visitObstacle(Obstacle obstacle) {
        currentTileJson.put("Obstacle", true);
    }

    @Override
    public void visitTerrain(Terrain terrain) {
        currentTileJson.put("Terrain", terrain);
    }

    @Override
    public void visitTrap(Trap trap) {
        currentTileJson.put("Trap", true);
    }

    @Override
    public void visitGame(Game g) {
        visitEntity(g.getPlayer());
        visitOverWorld(g.getOverWorld());
        for (LocalWorld localWorld : g.getLocalWorlds())
            visitLocalWorld(localWorld);
        addTransitionCommandTargetWorlds(g.getWorlds());

        saveFileJson.put("Player", playerJson);
        saveFileJson.put("OverWorld", overWorldJson);
        JSONObject localWorldsJson = new JSONObject();
        int i = 1;
        for (JSONObject localWorldJson : localWorldJsons)
            localWorldsJson.put(getLocalWorldID(i), localWorldJson);
        saveFileJson.put("LocalWorlds", localWorldsJson);

        String jsonText = saveFileJson.toString(1);
        File saveFile = new File(fileName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(saveFile);
            fileWriter.write(jsonText);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
