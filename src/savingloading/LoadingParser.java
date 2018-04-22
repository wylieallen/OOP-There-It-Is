package savingloading;

import commands.*;
import commands.reversiblecommands.*;
import commands.skillcommands.SkillCommand;
import entity.entitycontrol.AI.*;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitycontrol.controllerActions.ControllerActionFactory;
import entity.entitymodel.*;
import entity.entitymodel.interactions.*;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import gameview.GameDisplayState;
import gameview.GamePanel;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.displayable.Displayable;
import items.InteractiveItem;
import items.Item;
import items.OneshotItem;
import items.takeableitems.*;
import maps.Influence.InfluenceArea;
import maps.Influence.InfluenceType;
import maps.entityimpaction.*;
import maps.movelegalitychecker.MoveLegalityChecker;
import maps.movelegalitychecker.Obstacle;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.OverWorldTile;
import maps.trajectorymodifier.River;
import maps.trajectorymodifier.TrajectoryModifier;
import maps.world.*;
import org.json.JSONArray;
import org.json.JSONObject;
import skills.SkillType;
import spawning.SpawnObservable;
import utilities.Coordinate;
import utilities.Vector;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
import java.util.Queue;

import static maps.movelegalitychecker.Terrain.*;
import static skills.SkillType.*;

/**
 * Created by dontf on 4/13/2018.
 */
public class LoadingParser {

    private Game game;
    private Entity player;
    private GameDisplayState gameDisplay;

    private OverWorld overWorld;
    private List<LocalWorld> localWorlds = new ArrayList<>();

    private Map<GameObject, Displayable> spriteMap = new HashMap<GameObject, Displayable>();
    private Map<World, WorldDisplayable> worldDisplayableMap = new HashMap<World, WorldDisplayable>();

    private JSONObject gameJson;

    private Map<String,World> worldIdMappings = new HashMap<>();
    private List<TransitionCommandHolder> transitionCommands = new ArrayList<>();
    private Queue<SpawnObservable> spawnObservables = new ArrayDeque<>();
    private Map<SpawnObservable, Displayable> spawnerMap = new HashMap<>();

    public void loadGame (String saveFileName, GamePanel gamePanel) throws FileNotFoundException {
        loadFileToJson(saveFileName);
        loadPlayer(gameJson.getJSONObject("Player"), gamePanel);
        loadOverWorld(gameJson.getJSONObject("OverWorld"));
        loadLocalWorlds(gameJson.getJSONObject("LocalWorlds"));

        List<FoggyWorld> foggyWorlds = loadFoggyWorlds();

        game = new Game(overWorld, overWorld, foggyWorlds, 0, player);

        // must do this after game is made
        setTransitionCommands();

        //gameDisplay = new GameDisplayState(gamePanel.getSize(), game, spriteMap, spawnerMap, worldDisplayableMap, overWorld);
    }

    private void loadFileToJson(String saveFileName) throws FileNotFoundException {
        String filePath = "resources/savefiles/" + saveFileName + ".json";
        Scanner scanner = new Scanner( new File(filePath) );
        String jsonText = scanner.useDelimiter("\\A").next();
        scanner.close();
        gameJson = new JSONObject(jsonText);
    }

    private void loadPlayer(JSONObject playerJson, GamePanel gamePanel){
        Vector movementVector = new Vector();
        JSONObject entityStatsJson = playerJson.getJSONObject("Stats");
        EntityStats entityStats = loadEntityStats(entityStatsJson);
        List<EntityInteraction> actorInteractions = loadActorInteractions(playerJson.getJSONArray("ActorInteractions"));
        List<TimedEffect> effects = new ArrayList<>();
        Inventory inventory = loadInventory(playerJson.getJSONArray("Inventory"));
        Boolean onMap = !playerJson.getBoolean("InVehicle");
        player = new Entity(movementVector, entityStats, effects, actorInteractions, inventory, onMap, "Default");
        Equipment equipment = loadEquipment(playerJson.getJSONObject("Equipment"), inventory, player);
        Coordinate coordinate = new Coordinate(playerJson.getInt("X"), playerJson.getInt("Y"));
        HumanEntityController controller = new HumanEntityController(player, equipment, coordinate, gamePanel);
        player.setController(controller);
        List<ControllerAction> controllerActions = loadControllerActions(playerJson.getString("Name"), player, controller, equipment);
        controller.setControllerActions(controllerActions);
        Displayable displayable = loadDisplayable(playerJson.getString("Name"));
        spriteMap.put(player, displayable);
    }

    private void loadOverWorld(JSONObject overWorldJson) {
        Map <Coordinate, OverWorldTile> tiles = loadOverWorldTiles(overWorldJson.getJSONArray("Tiles"));
        overWorld = new OverWorld(tiles);
        worldIdMappings.put("OverWorld", overWorld);
        worldDisplayableMap.put(overWorld, new WorldDisplayable(new Point(0, 0), 0, overWorld));
        while(!spawnObservables.isEmpty()){
            spawnObservables.remove().registerObserver(overWorld);
        }
    }

