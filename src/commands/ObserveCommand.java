package commands;

import entity.entitycontrol.EntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.Inventory;
import savingloading.Visitor;

// not sure how this will work
public class ObserveCommand implements Command {

    private int distance;
    private int level;

    private EntityController controller;

    public ObserveCommand(int level) {
        this.level = level;
        distance = 0;
    }

    public ObserveCommand(int distance, int level) {
        this.distance = distance;
        this.level = level;
    }

    @Override
    public void trigger(Entity e) {
        trigger(e, distance);
    }

    //This will take in the entity that is being observed and the distance they are from the entity that spawned the
    //observation influence area and make some observation
    @Override
    public void trigger(Entity e, int distance) {
        String message = "";

        int mode = (int)(Math.random() * 4);
        switch(mode) {
            //health observation
            case 0:
                message = healthObservation(e, distance);
                break;
            //move speed observation
            case 1:
                message = moveSpeedObservation(e, distance);
                break;
            //gold observation
            case 2:
                message = goldObservation(e, distance);
                break;
            //item observation
            case 3:
                message = itemObservation(e.getInventory(), distance);
                break;
            default:
                message = "*wisper from the dark*  you are not alone";
        }

        if(controller != null)
            controller.notifyAllObservers(message);
    }

    //0 accuracy represents perfect accuracy, -1 represents an underestimation by 100%,
    //1 represents an overestimation by 100%
    private double getAccuracy(int distance) {
        //get a random number [0, 2)
        double accuracy = Math.random() * 2;

        //scale to the range [-1, 1)
        accuracy -= 1;

        accuracy = worsenAccuracy(accuracy, 0.1 * distance);
        accuracy = improveAccuracy(accuracy, 0.1 * level);

        return accuracy;
    }

    public double improveAccuracy(double accuracy, double factor) {
        double newAccuracy = accuracy - (accuracy * factor);
        if(newAccuracy < 0 && accuracy > 0
                || newAccuracy > 0 && accuracy < 0) {
            newAccuracy = 0;
        }
        return newAccuracy;
    }

    public double worsenAccuracy(double accuracy, double factor) {
        return accuracy + (accuracy * factor);
    }

    private String healthObservation(Entity e, int distance) {
        int health = e.getCurrHealth();
        health += health * getAccuracy(distance);
        return "This entity has " + health + " health.";
    }

    private String moveSpeedObservation(Entity e, int distance) {
        double moveSpeed = e.getBaseMoveSpeed();
        moveSpeed += moveSpeed * getAccuracy(distance);
        return "This entity's move speed is " + moveSpeed + ".";
    }

    private String goldObservation(Entity e, int distance) {
        double gold = e.getGold();
        gold += gold * getAccuracy(distance);
        return "This entity has " + gold + " gold.";
    }

    private String itemObservation(Inventory inventory, int distance) {
        String itemName = inventory.getRandomItemName();
        if(itemName.equals(""))
            return "This entity has no items.";
        else
            return "This entity has an item named " + itemName + ".";
    }

    public void setEntityController (EntityController caster) {
        controller = caster;
    }

    @Override
    public void accept(Visitor v) {
        v.visitObserveCommand(this);
    }

    public int getLevel() {
        return level;
    }

    public int getDistance() {
        return distance;
    }

}
