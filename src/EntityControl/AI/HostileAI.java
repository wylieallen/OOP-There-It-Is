package EntityControl.AI;

import EnitityModel.Entity;
import GameObject.GameObjectContainer;
import Utilities.Coordinate;

import java.util.Map;

public class HostileAI extends AI {

    private Entity target;

    @Override
    public void nextAction(Map<Coordinate, GameObjectContainer> map, Entity e) {
        //TODO: make it chase and attack the target
    }
}