    private void loadLocalWorlds(JSONObject localWorldsJson){
        Iterator<String> localWorldIds = localWorldsJson.keys();
        while(localWorldIds.hasNext()) {
            String localWorldId = localWorldIds.next();
            JSONObject localWorldJson = localWorldsJson.getJSONObject(localWorldId);
            Map<Coordinate, LocalWorldTile> localWorldTiles = loadLocalWorldTiles(localWorldJson.getJSONArray("Tiles"));
            if (localWorldJson.has("Entities")) {
                JSONArray entitiesJson = localWorldJson.getJSONArray("Entities");
                for (Object enitityJsonObj : entitiesJson) {
                    JSONObject entityJson = (JSONObject) enitityJsonObj;
                    Entity entity = loadEntity(entityJson);
                    Coordinate coordinate = new Coordinate(entityJson.getInt("X"), entityJson.getInt("Y"));
                    LocalWorldTile tile = localWorldTiles.get(coordinate);
                    tile.setEntity(entity);
                }
            }
            LocalWorld localWorld = new LocalWorld(localWorldTiles, new HashSet<InfluenceArea>());
            localWorlds.add(localWorld);
            worldIdMappings.put(localWorldId, localWorld);
            worldDisplayableMap.put(localWorld, new WorldDisplayable(new Point(0, 0), 0, localWorld));
            while(!spawnObservables.isEmpty()){
                spawnObservables.remove().registerObserver(localWorld);
            }
        }
    }

    private List<FoggyWorld> loadFoggyWorlds() {
        List<FoggyWorld> foggyWorlds = new ArrayList<>();
        for (LocalWorld localWorld : localWorlds){
            foggyWorlds.add(new FoggyWorld(localWorld, player));
        }
        return foggyWorlds;
    }

    private Entity loadEntity(JSONObject entityJson) {
        Vector movementVector = new Vector();
        JSONObject entityStatsJson = entityJson.getJSONObject("Stats");
        EntityStats entityStats = loadEntityStats(entityStatsJson);
        List<EntityInteraction> actorInteractions = loadActorInteractions(entityJson.getJSONArray("ActorInteractions"));
        List<TimedEffect> effects = new ArrayList<>();
        Inventory inventory = loadInventory(entityJson.getJSONArray("Inventory"));
        Boolean onMap = !entityJson.getBoolean("InVehicle");
        Entity entity;
        if (entityJson.getString("Type").equals("Vehicle"))
            entity = new Vehicle(movementVector, entityStats, effects, actorInteractions, inventory, onMap, null);
        else
            entity = new Entity(movementVector, entityStats, effects, actorInteractions, inventory, onMap, "Default");
        Equipment equipment = loadEquipment(entityJson.getJSONObject("Equipment"), inventory, entity);
        Coordinate coordinate = new Coordinate(entityJson.getInt("X"), entityJson.getInt("Y"));
        NpcEntityController controller = loadNpcEntityController(entityJson, entity, equipment, coordinate);
        entity.setController(controller);
        if (!entityJson.has("Name"))
            System.out.println(entityJson.toString(1));
        List<ControllerAction> controllerActions = loadControllerActions(entityJson.getString("Name"), entity, controller, equipment);
        controller.setControllerActions(controllerActions);
        Displayable displayable = loadDisplayable(entityJson.getString("Name"));
        spriteMap.put(entity, displayable);
        return entity;
    }

    private List<ControllerAction> loadControllerActions(String name, Entity entity, EntityController controller, Equipment equipment) {
        if (name.equals("Smasher")){
            return ControllerActionFactory.makeSmasherActions(entity, controller, equipment);
        }
        else if (name.equals("Summoner")){
            return ControllerActionFactory.makeSummonerActions(entity, controller, equipment);
        }
        else if (name.equals("Sneak")){
            return ControllerActionFactory.makeSneakActions(entity, controller, equipment);
        }
        else{
            return ControllerActionFactory.makeBasicNPCActions(entity, controller, equipment);
        }
    }

    private Map<Coordinate, LocalWorldTile> loadLocalWorldTiles(JSONArray tilesJson) {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();
        for (Object tileJsonObj : tilesJson){
            JSONObject tileJson = (JSONObject) tileJsonObj;
            Coordinate coordinate = new Coordinate(tileJson.getInt("X"), tileJson.getInt("Y"));
            Set<MoveLegalityChecker> moveLegalityCheckers = new HashSet<>();
            Set<TrajectoryModifier> trajectoryModifiers = new HashSet<>();
            Set<EntityImpactor> entityImpactors = new HashSet<>();
            Terrain terrain = null;
            if (tileJson.has("Terrain")){
                terrain = loadTerrain(tileJson.getString("Terrain"));
            }
            if (tileJson.has("Obstacle")){
                moveLegalityCheckers.add(new Obstacle());
            }
            if (tileJson.has("River")){
                JSONObject riverJson = tileJson.getJSONObject("River");
                trajectoryModifiers.add(loadRiver(tileJson.getJSONObject("River")));
            }
            if (tileJson.has("AreaEffect")){
                entityImpactors.add(loadAreaEffect(tileJson.getJSONObject("AreaEffect")));
            }
            if (tileJson.has("Trap")){
                entityImpactors.add(loadTrap(tileJson.getJSONObject("Trap")));
            }
            if (tileJson.has("Items")){
                for (Object itemJsonObj : tileJson.getJSONArray("Items")){
                    JSONObject itemJson = (JSONObject) itemJsonObj;
                    Item item = loadItem(itemJson);
                    entityImpactors.add(item);
                }
            }
            LocalWorldTile tile = new LocalWorldTile(moveLegalityCheckers, terrain, null, trajectoryModifiers, entityImpactors);
            tiles.put(coordinate, tile);
        }
        return tiles;
    }

