package savingloading;

import commands.Command;
import commands.TimedEffect;
import commands.TransitionCommand;
import commands.reversiblecommands.ReversibleCommand;
import commands.skillcommands.*;
import entity.entitycontrol.AI.AI;
import entity.entitycontrol.NpcEntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.*;
import entity.entitymodel.interactions.*;
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
import maps.tile.Tile;
import maps.trajectorymodifier.River;
import maps.trajectorymodifier.TrajectoryModifier;
import maps.world.Game;
import gameview.GameDisplayState;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import org.json.*;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static maps.movelegalitychecker.Terrain.GRASS;
import static maps.movelegalitychecker.Terrain.MOUNTAIN;
import static maps.movelegalitychecker.Terrain.WATER;
import static skills.SkillType.*;

/**
 * Created by dontf on 4/13/2018.
 */
public class LoadingParser {

    private Game game;
    private Entity player;
    private GameDisplayState gameDisplay;

    private OverWorld overWorld;
    private List<LocalWorld> localWorlds = new ArrayList<LocalWorld>();

    private JSONObject gameJson;

    private Map<String,World> idMappings = new HashMap<String,World>();
    private List<TransitionCommandHolder> transitionCommands = new ArrayList<TransitionCommandHolder>();

    public void loadGame (String saveFileName) throws FileNotFoundException {
        loadFileToJson(saveFileName);
        loadPlayer(gameJson.getJSONObject("Player"));
        loadOverWorld(gameJson.getJSONObject("OverWorld"));
        loadLocalWorlds(gameJson.getJSONObject("LocalWorlds"));

        game = new Game(overWorld, overWorld, localWorlds, 0, player);

        // must do this after game is made
        setTransitionCommands();
    }

    private void loadFileToJson(String saveFileName) throws FileNotFoundException {
        String filePath = "resources/savefiles/" + saveFileName + ".json";
        Scanner scanner = new Scanner( new File(filePath) );
        String jsonText = scanner.useDelimiter("\\A").next();
        scanner.close();
        gameJson = new JSONObject(jsonText);
    }

    private void loadPlayer(JSONObject playerJson){
        Vector movementVector = new Vector();
        JSONObject entityStatsJson = playerJson.getJSONObject("Stats");
        EntityStats entityStats = loadEntityStats(entityStatsJson);
        List<ControllerAction> controllerActions = loadPlayerControllerActions(); // TODO: create ControllerActions
        List<EntityInteraction> actorInteractions = loadActorInteractions(playerJson.getJSONArray("ActorInteractions"));
        List<TimedEffect> effects = new ArrayList<TimedEffect>();
        Inventory inventory = loadInventory(playerJson.getJSONArray("Inventory"));
        Boolean onMap = true;
        player = new Entity(movementVector, entityStats, effects, actorInteractions, inventory, onMap);
        player.setControllerActions(controllerActions);
        // TODO: set up player in gameDisplay
    }

