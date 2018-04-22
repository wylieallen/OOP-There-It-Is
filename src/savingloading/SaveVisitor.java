package savingloading;

import commands.*;
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
import maps.entityimpaction.EntityImpactor;
import maps.entityimpaction.InfiniteAreaEffect;
import maps.entityimpaction.OneShotAreaEffect;
import maps.entityimpaction.Trap;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Obstacle;
import maps.movelegalitychecker.Terrain;
import maps.tile.LocalWorldTile;
import maps.tile.OverWorldTile;
import maps.tile.Tile;
import maps.trajectorymodifier.River;
import maps.trajectorymodifier.TrajectoryModifier;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
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
    public JSONObject saveFileJson = new JSONObject();
    private JSONObject playerJson = new JSONObject();
    private JSONObject overWorldJson = new JSONObject();
    private List<JSONObject> localWorldJsons = new ArrayList<>();

    private JSONObject currentEntityJson;
    private JSONObject currentCommandJson;
    private JSONObject currentSkillCommandJson;
    private JSONObject currentTileJson;
    private JSONObject currentAiJson;
    private Queue<JSONObject> itemJsonsQueue = new ArrayDeque<>();
    private Queue<JSONObject> interactionsQueue = new ArrayDeque<>();
    private Map<TransitionCommand, JSONObject> transitionCommandJsons = new HashMap<>();

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
        currentEntityJson.put("DirectionFacing", e.getMovementDirection().name());
        e.getStats().accept(this);
        currentEntityJson.put("ActorInteractions", new JSONArray());
        for (EntityInteraction ei : e.getActorInteractions()) {
            ei.accept(this);
            currentEntityJson.getJSONArray("ActorInteractions").put(interactionsQueue.remove());
        }
        currentEntityJson.put("ActeeInteractions", new JSONArray());
        for (EntityInteraction ei : e.getActorInteractions()) {
            ei.accept(this);
            currentEntityJson.getJSONArray("ActeeInteractions").put(interactionsQueue.remove());
        }
        e.getInventory().accept(this);
        currentEntityJson.put("Name", e.getName());
    }

    @Override
    public void visitVehicle(Vehicle e){
        e.getController().accept(this);
        currentEntityJson.remove("Type");
        currentEntityJson.put("Type", "Vehicle");
        currentEntityJson.put("DirectionFacing", e.getMovementDirection().name());
        e.getStats().accept(this);
        currentEntityJson.put("ActorInteractions", new JSONArray());
        for (EntityInteraction ei : e.getActorInteractions()) {
            ei.accept(this);
            currentEntityJson.getJSONArray("ActorInteractions").put(interactionsQueue.remove());
        }
        currentEntityJson.put("ActeeInteractions", new JSONArray());
        for (EntityInteraction ei : e.getActorInteractions()) {
            ei.accept(this);
            currentEntityJson.getJSONArray("ActeeInteractions").put(interactionsQueue.remove());
        }
        e.getInventory().accept(this);
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
        addNonPlayerEntity();
        currentEntityJson.put("Type", "NPC");
        n.getAggroAi().accept(this);
        currentEntityJson.put("AggroAi", currentAiJson);
        n.getNonAggroAi().accept(this);
        currentEntityJson.put("NonAggroAi", currentAiJson);
        addInVehicle(n.isInVehicle());
        addCoordinates(n.getEntityLocation());
        n.getEquipment().accept(this);
        currentEntityJson.put("IsAggro", n.isAggro());
    }

    @Override
    public void visitFriendlyAI(FriendlyAI f) {
        currentAiJson = new JSONObject();
        currentAiJson.put("Type", "Friendly");
        JSONArray interactionsJson = new JSONArray();
        List<EntityInteraction> interactions = f.getInteractions();
        for (EntityInteraction ei : interactions){
            ei.accept(this);
        }
        while(!interactionsQueue.isEmpty()){
            interactionsJson.put(interactionsQueue.remove());
        }
        currentAiJson.put("Interactions", interactionsJson);
    }

    @Override
    public void visitHostileAI(HostileAI h) {
        currentAiJson = new JSONObject();
        currentAiJson.put("Type", "Hostile");
        JSONArray interactionsJson = new JSONArray();
        List<EntityInteraction> interactions = h.getInteractions();
        for (EntityInteraction ei : interactions){
            ei.accept(this);
        }
        while(!interactionsQueue.isEmpty()){
            interactionsJson.put(interactionsQueue.remove());
        }
        currentAiJson.put("Interactions", interactionsJson);
    }

    @Override
    public void visitPatrolAI(PatrolAI p) {
        currentAiJson = new JSONObject();
        currentAiJson.put("Type", "Patrol");
        JSONArray interactionsJson = new JSONArray();
        List<EntityInteraction> interactions = p.getInteractions();
        for (EntityInteraction ei : interactions){
            ei.accept(this);
        }
        while(!interactionsQueue.isEmpty()){
            interactionsJson.put(interactionsQueue.remove());
        }
        currentAiJson.put("Interactions", interactionsJson);
    }

    @Override
    public void visitPetAI(PetAI p) {
        currentAiJson = new JSONObject();
        currentAiJson.put("Type", "Pet");
        JSONArray interactionsJson = new JSONArray();
        List<EntityInteraction> interactions = p.getInteractions();
        for (EntityInteraction ei : interactions){
            ei.accept(this);
        }
        while(!interactionsQueue.isEmpty()){
            interactionsJson.put(interactionsQueue.remove());
        }
        currentAiJson.put("Interactions", interactionsJson);
    }

    @Override
    public void visitEntityStats(EntityStats entityStats) {
        JSONObject statsJson = new JSONObject();
        JSONObject skillsJson = new JSONObject();
        Map<SkillType, Integer> skills = entityStats.getSkills();
        for (Map.Entry<SkillType, Integer> entry : skills.entrySet()) {
            skillsJson.put(entry.getKey().name(), entry.getValue());
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

    private void addNonPlayerEntity(){
        JSONArray entitiesJson = getCurrentLocalWorldJson().getJSONArray("Entities");
        JSONObject entityJson = new JSONObject();
        entitiesJson.put(entityJson);
        currentEntityJson = entityJson;
    }

    private void addInVehicle(boolean inVehicle){
        currentEntityJson.put("InVehicle", inVehicle);
    }

    private void addCoordinates(Coordinate coordinate){
        currentEntityJson.put("X", coordinate.x());
        currentEntityJson.put("Y", coordinate.y());
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
        JSONObject wearableItemsJson = new JSONObject();
        equipmentJson.put("Wearables", wearableItemsJson);
        for(WeaponItem item : equipment.getWeapons()) {
            System.out.println(item);
            item.accept(this);
        }
        while(!itemJsonsQueue.isEmpty())
            weaponItemsJson.put(itemJsonsQueue.remove());
        for (Map.Entry<EquipSlot, WearableItem> entry : equipment.getWearables().entrySet()) {
            entry.getValue().accept(this);
            while(!itemJsonsQueue.isEmpty())
                wearableItemsJson.put(entry.getKey().name(), itemJsonsQueue.remove());
        }
    }

    @Override
    public void visitMountInteraction(MountInteraction m) {
        JSONObject interactionJson = new JSONObject();
        interactionJson.put("Name", "MountInteraction");
        interactionsQueue.add(interactionJson);
    }

    @Override
    public void visitBackStabInteraction(BackStabInteraction b) {
        JSONObject interactionJson = new JSONObject();
        interactionJson.put("Name", "BackStabInteraction");
        interactionsQueue.add(interactionJson);
    }

    @Override
    public void visitPickPocketInteraction(PickPocketInteraction p) {
        JSONObject interactionJson = new JSONObject();
        interactionJson.put("Name", "PickPocketInteraction");
        interactionsQueue.add(interactionJson);
    }

    @Override
    public void visitTalkInteraction(TalkInteraction t) {
        JSONObject interactionJson = new JSONObject();
        interactionJson.put("Name", "TalkInteraction");
        JSONArray messagesJson = new JSONArray();
        List<String> messages = t.getMessages();
        for (String message : messages){
            messagesJson.put(message);
        }
        interactionJson.put("Messages", messagesJson);
        interactionsQueue.add(interactionJson);
    }

    @Override
    public void visitTradeInteraction(TradeInteraction t) {
        JSONObject interactionJson = new JSONObject();
        interactionJson.put("Name", "TradeInteraction");
        interactionsQueue.add(interactionJson);
    }

    @Override
    public void visitUseItemInteraction(UseItemInteraction u) {
        JSONObject interactionJson = new JSONObject();
        interactionJson.put("Name", "UseItemInteraction");
        interactionsQueue.add(interactionJson);
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
        questItemJson.put("OnMap", q.isOnMap());
        questItemJson.put("QuestId", q.getQuestId());
        itemJsonsQueue.add(questItemJson);
    }

    @Override
    public void visitConsumableItem(ConsumableItem c) {
        JSONObject consumableItemJson = new JSONObject();
        consumableItemJson.put("Type", "Consumable");
        consumableItemJson.put("Name", c.getName());
        consumableItemJson.put("OnMap", c.isOnMap());
        c.getCommand().accept(this);
        consumableItemJson.put("Command", currentCommandJson);
        itemJsonsQueue.add(consumableItemJson);
    }

    @Override
    public void visitWeaponItem(WeaponItem w) {
        JSONObject weaponItemJson = new JSONObject();
        weaponItemJson.put("Type", "Weapon");
        weaponItemJson.put("Name", w.getName());
        weaponItemJson.put("OnMap", w.isOnMap());
        w.getCommand().accept(this);
        weaponItemJson.put("SkillCommand", currentSkillCommandJson);
        weaponItemJson.put("Damage", w.getDamage());
        weaponItemJson.put("AttackSpeed", w.getAttackSpeed());
        weaponItemJson.put("MaxRadius", w.getMaxRadius());
        weaponItemJson.put("ExpansionInterval", w.getExpansionInterval());
        weaponItemJson.put("UpdateInterval", w.getUpdateInterval());
        weaponItemJson.put("RequiredSkill", w.getRequiredSkill().name());
        weaponItemJson.put("InfluenceType", w.getInfluenceType().name());
        itemJsonsQueue.add(weaponItemJson);
    }

    @Override
    public void visitWearableItem(WearableItem w) {
        JSONObject wearableItemJson = new JSONObject();
        wearableItemJson.put("Type", "Wearable");
        wearableItemJson.put("Name", w.getName());
        wearableItemJson.put("OnMap", w.isOnMap());
        w.getCommand().accept(this);
        wearableItemJson.put("ReversableCommand", currentCommandJson);
        wearableItemJson.put("EquipType", w.getEquipType().name());
        itemJsonsQueue.add(wearableItemJson);
    }

    @Override
    public void visitConfuseCommand(ConfuseCommand confuseCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Confuse");
        currentCommandJson.put("Duration", confuseCommand.getDuration());
    }

    @Override
    public void visitEnrageCommand(EnrageCommand enrageCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Enrage");
    }

    @Override
    public void visitMakeFriendlyCommand(MakeFriendlyCommand makeFriendlyCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "MakeFriendly");
    }

    @Override
    public void visitModifyHealthCommand(ModifyHealthCommand modifyHealthCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "ModifyHealth");
        currentCommandJson.put("Amount", modifyHealthCommand.getModifyAmount());
    }

    @Override
    public void visitModifyStaminaRegenCommand(ModifyStaminaRegenCommand modifyStaminaRegenCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "ModifyStaminaRegen");
        currentCommandJson.put("Factor", modifyStaminaRegenCommand.getFactor());
        currentCommandJson.put("Duration", modifyStaminaRegenCommand.getDuration());
    }

    @Override
    public void visitObserveCommand(ObserveCommand observeCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Observe");
        currentCommandJson.put("Level", observeCommand.getLevel());
        currentCommandJson.put("Distance", observeCommand.getDistance());
    }

    @Override
    public void visitParalyzeCommand(ParalyzeCommand paralyzeCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "Paralyze");
        currentCommandJson.put("Duration", paralyzeCommand.getDuration());
    }

    @Override
    public void visitPickPocketCommand(PickPocketCommand pickPocketCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "PickPocket");
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
        currentCommandJson.put("EntityBaseMoveSpeed", makeParalyzedCommand.getEntityBaseMoveSpeed());
        addReversibleCommand(makeParalyzedCommand);
    }

    @Override
    public void visitTimedStaminaRegenCommand(TimedStaminaRegenCommand timedStaminaRegenCommand) {
        currentCommandJson = new JSONObject();
        currentCommandJson.put("Name", "TimedStaminaRegen");
        currentCommandJson.put("StaminaRegenDecrease", timedStaminaRegenCommand.getStaminaRegenDecrease());
        currentCommandJson.put("Factor", timedStaminaRegenCommand.getFactor());
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

    public void visitSkillCommand(SkillCommand skillCommand) {
        currentSkillCommandJson = new JSONObject();
        currentSkillCommandJson.put("SkillType", skillCommand.getSkillType().name());
        currentSkillCommandJson.put("Level", skillCommand.getLevel());
        currentSkillCommandJson.put("Effectiveness", skillCommand.getEffectiveness());
        skillCommand.getSuccessCommand().accept(this);
        currentSkillCommandJson.put("SuccessCommand", currentCommandJson);
        skillCommand.getFailureCommand().accept(this);
        currentSkillCommandJson.put("FailureCommand", currentCommandJson);
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
        if(overWorldTile.getEncounter() != null) {
            overWorldTile.getEncounter().accept(this);
            currentTileJson.put("Encounter", itemJsonsQueue.remove());
        }
    }

    @Override
    public void visitLocalWorldTile(LocalWorldTile localWorldTile) {
        addTile(localWorldTile);
        Set<TrajectoryModifier> tms = localWorldTile.getTrajectoryModifiers();
        for (TrajectoryModifier tm : tms){
            tm.accept(this);
        }
        Set<EntityImpactor> eis = localWorldTile.getEntityImpactors();
        for (EntityImpactor ei : eis){
            ei.accept(this);
        }
        addItemsToTile();
    }

    private void addTile(Tile tile){
        currentTileJson = new JSONObject();
        Collection<MoveLegalityChecker> mlcs = tile.getMoveLegalityCheckers();
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
        currentTileJson.put("Terrain", terrain.name());
    }

    @Override
    public void visitRiver(River river) {
        JSONObject riverJson = new JSONObject();
        riverJson.put("dx", river.getVector().dx());
        riverJson.put("dy", river.getVector().dy());
        currentTileJson.put("River", riverJson);
    }

    @Override
    public void visitInfiniteAreaEffect(InfiniteAreaEffect infiniteAreaEffect) {
        JSONObject areaEffectJson = new JSONObject();
        areaEffectJson.put("Type", "Infinite");
        areaEffectJson.put("Name", infiniteAreaEffect.name());
        areaEffectJson.put("TriggerInterval", infiniteAreaEffect.getTriggerInterval());
        areaEffectJson.put("LastTriggerTime", infiniteAreaEffect.getLastTriggerTime());
        infiniteAreaEffect.getCommand().accept(this);
        areaEffectJson.put("Command", currentCommandJson);
        currentTileJson.put("AreaEffect", areaEffectJson);
    }

    @Override
    public void visitOneShotAreaEffect(OneShotAreaEffect oneShotAreaEffect) {
        JSONObject areaEffectJson = new JSONObject();
        areaEffectJson.put("Type", "OneShot");
        areaEffectJson.put("Name", oneShotAreaEffect.name());
        oneShotAreaEffect.getCommand().accept(this);
        areaEffectJson.put("Command", currentCommandJson);
        areaEffectJson.put("HasFired", oneShotAreaEffect.shouldBeRemoved());
        currentTileJson.put("AreaEffect", areaEffectJson);
    }

    @Override
    public void visitTrap(Trap trap) {
        JSONObject trapJson = new JSONObject();
        trap.getCommand().accept(this);
        trapJson.put("Command", currentCommandJson);
        trapJson.put("HasFired", trap.hasFired());
        trapJson.put("Strength", trap.getStrength());
        trapJson.put("IsVisible", trap.isVisible());
        currentTileJson.put("Trap", trapJson);
    }

    @Override
    public void visitGame(Game g) {
        saveFileJson = new JSONObject();

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