    private Trap loadTrap(JSONObject trapJson) {
        Command command = loadCommand(trapJson.getJSONObject("Command"));
        Boolean hasFired = trapJson.getBoolean("HasFired");
        int strength = trapJson.getInt("Strength");
        Boolean isVisible = trapJson.getBoolean("IsVisible");
        Trap trap = new Trap(command, hasFired, strength, isVisible);
        Displayable displayable = loadDisplayable("Trap");
        spriteMap.put(trap, displayable);
        return trap;
    }

    private River loadRiver(JSONObject riverJson) {
        double dx = riverJson.getDouble("dx");
        double dz = riverJson.getDouble("dz");
        Vector v = new Vector(dx, dz);
        River river = new River(v);
        Displayable displayable = ImageMaker.makeRiverDisplayable(v.getDirection());
        spriteMap.put(river, displayable);
        return river;
    }

    private AreaEffect loadAreaEffect(JSONObject areaEffectJson) {
        if (areaEffectJson.getString("Type").equals("Infinite")) {
            AreaEffect areaEffect = loadInfiniteAreaEffect(areaEffectJson);
            return areaEffect;
        }
        else if (areaEffectJson.getString("Type").equals("OneShot"))
            return loadOneShotAreaEffect(areaEffectJson);
        else{
            System.out.println("ERROR: AreaEffect not loaded properly -- Type string given: " + areaEffectJson.getString("Type"));
            return null;
        }
    }

    private OneShotAreaEffect loadOneShotAreaEffect(JSONObject areaEffectJson) {
        Command command = loadCommand(areaEffectJson.getJSONObject("Command"));
        Boolean hasFired = areaEffectJson.getBoolean("HasFired");
        OneShotAreaEffect areaEffect = new OneShotAreaEffect(command, hasFired, areaEffectJson.getString("Name"));
        Displayable displayable = loadDisplayable(areaEffectJson.getString("Name"));
        spriteMap.put(areaEffect, displayable);
        return areaEffect;
    }

    private InfiniteAreaEffect loadInfiniteAreaEffect(JSONObject areaEffectJson) {
        Command command = loadCommand(areaEffectJson.getJSONObject("Command"));
        long triggerInterval = areaEffectJson.getLong("TriggerInterval");
        long lastTriggerTime = areaEffectJson.getLong("LastTriggerTime");
        InfiniteAreaEffect areaEffect = new InfiniteAreaEffect(command, triggerInterval, lastTriggerTime, areaEffectJson.getString("Name"));
        Displayable displayable = loadDisplayable(areaEffectJson.getString("Name"));
        spriteMap.put(areaEffect, displayable);
        return areaEffect;
    }

    private Map<Coordinate, OverWorldTile> loadOverWorldTiles(JSONArray tilesJson) {
        Map <Coordinate, OverWorldTile> tiles = new HashMap<>();
        for (Object tileJsonObj : tilesJson){
            JSONObject tileJson = (JSONObject) tileJsonObj;
            Coordinate coordinate = new Coordinate(tileJson.getInt("X"), tileJson.getInt("Y"));
            Set<MoveLegalityChecker> moveLegalityCheckers = new HashSet<>();
            EntityImpactor entityImpactor = null;
            Terrain terrain = null;
            if (tileJson.has("Terrain")){
                terrain = loadTerrain(tileJson.getString("Terrain"));
            }
            if (tileJson.has("Obstacle")){
                moveLegalityCheckers.add(new Obstacle());
            }
            OverWorldTile tile;
            if (tileJson.has("Encounter")){
                JSONObject encounterJson = tileJson.getJSONObject("Encounter");
                entityImpactor = new InteractiveItem(encounterJson.getString("Name"), loadCommand(encounterJson.getJSONObject("Command")));
                tile = new OverWorldTile(moveLegalityCheckers, terrain, null, entityImpactor);
            }
            else{
                tile = new OverWorldTile(moveLegalityCheckers, terrain, null);
            }
            tiles.put(coordinate, tile);
        }
        return tiles;
    }

    private Terrain loadTerrain(String terrain) {
        if (terrain.equals("GRASS")) {
            Displayable displayable = loadDisplayable("Grass");
            spriteMap.put(GRASS, displayable);
            return GRASS;
        }
        else if (terrain.equals("WATER")){
            Displayable displayable = loadDisplayable("Water");
            spriteMap.put(WATER, displayable);
            return WATER;
        }
        else if (terrain.equals("MOUNTAIN")){
            Displayable displayable = loadDisplayable("Mountain");
            spriteMap.put(MOUNTAIN, displayable);
            return MOUNTAIN;
        }
        else{
            System.out.println("ERROR: Terrain not loaded properly -- String given: " + terrain);
            return null;
        }
    }

