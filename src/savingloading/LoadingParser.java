package savingloading;

import entity.entitycontrol.AI.AI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Equipment;
import gameview.GamePanel;
import maps.tile.Direction;
import maps.world.Game;
import gameview.GameDisplayState;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import org.json.*;
import skills.SkillType;
import utilities.Coordinate;

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
        loadLocalWorlds(gameJson.getJSONArray("LocalWorlds"));
    }

    private void loadFileToJson(String saveFileName) throws FileNotFoundException {
        String filePath = "resources/savefiles/" + saveFileName + ".json";
        Scanner scanner = new Scanner( new File(filePath) );
        String jsonText = scanner.useDelimiter("\\A").next();
        scanner.close();
        gameJson = new JSONObject(jsonText);
    }

    private void loadPlayer(JSONObject playerJson){
        JSONObject entityStatsJson = playerJson.getJSONObject("Stats");
        EntityStats entityStats = loadEntityStats(entityStatsJson);
    }

    private void loadOverWorld(JSONObject overWorld) {

    }

    private void loadLocalWorlds(JSONArray localWorlds){

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
            int level = (int) entityStatsJson.get(skillString);
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
