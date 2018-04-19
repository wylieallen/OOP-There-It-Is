package savingloading;

import commands.Command;
import commands.TransitionCommand;
import commands.reversiblecommands.ReversibleCommand;
import commands.skillcommands.*;
import entity.entitycontrol.AI.AI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.*;
import entity.entitymodel.interactions.*;
import gameview.GamePanel;
import items.takeableitems.*;
import maps.tile.Direction;
import maps.world.Game;
import gameview.GameDisplayState;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import org.json.*;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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

    public void loadGame (String saveFileName) throws FileNotFoundException {
        loadFileToJson(saveFileName);
        loadPlayer(gameJson.getJSONObject("Player"));
        loadOverWorld(gameJson.getJSONObject("OverWorld"));
        loadLocalWorlds(gameJson.getJSONObject("LocalWorlds"));
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
        // TODO: create ControllerActions
        List<EntityInteraction> actorInteractions = loadActorInteractions(playerJson.getJSONArray("ActorInteractions"));

    }

    private void loadOverWorld(JSONObject overWorld) {

    }

    private void loadLocalWorlds(JSONObject localWorlds){

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
        }
        return new Inventory(takeableItems);
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
        return new WeaponItem(itemJson.getString("Name"), itemJson.getBoolean("OnMap"), loadCommand(itemJson.getJSONObject("Command")),
                itemJson.getInt("Damage"), itemJson.getInt("AttackSpeed"), loadSkillType(itemJson.getString("RequiredSkill")));
    }

    private WearableItem loadWearableItem(JSONObject itemJson) {
        return new WearableItem(itemJson.getString("Name"), itemJson.getBoolean("OnMap"),
                loadReversibleCommand(itemJson.getJSONObject("ReversableCommand")), loadEquipType(itemJson.getString("EquipType")));
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
        // TODO
        return null;
    }

    private ReversibleCommand loadReversibleCommand(JSONObject reversableCommandJson) {
        if (reversableCommandJson.getString("Name").equals("MakeConfused"))
            return loadMakeConfusedCommand(reversableCommandJson);
        //
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

    public GameDisplayState getGameDisplayState () {
        return gameDisplay;
    }

    public Game getGame () {
        return game;
    }

    public Entity getPlayer () {
        return player;
    }

}