    private Direction loadDirectionFacing(String directionFacting){
        if (directionFacting.equals("N"))
            return Direction.N;
        else if (directionFacting.equals("NE"))
            return Direction.NE;
        else if (directionFacting.equals("SE"))
            return Direction.SE;
        else if (directionFacting.equals("S"))
            return Direction.S;
        else if (directionFacting.equals("SW"))
            return Direction.SW;
        else if (directionFacting.equals("NW"))
            return Direction.NW;
        else
            System.out.println("ERROR: Direction not loaded properly -- String given: " + directionFacting);
        return Direction.NULL;
    }

    private EntityStats loadEntityStats(JSONObject entityStatsJson){
        Map<SkillType, Integer> skills = new HashMap<>();
        Iterator<String> skillStrings = entityStatsJson.getJSONObject("Skills").keys();
        while(skillStrings.hasNext()) {
            String skillString = skillStrings.next();
            int level = (int) entityStatsJson.getJSONObject("Skills").get(skillString);
            skills.put(loadSkillType(skillString), level);
        }
        return new EntityStats(skills, entityStatsJson.getInt("BaseMoveSpeed"),
                entityStatsJson.getInt("MaxHealth"), entityStatsJson.getInt("CurrentHealth"),
                entityStatsJson.getInt("MaxMana"), entityStatsJson.getInt("CurrentMana"),
                entityStatsJson.getInt("ManaRegenRate"), entityStatsJson.getInt("CurrentXP"),
                entityStatsJson.getInt("UnspentSkillPoints"), entityStatsJson.getInt("VisibilityRadius"),
                entityStatsJson.getInt("Concealment"), entityStatsJson.getDouble("Gold"),
                entityStatsJson.getBoolean("IsSearching"), entityStatsJson.getBoolean("IsConfused"));
    }

    private SkillType loadSkillType(String skillString){
        if (skillString.equals("BINDWOUNDS"))
            return BINDWOUNDS;
        if (skillString.equals("BARGAIN"))
            return BARGAIN;
        if (skillString.equals("OBSERVATION"))
            return OBSERVATION;
        if (skillString.equals("ONEHANDEDWEAPON"))
            return ONEHANDEDWEAPON;
        if (skillString.equals("TWOHANDEDWEAPON"))
            return TWOHANDEDWEAPON;
        if (skillString.equals("BRAWLING"))
            return BRAWLING;
        if (skillString.equals("ENCHANTMENT"))
            return ENCHANTMENT;
        if (skillString.equals("BOON"))
            return BOON;
        if (skillString.equals("BANE"))
            return BANE;
        if (skillString.equals("STAFF"))
            return STAFF;
        if (skillString.equals("PICKPOCKET"))
            return PICKPOCKET;
        if (skillString.equals("DETECTANDREMOVETRAP"))
            return DETECTANDREMOVETRAP;
        if (skillString.equals("CREEP"))
            return CREEP;
        if (skillString.equals("RANGEDWEAPON"))
            return RANGEDWEAPON;
        else
            System.out.println("ERROR: Skill type not loaded properly -- String given: " + skillString);
        return NULL;
    }

    private List<EntityInteraction> loadActorInteractions(JSONArray actorInteractionsJson){
        List<EntityInteraction> actorInteractions = new ArrayList<>();
        for (Object interactionJson : actorInteractionsJson){
            if (((JSONObject) interactionJson).get("Name").equals("MountInteraction"))
                actorInteractions.add(new MountInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("BackStabInteraction"))
                actorInteractions.add(new BackStabInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("PickPocketInteraction"))
                actorInteractions.add(new PickPocketInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("TalkInteraction")) {
                JSONArray messagesJson = ((JSONObject) interactionJson).getJSONArray("Messages");
                List<String> messages = new ArrayList<>();
                for (Object message : messagesJson){
                    messages.add((String) message);
                }
                actorInteractions.add(new TalkInteraction(messages));
            }
            else if (((JSONObject) interactionJson).get("Name").equals("TradeInteraction"))
                actorInteractions.add(new TradeInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("UseItemInteraction"))
                actorInteractions.add(new UseItemInteraction());
            else {
                System.out.println("ERROR: ActorInteraction not loaded properly -- String given: " + ((JSONObject) interactionJson).get("Name"));
                return null;
            }
        }
        return actorInteractions;
    }

    private Inventory loadInventory(JSONArray inventoryJson){
        List<TakeableItem> takeableItems = new ArrayList<>();
        for (Object itemJson : inventoryJson){
            TakeableItem item = loadTakeableItem((JSONObject)itemJson);
            takeableItems.add(item);
        }
        return new Inventory(takeableItems);
    }