    private void loadOverWorld(JSONObject overWorldJson) {
        Map <Coordinate, OverWorldTile> tiles = loadOverWorldTiles(overWorldJson.getJSONArray("Tiles"));
        overWorld = new OverWorld(tiles);
        idMappings.put("OverWorld", overWorld);
        // TODO: set up overworld in gameDisplay
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
            idMappings.put(localWorldId, localWorld);
        }
    }

    private Entity loadEntity(JSONObject entityJson) {
        Vector movementVector = new Vector();
        JSONObject entityStatsJson = entityJson.getJSONObject("Stats");
        EntityStats entityStats = loadEntityStats(entityStatsJson);
//        List<ControllerAction> controllerActions = // TODO: create ControllerActions
        List<EntityInteraction> actorInteractions = loadActorInteractions(entityJson.getJSONArray("ActorInteractions"));
        List<TimedEffect> effects = new ArrayList<TimedEffect>();
        Inventory inventory = loadInventory(entityJson.getJSONArray("Inventory"));
        Boolean onMap = true;
        Entity entity = new Entity(movementVector, entityStats, effects, actorInteractions, inventory, onMap);
//        entity.setControllerActions(controllerActions);
        // TODO: set up entity in gameDisplay
        return entity;
    }

    private Map<Coordinate, LocalWorldTile> loadLocalWorldTiles(JSONArray tilesJson) {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<Coordinate, LocalWorldTile>();
        for (Object tileJsonObj : tilesJson){
            JSONObject tileJson = (JSONObject) tileJsonObj;
            Coordinate coordinate = new Coordinate(tileJson.getInt("X"), tileJson.getInt("Y"));
            Set<MoveLegalityChecker> moveLegalityCheckers = new HashSet<MoveLegalityChecker>();
            Set<TrajectoryModifier> trajectoryModifiers = new HashSet<TrajectoryModifier>();
            Set<EntityImpactor> entityImpactors = new HashSet<EntityImpactor>();
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
        return new Trap(command, hasFired, strength, isVisible);
    }

    private River loadRiver(JSONObject riverJson) {
        double dx = riverJson.getDouble("dx");
        double dz = riverJson.getDouble("dz");
        Vector v = new Vector(dx, dz);
        return new River(v);
    }

    private AreaEffect loadAreaEffect(JSONObject areaEffectJson) {
        if (areaEffectJson.getString("Type").equals("Infinite"))
            return loadInfiniteAreaEffect(areaEffectJson);
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
        return new OneShotAreaEffect(command, hasFired);
    }

    private InfiniteAreaEffect loadInfiniteAreaEffect(JSONObject areaEffectJson) {
        Command command = loadCommand(areaEffectJson.getJSONObject("Command"));
        long triggerInterval = areaEffectJson.getLong("TriggerInterval");
        long lastTriggerTime = areaEffectJson.getLong("LastTriggerTime");
        return new InfiniteAreaEffect(command, triggerInterval, lastTriggerTime);
    }

    private Map<Coordinate, OverWorldTile> loadOverWorldTiles(JSONArray tilesJson) {
        Map <Coordinate, OverWorldTile> tiles = new HashMap<Coordinate, OverWorldTile>();
        for (Object tileJsonObj : tilesJson){
            JSONObject tileJson = (JSONObject) tileJsonObj;
            Coordinate coordinate = new Coordinate(tileJson.getInt("X"), tileJson.getInt("Y"));
            Set<MoveLegalityChecker> moveLegalityCheckers = new HashSet<MoveLegalityChecker>();
            Terrain terrain = null;
            if (tileJson.has("Terrain")){
                terrain = loadTerrain(tileJson.getString("Terrain"));
            }
            if (tileJson.has("Obstacle")){
                moveLegalityCheckers.add(new Obstacle());
            }
            tiles.put(coordinate, new OverWorldTile(moveLegalityCheckers, terrain, null));
        }
        return tiles;
    }

    private Terrain loadTerrain(String terrain) {
        if (terrain.equals("GRASS"))
            return GRASS;
        else if (terrain.equals("WATER"))
            return WATER;
        else if (terrain.equals("MOUNTAIN"))
            return MOUNTAIN;
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
        Map<SkillType, Integer> skills = new HashMap<SkillType, Integer>();
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
        List<EntityInteraction> actorInteractions = new ArrayList<EntityInteraction>();
        for (Object interactionJson : actorInteractionsJson){
            if (((JSONObject) interactionJson).get("Name").equals("MountInteraction"))
                actorInteractions.add(new MountInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("BackStabInteraction"))
                actorInteractions.add(new BackStabInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("PickPocketInteraction"))
                actorInteractions.add(new PickPocketInteraction());
            else if (((JSONObject) interactionJson).get("Name").equals("TalkInteraction")) {
                JSONArray messagesJson = ((JSONObject) interactionJson).getJSONArray("Messages");
                Set<String> messages = new HashSet<String>();
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
        List<TakeableItem> takeableItems = new ArrayList<TakeableItem>();
        for (Object itemJson : inventoryJson){
            TakeableItem item = loadTakeableItem((JSONObject)itemJson);
            takeableItems.add(item);
        }
        return new Inventory(takeableItems);
    }

    private Item loadItem(JSONObject itemJson) {
        if (itemJson.getString("Type").equals("OneShot"))
            return loadOneShotItem(itemJson);
        else if (itemJson.getString("Type").equals("Quest"))
            return loadQuestItem(itemJson);
        else if (itemJson.getString("Type").equals("Consumable"))
            return loadConsumableItem(itemJson);
        else if (itemJson.getString("Type").equals("Weapon"))
            return loadWeaponItem(itemJson);
        else if (itemJson.getString("Type").equals("Wearable"))
            return loadWearableItem(itemJson);
        else if (itemJson.getString("Type").equals("Wearable"))
            return loadWearableItem(itemJson);
        else{
            System.out.println("ERROR: Item not loaded properly -- Type string given: " + itemJson.getString("Type"));
            return null;
        }
    }

    private OneshotItem loadOneShotItem(JSONObject itemJson) {
        String name = itemJson.getString("Name");
        Command command = loadCommand(itemJson.getJSONObject("Command"));
        Boolean isActive = itemJson.getBoolean("IsActive");
        return new OneshotItem(name, command, !isActive);
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
        //TODO reinitialize spawn observers
        return new WeaponItem(itemJson.getString("Name"),
                            itemJson.getBoolean("OnMap"),
                            itemJson.getInt("Damage"),
                            itemJson.getInt("AttackSpeed"),
                            loadSkillType(itemJson.getString("RequiredSkill")),
                            itemJson.getInt("MaxRadius"),
                            itemJson.getLong("ExpansionInterval"),
                            itemJson.getLong("UpdateInterval"),
                            loadInfluenceType(itemJson.getString("InfluenceType")),
                            loadSkillCommand(itemJson.getJSONObject("Command")));

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
        return new WearableItem(itemJson.getString("Name"), itemJson.getBoolean("OnMap"),
                loadReversibleCommand(itemJson.getJSONObject("ReversableCommand")), loadEquipType(itemJson.getString("EquipType")));
    }

    private SkillCommand loadSkillCommand(JSONObject skillCommandJSON) {
        return null;
    }

    private Command loadCommand(JSONObject commandJson) {
        if (commandJson.getString("Name").equals("Transition"))
            return loadTransitionCommand(commandJson);
        else if (commandJson.getString("Name").equals("Confuse"))
            return loadConfuseCommand(commandJson);
        else if (commandJson.getString("Name").equals("MakeFriendly"))
            return loadMakeFriendlyCommand(commandJson);
        else if (commandJson.getString("Name").equals("ModifyHealth"))
            return loadModifyHealthCommand(commandJson);
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

    private PickPocketCommand loadPickPocketCommand(JSONObject commandJson) {
        // TODO: need to save? Entity caster is issue
        return null;
    }

    private ParalyzeCommand loadParalyzeCommand(JSONObject commandJson) {
        // TODO: need to save? Entity caster is issue
        return null;
    }

    private ObserveCommand loadObserveCommand(JSONObject commandJson) {
        return new ObserveCommand(commandJson.getInt("Level"), commandJson.getInt("Effectiveness"));
    }

    private ModifyStaminaRegenCommand loadModifyStaminaRegenCommand(JSONObject commandJson) {
        return new ModifyStaminaRegenCommand(loadSkillType(commandJson.getString("SkillType")), commandJson.getInt("Level"),
                commandJson.getInt("Effectiveness"), commandJson.getDouble("Factor"));
    }

    private ModifyHealthCommand loadModifyHealthCommand(JSONObject commandJson) {
        return new ModifyHealthCommand(loadSkillType(commandJson.getString("SkillType")), commandJson.getInt("Level"),
                commandJson.getInt("Effectiveness"));
    }

    private MakeFriendlyCommand loadMakeFriendlyCommand(JSONObject commandJson) {
        // TODO: need to save? Entity caster is issue
        return null;
    }

    private ConfuseCommand loadConfuseCommand(JSONObject commandJson) {
        // TODO: need to save? Entity caster is issue
        return null;
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
        // TODO: add the rest
        return null;
    }

    private ReversibleCommand loadMakeConfusedCommand(JSONObject reversableCommandJson) {
        // TODO
        return null;
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

    private NpcEntityController loadNpcEntityController(String entityType, Entity entity,
                                                        Equipment equipment, Coordinate entityLocation,
                                                        ArrayList<ControllerAction> actions, AI AggroAi, AI nonAggroAi,
                                                        boolean isAggro){
        if (entityType.equals("Friendly")){

        }
        else if (entityType.equals("Hostile")){

        }
        else if (entityType.equals("Patrol")){

        }
        else if (entityType.equals("Vehicle")){

        }
        else{
            System.out.println("ERROR: Entity type not loaded properly -- String given: " + entityType);
            return null;
        }

        // to stop compilation errors
        return new NpcEntityController(null, null, null, null, null, null, false);
    }

    private List<ControllerAction> loadPlayerControllerActions() {
        // TODO
        return new ArrayList<ControllerAction>();
    }

    private void setTransitionCommands() {
        for (TransitionCommandHolder holder : transitionCommands){
            String mapId = holder.getMapId();
            World world = idMappings.get(mapId);
            Coordinate coordinate = holder.getCoordinate();
            TransitionCommand transitionCommand = holder.getTransitionCommand();
            transitionCommand.setTargetWorld(world);
            transitionCommand.setStartingCoordinate(coordinate);
            transitionCommand.setTransitionObserver(game);
            Tile tile = world.getTileForCoordinate(coordinate);
            // TODO: add transitionCommand to tile
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
