package gameview;

import commands.skillcommands.ModifyHealthCommand;
import entity.entitycontrol.AI.FriendlyAI;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitymodel.Entity;
import entity.entitymodel.EntityStats;
import entity.entitymodel.Equipment;
import entity.entitymodel.Inventory;
import gameobject.GameObject;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.displayable.Displayable;
import items.takeableitems.WeaponItem;
import maps.Influence.InfluenceType;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.OverWorldTile;
import maps.world.Game;
import maps.world.LocalWorld;
import maps.world.OverWorld;
import maps.world.World;
import skills.SkillType;
import utilities.Coordinate;
import utilities.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameViewMaker
{
    private Map<GameObject, Displayable> spriteMap;
    private Map<World, WorldDisplayable> worldDisplayableMap;

    public GameViewMaker()
    {
        spriteMap = new HashMap<>();
        worldDisplayableMap = new HashMap<>();
    }

    public GameDisplayState makeGameDisplayState(GamePanel panel)
    {
        spriteMap = ImageMaker.makeDefaultMap();
        worldDisplayableMap = new HashMap<>();

        // OverWorld:

        Map<Coordinate, OverWorldTile> overworldMap = new HashMap<>();

        // Manual approach:

        OverWorldTile tile = new OverWorldTile(new HashSet<>(), Terrain.GRASS, null);
        overworldMap.put(new Coordinate(0, 0), tile);

        OverWorldTile tile2 = new OverWorldTile(new HashSet<>(), Terrain.WATER, null);
        overworldMap.put(Direction.NE.getOffsetCoordinate(), tile2);

        OverWorldTile tile3 = new OverWorldTile(new HashSet<>(), Terrain.MOUNTAIN, null);

        overworldMap.put(Direction.S.getOffsetCoordinate(), tile3);


        // expandOverworld approach:

        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        /*
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.MOUNTAIN);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.WATER);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);
        expandOverworld(overworldMap, Terrain.GRASS);

        for(int i = 0; i < 30; i++)
        {
            expandOverworld(overworldMap, Terrain.WATER);
        }
        */


        // Parallelogram approach:
        /*
        for(int i = -64; i < 64; i++)
        {
            for(int j = -64; j < 64; j++)
            {
                if(i != 0 || j != 0)
                {
                    OverWorldTile newTile = new OverWorldTile();
                    newTile.add(Terrain.GRASS);
                    overworldMap.put(new Coordinate(i, j), newTile);
                }
            }
        }
        */

        Entity player = new Entity();

        Coordinate npcLoc = new Coordinate(-2, 0);
        Entity npc = createNPC (npcLoc, player, true);
        WeaponItem w = new WeaponItem ("Bob", false, 3, 1, SkillType.TWOHANDEDWEAPON, 5, 1, 1, InfluenceType.CIRCULARINFLUENCE, new ModifyHealthCommand(SkillType.TWOHANDEDWEAPON, 100, -10));
        npc.getController().getEquipment().add(w);

        spriteMap.put(player, ImageMaker.makeEntityDisplayable(player));
        spriteMap.put(npc, ImageMaker.makeEntityDisplayable(npc));

        //tile.setEntity(player);

        overworldMap.get(new Coordinate(2, 1)).setEntity(player);
        overworldMap.get(npcLoc).setEntity(npc);

        System.out.println("Tiles in overworld: " + overworldMap.keySet().size());

        OverWorld overworld = new OverWorld(overworldMap);

        //must add overworld as observer
        w.registerObserver(overworld);

        WorldDisplayable overworldDisplayable = new WorldDisplayable(new Point(0, 0), 0, overworld);
        worldDisplayableMap.put(overworld, overworldDisplayable);

        // LocalWorlds:
        List<LocalWorld> localWorldsList = new ArrayList<>();

        Game game = new Game(overworld, overworld, localWorldsList, 0, player);
        game.setPlayerController(new HumanEntityController(player, new Equipment(10, new Inventory(), player), game.getCoordinate(player), new ArrayList<>(), panel));

        return new GameDisplayState(panel.getSize(), new Game(overworld, overworld, localWorldsList, 0, player), spriteMap, worldDisplayableMap, overworld);
    }

    // todo: expandOverworld is very inefficient right now
    private void expandOverworld(Map<Coordinate, OverWorldTile> map, Terrain terrain)
    {
        for(Coordinate c : new HashSet<>(map.keySet()))
        {
            for(Direction d : Direction.values())
            {
                Coordinate newCoord = c.add(d.getOffsetCoordinate());//new Coordinate(c.getX() + d.getDx(), c.getY() + d.getDy());
                if(!map.containsKey(newCoord))
                {
                    OverWorldTile newTile = new OverWorldTile(new HashSet<>(), terrain, null);
                    map.put(newCoord, newTile);
                }
            }
        }
    }

    private Entity createNPC (Coordinate loc, Entity aggroTarget, boolean isHostile) {

        Map <SkillType, Integer> skills = new HashMap<>();
        skills.put(SkillType.TWOHANDEDWEAPON, 100);

        Set <Terrain> compatible = new HashSet<>();
        compatible.add(Terrain.GRASS);

        EntityStats stats = new EntityStats(skills, 1, 10, 10, 10, 10, 1, 98, 5, 5, 10, 10, false, false, compatible);

        Inventory i = new Inventory(new ArrayList<>());

        Entity entity = new Entity(new Vector(Direction.NULL, 0), stats, new ArrayList<>(), new ArrayList<>(), i, true, "Tim");

        Equipment e = new Equipment(5, i, entity);

        HostileAI hostil = new HostileAI(entity.getActeeInteractions(), aggroTarget, new HashMap<>());
        FriendlyAI friendly = new FriendlyAI(entity.getActeeInteractions(), new HashMap<>(), false);
        NpcEntityController controller = new NpcEntityController(entity, e, loc, new ArrayList<>(), hostil, friendly, isHostile);
        entity.setController(controller);

        return entity;
    }
}