    private Equipment loadEquipment(JSONObject equipmentJson, Inventory inventory, Entity entity) {
        JSONArray weaponItemsJson = equipmentJson.getJSONArray("Weapons");
        JSONObject wearableItemsJson = equipmentJson.getJSONObject("Wearables");
        List<WeaponItem> weaponItems = new ArrayList<WeaponItem>();
        Map<EquipSlot, WearableItem> wearableItems = new HashMap<EquipSlot, WearableItem>();
        for (Object weaponItemJsonObj : weaponItemsJson){
            JSONObject itemJson = (JSONObject) weaponItemJsonObj;
            WeaponItem item = loadWeaponItem(itemJson);
            weaponItems.add(item);
            spawnObservables.add(item);
        }
        Iterator<String> equipSlotStrings = wearableItemsJson.keys();
        while(equipSlotStrings.hasNext()) {
            String equipSlotString = equipSlotStrings.next();
            EquipSlot equipSlot = loadEquipType(equipSlotString);
            JSONObject itemJson = wearableItemsJson.getJSONObject(equipSlotString);
            WearableItem item = loadWearableItem(itemJson);
            wearableItems.put(equipSlot, item);
        }
        int maxSize = 10; // ?
        WeaponItem[] weaponItemsArray = weaponItems.toArray(new WeaponItem[0]);
        return new Equipment(wearableItems, weaponItemsArray, maxSize, inventory, entity);
    }

    private Item loadItem(JSONObject itemJson) {
        Item item;
        if (itemJson.getString("Type").equals("OneShot"))
            item = loadOneShotItem(itemJson);
        else if (itemJson.getString("Type").equals("Interactive"))
            item = loadInteractiveItem(itemJson);
        else if (itemJson.getString("Type").equals("Quest"))
            item = loadQuestItem(itemJson);
        else if (itemJson.getString("Type").equals("Consumable"))
            item = loadConsumableItem(itemJson);
        else if (itemJson.getString("Type").equals("Weapon"))
            item = loadWeaponItem(itemJson);
        else if (itemJson.getString("Type").equals("Wearable"))
            item = loadWearableItem(itemJson);
        else if (itemJson.getString("Type").equals("Wearable"))
            item = loadWearableItem(itemJson);
        else{
            System.out.println("ERROR: Item not loaded properly -- Type string given: " + itemJson.getString("Type"));
            return null;
        }
        Displayable displayable = loadDisplayable(itemJson.getString("Name"));
        spriteMap.put(item, displayable);
        return item;
    }

    private OneshotItem loadOneShotItem(JSONObject itemJson) {
        String name = itemJson.getString("Name");
        Command command = loadCommand(itemJson.getJSONObject("Command"));
        Boolean isActive = itemJson.getBoolean("IsActive");
        return new OneshotItem(name, command, !isActive);
    }

    private InteractiveItem loadInteractiveItem(JSONObject itemJson) {
        String name = itemJson.getString("Name");
        Command command = loadCommand(itemJson.getJSONObject("Command"));
        return new InteractiveItem(name, command);
    }

    private TakeableItem loadTakeableItem(JSONObject itemJson){
        TakeableItem item;
        if (itemJson.get("Type").equals("Quest")){
            item = loadQuestItem(itemJson);
        }
        else if (itemJson.get("Type").equals("Consumable")){
            item = loadConsumableItem(itemJson);
        }
        else if (itemJson.get("Type").equals("Weapon")){
            item = loadWeaponItem(itemJson);
        }
        else if (itemJson.get("Type").equals("Wearable")){
            item = loadWearableItem(itemJson);
        }
        else{
            item = null;
            System.out.println("ERROR: Item type not loaded properly -- String given: " + itemJson.get("Type"));
        }
        return item;
    }

    private QuestItem loadQuestItem(JSONObject itemJson) {
        return new QuestItem(itemJson.getString("Name"),itemJson.getBoolean("OnMap"),itemJson.getInt("QuestId"));
    }

    private ConsumableItem loadConsumableItem(JSONObject itemJson) {
        return new ConsumableItem(itemJson.getString("Name"), itemJson.getBoolean("OnMap"), loadCommand(itemJson.getJSONObject("Command")));
    }

    private WeaponItem loadWeaponItem(JSONObject itemJson) {
        WeaponItem item = new WeaponItem(itemJson.getString("Name"),
                itemJson.getBoolean("OnMap"),
                itemJson.getInt("AttackSpeed"),
                loadSkillType(itemJson.getString("RequiredSkill")),
                itemJson.getInt("StaminaCost"),
                itemJson.getInt("MaxRadius"),
                itemJson.getInt("ExpansionInterval"),
                itemJson.getLong("UpdateInterval"),
                itemJson.getLong("duration"),
                loadInfluenceType(itemJson.getString("InfluenceType")),
                loadSkillCommand(itemJson.getJSONObject("SkillCommand")),
                itemJson.getBoolean("makesExpandingArea"));
        Displayable displayable = loadDisplayable(itemJson.getString("Name"));
        spriteMap.put(item, displayable);
        Displayable spawningThingDisplayable = loadDisplayable(itemJson.getString("Name")  + " - Spawn");
        spawnerMap.put(item, spawningThingDisplayable);
        return item;

    }

    private InfluenceType loadInfluenceType(String type) {
        switch(type) {
            case "LINEARINFLUENCE":
                return InfluenceType.LINEARINFLUENCE;
            case "ANGULARINFLUENCE":
                return InfluenceType.ANGULARINFLUENCE;
            case "CIRCULARINFLUENCE":
                return InfluenceType.CIRCULARINFLUENCE;
        }
        return InfluenceType.LINEARINFLUENCE;
    }

