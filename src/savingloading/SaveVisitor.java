package savingloading;

import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.AI.PatrolAI;
import entity.entitycontrol.AI.PetAI;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.Entity;
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
import maps.tile.Tile;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import items.Item;
import org.json.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dontf on 4/13/2018.
 */
public class SaveVisitor implements Visitor {

    private String fileName;
    private JSONObject saveFileJson;
    private JSONObject playerJson;
    private JSONObject overWorldJson;
    private List<JSONObject> localWorldJsons;

    public SaveVisitor(String saveFileName){
        this.fileName = "resources/savefiles/" + saveFileName + ".json";
        saveFileJson = new JSONObject();
        playerJson = new JSONObject();
        overWorldJson = new JSONObject();
        localWorldJsons = new ArrayList<JSONObject>();
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
    }

    public void visitVehicle(Vehicle v){
        JSONArray entitiesJson = getCurrentLocalWorldJson().getJSONArray("Entities");
        JSONObject entityJson = new JSONObject();
        entityJson.put("Type","Vehicle");
        entitiesJson.put(entityJson);
    }

    @Override
    public void visitHumanEntityController(HumanEntityController h) {
        playerJson.put("Type", "Player");
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

    private void addNonPlayerEntity(String type){
        JSONArray entitiesJson = getCurrentLocalWorldJson().getJSONArray("Entities");
        JSONObject entityJson = new JSONObject();
        entityJson.put("Type",type);
        entitiesJson.put(entityJson);
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
