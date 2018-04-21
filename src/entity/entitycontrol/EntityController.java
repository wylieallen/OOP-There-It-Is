package entity.entitycontrol;


import entity.entitycontrol.controllerActions.ControllerAction;
import entity.entitymodel.Entity;
import entity.entitymodel.Equipment;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import maps.tile.Tile;
import savingloading.Visitable;
import spawning.SpawnObserver;
import utilities.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public abstract class EntityController implements Visitable{

    private Entity controlledEntity;
    private Equipment equipment;
    private Coordinate entityLocation;
    private Collection<ControllerAction> actions;
    //make sure if we load in and we are on a vehicle that this is set correctly
    private boolean inVehicle = false;
    private boolean dismounting = false;
    private Vehicle mount;

    public EntityController(Entity entity, Equipment equipment,
                            Coordinate entityLocation, Collection<ControllerAction> actions) {
        this.controlledEntity = entity;
        this.equipment = equipment;
        this.entityLocation = entityLocation;
        this.actions = actions;
        if (this.actions == null)
            this.actions = new ArrayList<>();
    }


    //this is the specific funcitonality that each entity controller is responsible for implementing
    protected abstract void processController();
    public abstract void interact(EntityController interacter);
    public abstract void notifyFreeMove(Entity e);
    public abstract void notifyInventoryManagment(Entity e);
    public abstract void notifyInteraction(Entity player, Entity interactee);
    public abstract void notifyShopping(Entity trader1, Entity trader2);
    public abstract void notifyLevelUp(Entity e);
    public abstract void notifyMainMenu(Entity e);

    // used to update ai;
    public abstract void updateMap (Map <Coordinate, Tile> map);

    public abstract void enrage(Entity e);
    public abstract void pacify();

    protected Entity getControlledEntity() { return controlledEntity; }


    //this is the functionality all entity controllers need

    public final void update(Map<Coordinate, Tile> mapOfContainers){
        boolean found = false;

        //find the entity in the map and set his location
        Collection<GameObject> gameObjectList;
        //iterate through all the entries in the map of GameObjectContainers
        for(Map.Entry<Coordinate, Tile> container : mapOfContainers.entrySet()){
            gameObjectList = container.getValue().getGameObjects();
            //iterate through all the gameObjects in each gameObjectContainer
            for(GameObject gameObject : gameObjectList){
                if((!inVehicle && gameObject == controlledEntity) || (inVehicle && gameObject == mount)){
                    entityLocation = container.getKey();
                    found = true;
                }
            }
        }
        if(!found){
            throw new java.lang.RuntimeException("EntityController::update() : The controlled entity is not in this list of GameObjectContainers");
        }

        //iterate through the controllerActions and tell them to update so they can do their action if they need to
        for(ControllerAction action : actions){
            action.update();
        }

        // checking if entity is trying to dismount
        if(dismounting) {
            if (dismountTo(mapOfContainers.get(entityLocation))) {
                inVehicle = false;
                mount.removeDriver();
                mount = null;
                dismounting = false;
            }
        }

    }

    public final void notifyMount(Vehicle mount){
        if(!inVehicle){
            inVehicle = true;
            this.mount = mount;
        }
        else{
            throw new java.lang.RuntimeException("EntityController::notifyMount() : The controlled entity cannot mount because it is already in a vehicle");
        }
    }

    public final void notifyDismount(){
        if(inVehicle){
            dismounting = true;
        }
        else{
            throw new java.lang.RuntimeException("EntityController::notifyDismount() : The controlled entity cannot dismount because it is not currently in a vehicle");
        }
    }

    public boolean isInVehicle(){
        return inVehicle;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Coordinate getEntityLocation(){
        return entityLocation;
    }

    public boolean has(GameObject o) {
        return equipment.has(o);
    }

    public void updateSpawnObservers(SpawnObserver oldObserver, SpawnObserver newObserver) {
        if(equipment != null)
            equipment.updateSpawnObservers(oldObserver, newObserver);
    }

    private final boolean dismountTo (Tile toTile) {
        return toTile.placeEntityOnNeighbor(controlledEntity);
    }

}