    private WearableItem loadWearableItem(JSONObject itemJson) {
        if (itemJson.getString("Name").equals("NONE"))
            return WearableItem.NONE;
        else
            return new WearableItem(itemJson.getString("Name"), itemJson.getBoolean("OnMap"),
                loadReversibleCommand(itemJson.getJSONObject("ReversableCommand")), loadEquipType(itemJson.getString("EquipType")));
    }

    private Command loadCommand(JSONObject commandJson) {
        if (commandJson.getString("Name").equals("Null"))
            return null;
        else if (commandJson.getString("Name").equals("Transition"))
            return loadTransitionCommand(commandJson);
        else if (commandJson.getString("Name").equals("Confuse"))
            return loadConfuseCommand(commandJson);
        else if (commandJson.getString("Name").equals("Enrage"))
            return loadEnrageCommand(commandJson);
        else if (commandJson.getString("Name").equals("MakeFriendly"))
            return loadMakeFriendlyCommand(commandJson);
        else if (commandJson.getString("Name").equals("ModifyHealth"))
            return loadModifyHealthCommand(commandJson);
        else if (commandJson.getString("Name").equals("BuffHealth"))
            return loadBuffHealthCommand(commandJson);
        else if (commandJson.getString("Name").equals("Kill"))
            return loadKillCommand(commandJson);
        else if (commandJson.getString("Name").equals("LevelUp"))
            return loadLevelUpCommand(commandJson);
        else if (commandJson.getString("Name").equals("ModifyStaminaRegen"))
            return loadModifyStaminaRegenCommand(commandJson);
        else if (commandJson.getString("Name").equals("Observe"))
            return loadObserveCommand(commandJson);
        else if (commandJson.getString("Name").equals("Paralyze"))
            return loadParalyzeCommand(commandJson);
        else if (commandJson.getString("Name").equals("PickPocket"))
            return loadPickPocketCommand(commandJson);
        else{
            System.out.println("ERROR: Command not loaded properly -- Command name given: " + commandJson.getString("Name"));
            return null;
        }
    }

    private SkillCommand loadSkillCommand(JSONObject commandJson) {
        Command failureCommand = null;
        if(commandJson.has("FailureCommand")){
            failureCommand = loadCommand(commandJson.getJSONObject("FailureCommand"));
        }
        return new SkillCommand(loadSkillType(commandJson.getString("SkillType")), commandJson.getInt("Level"),
                commandJson.getInt("Effectiveness"), loadCommand(commandJson.getJSONObject("SuccessCommand")),
                failureCommand);
    }

    private PickPocketCommand loadPickPocketCommand(JSONObject commandJson) {
        return new PickPocketCommand();
    }

    private ParalyzeCommand loadParalyzeCommand(JSONObject commandJson) {
        return new ParalyzeCommand(commandJson.getInt("Duration"));
    }

    private ObserveCommand loadObserveCommand(JSONObject commandJson) {
        return new ObserveCommand(commandJson.getInt("Level"), commandJson.getInt("Distance"));
    }

    private ModifyStaminaRegenCommand loadModifyStaminaRegenCommand(JSONObject commandJson) {
        return new ModifyStaminaRegenCommand(commandJson.getDouble("Factor"), commandJson.getInt("Duration"));
    }

    private ModifyHealthCommand loadModifyHealthCommand(JSONObject commandJson) {
        return new ModifyHealthCommand(commandJson.getInt("Amount"));
    }

    private BuffHealthCommand loadBuffHealthCommand(JSONObject commandJson)
    {
        return new BuffHealthCommand(commandJson.getInt("Amount"));
    }

    private KillCommand loadKillCommand(JSONObject commandJson) {
        return new KillCommand();
    }

    private LevelUpCommand loadLevelUpCommand(JSONObject commandJson) {
        return new LevelUpCommand();
    }

    private EnrageCommand loadEnrageCommand(JSONObject commandJson) {
        return new EnrageCommand();
    }

    private MakeFriendlyCommand loadMakeFriendlyCommand(JSONObject commandJson) {
        return new MakeFriendlyCommand();
    }

    private ConfuseCommand loadConfuseCommand(JSONObject commandJson) {
        return new ConfuseCommand(commandJson.getInt("Duration"));
    }

    private TransitionCommand loadTransitionCommand(JSONObject commandJson) {
        TransitionCommand transitionCommand = new TransitionCommand();
        String worldId = commandJson.getString("TargetWorld");
        Coordinate coordinate = new Coordinate(commandJson.getInt("TargetX"), commandJson.getInt("TargetY"));
        TransitionCommandHolder holder = new TransitionCommandHolder(transitionCommand, worldId, coordinate);
        transitionCommands.add(holder);
        return transitionCommand;
    }

    private ReversibleCommand loadReversibleCommand(JSONObject reversableCommandJson) {
        if (reversableCommandJson.getString("Name").equals("MakeConfused"))
            return loadMakeConfusedCommand(reversableCommandJson);
        else if (reversableCommandJson.getString("Name").equals("MakeParalyzed"))
            return loadMakeParalyzed(reversableCommandJson);
        else if (reversableCommandJson.getString("Name").equals("TimedStaminaRegen"))
            return loadTimedStaminaRegenCommand(reversableCommandJson);
        else{
            System.out.println("ERROR: ReversableCommand not loaded properly -- Command name given: " + reversableCommandJson.getString("Name"));
            return null;
        }
    }

    private ReversibleCommand loadTimedStaminaRegenCommand(JSONObject reversableCommandJson) {
        return new TimedStaminaRegenCommand(reversableCommandJson.getBoolean("IsApplied"),
                reversableCommandJson.getInt("StaminaRegenDecrease"), reversableCommandJson.getInt("Factor"));
    }

    private ReversibleCommand loadMakeParalyzed(JSONObject reversableCommandJson) {
        return new MakeParalyzedCommand(reversableCommandJson.getBoolean("IsApplied"));
    }

    private ReversibleCommand loadMakeConfusedCommand(JSONObject reversableCommandJson) {
        return new MakeConfusedCommand(reversableCommandJson.getBoolean("IsApplied"));
    }

    private EquipSlot loadEquipType(String equipType) {
        if (equipType.equals("ARMOUR"))
            return EquipSlot.ARMOUR;
        else if (equipType.equals("RING"))
            return EquipSlot.RING;
        else{
            System.out.println("ERROR: EquipType not loaded properly -- String given: " + equipType);
            return null;
        }
    }

    private NpcEntityController loadNpcEntityController(JSONObject entityJson, Entity entity,
                                                        Equipment equipment, Coordinate entityLocation){
        String entityTypeString = entityJson.getString("Type");
        AI aggroAi = null;
        AI nonAggroAi = null;
        if (entityJson.has("AggroAi")) {
            JSONObject aggroAiJson = entityJson.getJSONObject("AggroAi");
            aggroAi = loadAI(aggroAiJson);
        }
        if (entityJson.has("NonAggroAi")){
            JSONObject nonAggroAiJson = entityJson.getJSONObject("NonAggroAi");
            nonAggroAi = loadAI(nonAggroAiJson);
        }
        Boolean isAggro = entityJson.getBoolean("IsAggro");
        return new NpcEntityController(entity, equipment, entityLocation, aggroAi, nonAggroAi, isAggro);
    }

    private AI loadAI(JSONObject aiJson) {
        if(aiJson.get("Type").equals("Friendly")){
            return new FriendlyAI(loadActorInteractions(aiJson.getJSONArray("Interactions")), new HashMap<Coordinate, Direction>(), false);
        }
        else if(aiJson.get("Type").equals("Hostile")){
            return new HostileAI(loadActorInteractions(aiJson.getJSONArray("Interactions")), player, new HashMap<Coordinate, Direction>());
        }
        else if(aiJson.get("Type").equals("Patrol")){
            return new PatrolAI(loadActorInteractions(aiJson.getJSONArray("Interactions")), new ArrayList<Coordinate>(), new HashMap<Coordinate, Direction>());
        }
        else if(aiJson.get("Type").equals("Pet")){
            return new PetAI(loadActorInteractions(aiJson.getJSONArray("Interactions")), player, new HashMap<Coordinate, Direction>(), false);
        }
        else{
            System.out.println("ERROR: AI not loaded properly -- Command name given: " + aiJson.get("Type"));
            return null;
        }
    }

    private void setTransitionCommands() {
        for (TransitionCommandHolder holder : transitionCommands){
            String mapId = holder.getMapId();
            World world = worldIdMappings.get(mapId);
            Coordinate coordinate = holder.getCoordinate();
            TransitionCommand transitionCommand = holder.getTransitionCommand();
            transitionCommand.setTargetWorld(world);
            transitionCommand.setStartingCoordinate(coordinate);
            transitionCommand.setTransitionObserver(game);
            // since transitionCommands are in items,
        }
    }

    private Displayable loadDisplayable(String name) {
        switch(name) {
            // entities
            case "Smasher":
                return ImageMaker.makeSmasherEntityDisplayable();
            case "Summoner":
                return ImageMaker.makeSummonerEntityDisplayable();
            case "Sneak":
                return ImageMaker.makeSneakEntityDisplayable();
            case "ShopKeep":
                return ImageMaker.makeShopKeepDisplayable();
            case "Vehicle":
                return ImageMaker.makeVehicleDisplayable();
            case "Tim":
                return ImageMaker.makeEntityDisplayable2(null);
                // terrains
            case "Grass":
                return ImageMaker.makeGrassDisplayable();
            case "Water":
                return ImageMaker.makeWaterDisplayable();
            case "Mountain":
                return ImageMaker.makeMountainDisplayable();
                // items
            case "Encounter1":
                return ImageMaker.makeEncounterDisplayable1();
            case "Encounter2":
                return ImageMaker.makeEncounterDisplayable2();
            case "Teleporter":
                return ImageMaker.makeTeleporterDisplayable();
            case "Consumable1":
                return ImageMaker.makeConsumableDisplayable1();
            case "Consumable2":
                return ImageMaker.makeConsumableDisplayable2();
            case "Consumable3":
                return ImageMaker.makeConsumableDisplayable3();
            case "HealthPotion":
                return ImageMaker.makeConsumableDisplayable1();
            case "Brawling":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Gadget1":
                return ImageMaker.makeGadgetDisplayable1();
            case "Gadget2":
                return ImageMaker.makeGadgetDisplayable2();
            case "Gadget3":
                return ImageMaker.makeGadgetDisplayable3();
            case "Gadget4":
                return ImageMaker.makeGadgetDisplayable4();
            case "Gadget5":
                return ImageMaker.makeGadgetDisplayable5();
            case "LaserSword":
                return ImageMaker.makeLaserSwordDisplayable();
            case "RangedWeapon":
                return ImageMaker.makeRangedWeaponDisplayable();
            case "TwoHandedWeapon":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "RangedWeapon-Spawn": // format for spawning Displayables: "GameObject's name" + "-Spawn"
                return ImageMaker.makeRedProjectileDisplayable();
            case "Good Staff":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Good Staff - Spawn":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Staff":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Staff - Spawn":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Bad Staff":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Bad Staff - Spawn":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Circular Damage Gadget":
                return ImageMaker.makeGadgetDisplayable2();
            case "Circular Damage Gadget - Spawn":
                return ImageMaker.makeGadgetDisplayable2();
            case "Angular Damage Gadget":
                return ImageMaker.makeGadgetDisplayable5();
            case "Angular Damage Gadget - Spawn":
                return ImageMaker.makeGadgetDisplayable5();
            case "Linear Damage Gadget":
                return ImageMaker.makeGadgetDisplayable4();
            case "Linear Damage Gadget - Spawn":
                return ImageMaker.makeGadgetDisplayable4();
            case "Strong Heal Gadget":
                return ImageMaker.makeGadgetDisplayable1();
            case "Strong Heal Gadget - Spawn":
                return ImageMaker.makeGadgetDisplayable1();
            case "Faster Stamina Regen Gadget":
                return ImageMaker.makeGadgetDisplayable3();
            case "Faster Stamina Regen Gadget - Spawn":
                return ImageMaker.makeGadgetDisplayable3();
            case "Heal Gadget":
                return ImageMaker.makeGadgetDisplayable1();
            case "Heal Gadget - Spawn":
                return ImageMaker.makeGadgetDisplayable1();
            case "Pacify Gadget":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Pacify Gadget - Spawn":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Paralyze Gadget":
                return ImageMaker.makeRangedWeaponDisplayable();
            case "Paralyze Gadget - Spawn":
                return ImageMaker.makeRangedWeaponDisplayable();
            case "Confuse Gadget":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Confuse Gadget - Spawn":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Good Gun":
                return ImageMaker.makeRangedWeaponDisplayable();
            case "Good Gun - Spawn":
                return ImageMaker.makeBlueProjectileDisplayable();
            case "Gun":
                return ImageMaker.makeRangedWeaponDisplayable();
            case "Gun - Spawn":
                return ImageMaker.makeYellowProjectileDisplayable();
            case "Bad Gun":
                return ImageMaker.makeRangedWeaponDisplayable();
            case "Bad Gun - Spawn":
                return ImageMaker.makeRedProjectileDisplayable();
            case "Good Glove":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Good Glove - Spawn":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Glove":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Glove - Spawn":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Bad Glove":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Bad Glove - Spawn":
                return ImageMaker.makeBrawlingWeaponDisplayable();
            case "Good Sword":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Good Sword - Spawn":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Sword":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Sword - Spawn":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Bad Sword":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Bad Sword - Spawn":
                return ImageMaker.makeLaserSwordDisplayable();
            case "Good Axe":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Good Axe - Spawn":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Axe":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Axe - Spawn":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Bad Axe":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Bad Axe - Spawn":
                return ImageMaker.makeTwoHandedWeaponDisplayable();
            case "Bob":
                return ImageMaker.makeGadgetDisplayable1();
            case "Bob - Spawn":
                return ImageMaker.makeGadgetDisplayable1();
            // area effects
            case "Kill Area Effect":
                return ImageMaker.makeRedProjectileDisplayable();
            case "Heal Area Effect":
                return ImageMaker.makeBlueProjectileDisplayable();
            case "Damage Area Effect":
                return ImageMaker.makeYellowProjectileDisplayable();
            default:
                System.out.println("No Displayable for GameObject type -- " + name);
                return ImageMaker.getNullDisplayable();
        }
    }

    public GameDisplayState getGameDisplayState () {
        return gameDisplay;
    }

    public Game getGame () {
        return game;
    }

    public Entity getPlayer () {
        return player;
    }

    private class TransitionCommandHolder {

        private TransitionCommand transitionCommand;
        private String mapId;
        private Coordinate coordinate;

        public TransitionCommandHolder(TransitionCommand transitionCommand,
                                       String mapId,
                                       Coordinate coordinate){
            this.transitionCommand = transitionCommand;
            this.mapId = mapId;
            this.coordinate = coordinate;
        }

        public TransitionCommand getTransitionCommand() {
            return transitionCommand;
        }

        public String getMapId() {
            return mapId;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }
    }
}
