package gameview;

import commands.KillCommand;
import commands.LevelUpCommand;
import commands.ModifyHealthCommand;
import commands.TransitionCommand;
import commands.reversiblecommands.BuffHealthCommand;
import commands.reversiblecommands.TimedStaminaRegenCommand;
import commands.skillcommands.SkillCommand;
import entity.entitycontrol.AI.HostileAI;
import entity.entitycontrol.AI.PetAI;
import entity.entitycontrol.EntityController;
import entity.entitycontrol.HumanEntityController;
import entity.entitycontrol.NpcEntityController;
import entity.entitycontrol.controllerActions.DismountAction;
import entity.entitymodel.*;
import entity.entitymodel.interactions.PickPocketInteraction;
import entity.entitymodel.interactions.TalkInteraction;
import entity.entitymodel.interactions.UseItemInteraction;
import entity.vehicle.Vehicle;
import gameobject.GameObject;
import gameview.displayable.sprite.WorldDisplayable;
import gameview.util.ImageMaker;
import guiframework.displayable.Displayable;
import items.InteractiveItem;
import items.Item;
import items.ItemFactory;
import items.takeableitems.QuestItem;
import items.takeableitems.WeaponItem;
import items.takeableitems.WearableItem;
import maps.Influence.InfluenceType;
import maps.entityimpaction.AreaEffect;
import maps.entityimpaction.InfiniteAreaEffect;
import maps.entityimpaction.OneShotAreaEffect;
import maps.entityimpaction.Trap;
import maps.movelegalitychecker.Obstacle;
import maps.movelegalitychecker.Terrain;
import maps.tile.Direction;
import maps.tile.LocalWorldTile;
import maps.tile.OverWorldTile;
import maps.trajectorymodifier.River;
import maps.world.*;
import savingloading.LoadingParser;
import skills.SkillType;
import spawning.SpawnObservable;
import utilities.Coordinate;
import utilities.Vector;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class GameViewMaker
{
    private Map<GameObject, Displayable> spriteMap;
    private Map<SpawnObservable, Displayable> spawnerMap = new HashMap<SpawnObservable, Displayable>();
    private Map<World, WorldDisplayable> worldDisplayableMap;
    private Entity player;

    private boolean loadFromFile = false;

    private Game game;

    public GameViewMaker()
    {
        spriteMap = new HashMap<>();
        worldDisplayableMap = new HashMap<>();
    }

    public GameDisplayState makeGameDisplayState(GamePanel panel)
    {
        if (loadFromFile){
            LoadingParser loadingParser = new LoadingParser();
            try {
                loadingParser.loadGame("test", panel);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            GameDisplayState gameDisplayState = loadingParser.getGameDisplayState();
            game = loadingParser.getGame();
            return gameDisplayState;
        }
        spriteMap = ImageMaker.makeDefaultMap();
        worldDisplayableMap = new HashMap<>();

        // OverWorld:

        Map<Coordinate, OverWorldTile> overworldMap = new HashMap<>();

        // Manual approach:

        OverWorldTile tile = new OverWorldTile(new HashSet<>(), Terrain.SPACE, null);
        overworldMap.put(new Coordinate(0, 0), tile);

        /*OverWorldTile tile2 = new OverWorldTile(new HashSet<>(), Terrain.WATER, null);
        overworldMap.put(Direction.NE.getOffsetCoordinate(), tile2);

        OverWorldTile tile3 = new OverWorldTile(new HashSet<>(), Terrain.MOUNTAIN, null);
        overworldMap.put(Direction.S.getOffsetCoordinate(), tile3);*/


        // expandOverworld approach:

        for(int i = 0; i < 8; ++i){
            expandOverworld(overworldMap, Terrain.SPACE);
        }

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
        player.setName("Smasher");
        player.addCompatibleTerrain(Terrain.SPACE);
        player.setMovementObserver(panel);
        player.addToInventory(new QuestItem("Radio", false, 0));
        player.addActorInteraction(new PickPocketInteraction());
        player.addActorInteraction(new UseItemInteraction());
        String playerClass = "Sneak";
        switch(playerClass) {
            case "Smasher":
                player.addSkill(SkillType.BRAWLING, 1);
                player.addSkill(SkillType.ONEHANDEDWEAPON, 1);
                player.addSkill(SkillType.TWOHANDEDWEAPON, 1);
                break;
            case "Summoner":
                player.addSkill(SkillType.ENCHANTMENT, 1);
                player.addSkill(SkillType.BOON, 1);
                player.addSkill(SkillType.BANE, 1);
                player.addSkill(SkillType.STAFF, 1);
                break;
            case "Sneak":
                player.addSkill(SkillType.CREEP, 1);
                player.addSkill(SkillType.DETECTANDREMOVETRAP, 1);
                player.addSkill(SkillType.RANGEDWEAPON, 1);
                break;
        }

        Coordinate npcLoc = new Coordinate(-2, 0);

        Entity npc = createNPC (npcLoc, player, true, false);

//        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, 0, 10, new ModifyHealthCommand(-2), new ModifyHealthCommand(2));
//        WeaponItem w = new WeaponItem ("Bob", false, 0, 500, SkillType.TWOHANDEDWEAPON, 8, 1000, 1, InfluenceType.CIRCULARINFLUENCE, skill);
//        npc.getController().getEquipment().add(w);


        //tile.setEntity(player);

        overworldMap.get(new Coordinate(2, 1)).setEntity(player);
        spriteMap.put(player, ImageMaker.makeEntityDisplayable(player));

        System.out.println("Tiles in overworld: " + overworldMap.keySet().size());

        OverWorld overworld = new OverWorld(overworldMap);

        WorldDisplayable overworldDisplayable = new WorldDisplayable(new Point(0, 0), 0, overworld);
        worldDisplayableMap.put(overworld, overworldDisplayable);

        // LocalWorlds:
        List<FoggyWorld> foggyWorldsList = new ArrayList<>();

        //add first local world to local world list
        LocalWorld localWorld = createLocalWorld1();
        FoggyWorld foggyWorld = new FoggyWorld(localWorld, player);
        foggyWorldsList.add(foggyWorld);

        //create local world displayable
        WorldDisplayable foggyWorldDisplayable = new WorldDisplayable(new Point(0, 0), 0, foggyWorld);
        worldDisplayableMap.put(foggyWorld.getLocalWorld(), foggyWorldDisplayable);

        //add second local world to local world list
        foggyWorld = new FoggyWorld(createLocalWorld2(), player);
        foggyWorldsList.add(foggyWorld);

        //create local world displayable
        foggyWorldDisplayable = new WorldDisplayable(new Point(0, 0), 0, foggyWorld);
        worldDisplayableMap.put(foggyWorld.getLocalWorld(), foggyWorldDisplayable);

        //add third local world to local world list
        foggyWorld = new FoggyWorld(createLocalWorld3(), player);
        foggyWorldsList.add(foggyWorld);

        //create local world displayable
        foggyWorldDisplayable = new WorldDisplayable(new Point(0, 0), 0, foggyWorld);
        worldDisplayableMap.put(foggyWorld.getLocalWorld(), foggyWorldDisplayable);

        //add fourth local world to local world list
        foggyWorld = new FoggyWorld(createLocalWorld4(), player);
        foggyWorldsList.add(foggyWorld);

        //create local world displayable
        foggyWorldDisplayable = new WorldDisplayable(new Point(0, 0), 0, foggyWorld);
        worldDisplayableMap.put(foggyWorld.getLocalWorld(), foggyWorldDisplayable);

        game = new Game(overworld, overworld, foggyWorldsList, 0, player);
        game.setTransitionObserver(panel);
        game.setPlayerController(new HumanEntityController(player, new Equipment(10, player.getInventory(), player), game.getCoordinate(player), panel));

        player.getController().addAction(new DismountAction(player.getController()));
        //ObserveAction observe = new ObserveAction(player);
        //observe.setController(player.getController());
        //observe.registerObserver(foggyWorldsList.get(1));
        //player.getController().addAction(observe);
        //spawnerMap.put(observe, ImageMaker.makeYellowProjectileDisplayable());

        //setup world transitions
        //local world 1
        InteractiveItem localWorld1Entrance = new InteractiveItem("Encounter 1", new TransitionCommand(foggyWorldsList.get(0).getLocalWorld(), new Coordinate(0, 0), game));
        spriteMap.put(localWorld1Entrance, ImageMaker.makeEncounterDisplayable1());
        overworld.getTile(new Coordinate(1, -2)).setEncounter(localWorld1Entrance);

        InteractiveItem localWorld1Exit = new InteractiveItem("Teleporter", new TransitionCommand(overworld, new Coordinate(0, 0), game));
        spriteMap.put(localWorld1Exit, ImageMaker.makeTeleporterDisplayable());
        foggyWorldsList.get(0).getTile(new Coordinate(-1, -1)).addEI(localWorld1Exit);

        //local world 2
        InteractiveItem localWorld2Entrance = new InteractiveItem("Encounter 2", new TransitionCommand(foggyWorldsList.get(1).getLocalWorld(), new Coordinate(0, 0), game));
        spriteMap.put(localWorld2Entrance, ImageMaker.makeEncounterDisplayable2());
        overworld.getTile(new Coordinate(-6, 1)).setEncounter(localWorld2Entrance);

        InteractiveItem localWorld2Exit = new InteractiveItem("Teleporter", new TransitionCommand(overworld, new Coordinate(0, 0), game));
        spriteMap.put(localWorld2Exit, ImageMaker.makeTeleporterDisplayable());
        foggyWorldsList.get(1).getTile(new Coordinate(-1, -1)).addEI(localWorld2Exit);

        //local world 3
        InteractiveItem localWorld3Entrance = new InteractiveItem("Encounter 3", new TransitionCommand(foggyWorldsList.get(2).getLocalWorld(), new Coordinate(0, 0), game));
        spriteMap.put(localWorld3Entrance, ImageMaker.makeEncounterDisplayable1());
        overworld.getTile(new Coordinate(-5, 4)).setEncounter(localWorld3Entrance);

        InteractiveItem localWorld3Exit = new InteractiveItem("Teleporter", new TransitionCommand(overworld, new Coordinate(0, 0), game));
        spriteMap.put(localWorld3Exit, ImageMaker.makeTeleporterDisplayable());
        foggyWorldsList.get(2).getTile(new Coordinate(-1, -1)).addEI(localWorld3Exit);

        //local world 4
        InteractiveItem localWorld4Entrance = new InteractiveItem("Encounter 4", new TransitionCommand(foggyWorldsList.get(3).getLocalWorld(), new Coordinate(0, 0), game));
        spriteMap.put(localWorld4Entrance, ImageMaker.makeEncounterDisplayable2());
        overworld.getTile(new Coordinate(4, 3)).setEncounter(localWorld4Entrance);

        InteractiveItem localWorld4Exit = new InteractiveItem("Teleporter", new TransitionCommand(overworld, new Coordinate(0, 0), game));
        spriteMap.put(localWorld4Exit, ImageMaker.makeTeleporterDisplayable());
        foggyWorldsList.get(3).getTile(new Coordinate(-1, -1)).addEI(localWorld4Exit);

        //Transition Area Effect
        AreaEffect teleport = new InfiniteAreaEffect(new TransitionCommand(foggyWorldsList.get(3).getLocalWorld(), new Coordinate(4, -9), game), 1, 0, "Teleport Area Effect 1");
        foggyWorldsList.get(3).getTile(new Coordinate(-4, -6)).addEI(teleport);
        spriteMap.put(teleport, ImageMaker.makeTeleporterDisplayable2());

        teleport = new InfiniteAreaEffect(new TransitionCommand(foggyWorldsList.get(3).getLocalWorld(), new Coordinate(-4, -5), game), 1, 0, "Teleport Area Effect 1");
        foggyWorldsList.get(3).getTile(new Coordinate(4, -10)).addEI(teleport);
        spriteMap.put(teleport, ImageMaker.makeTeleporterDisplayable2());

       return new GameDisplayState(panel.getSize(), game, spriteMap, spawnerMap, worldDisplayableMap, overworld);
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

    private Entity createNPC (Coordinate loc, Entity aggroTarget, boolean isHostile, boolean canMove) {

        Map <SkillType, Integer> skills = new HashMap<>();
        skills.put(SkillType.TWOHANDEDWEAPON, 10);

        Set <Terrain> compatible = new HashSet<>();
        if(canMove)
            compatible.add(Terrain.GRASS);

        EntityStats stats = new EntityStats(skills, 1, 10, 10, 100, 100, 1, 98, 5, 6, 10, 10, false, false, compatible);

        Inventory i = new Inventory(new ArrayList<>());

        Entity entity = new Entity(new Vector(Direction.NULL, 0), stats, new ArrayList<>(), new ArrayList<>(), i, true, "Tim");
        Equipment e = new Equipment(5, i, entity);

        HostileAI hostile = new HostileAI(entity.getActeeInteractions(), aggroTarget, new HashMap<>());
        PetAI friendly = new PetAI(entity.getActeeInteractions(), player, new HashMap<>(), false);
        NpcEntityController controller = new NpcEntityController(entity, e, loc, hostile, friendly, isHostile);
        entity.setController(controller);
        entity.addActeeInteraction(new TalkInteraction(new ArrayList<String>()));

        return entity;
    }

    private LocalWorld createLocalWorld1() {

        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();

        for(int x = -10; x <= 10; ++x) {
            for(int z = -10; z <= 10; ++z) {
                Coordinate coordinate = new Coordinate(x, z);
                if(coordinate.y() > 10 || coordinate.y() < -10) {
                    continue;
                }

                LocalWorldTile tile = new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>());
                tiles.put(coordinate, tile);

            }
        }

        LocalWorld world = new LocalWorld(tiles, new HashSet<>());


        Vehicle thingy = createVehicle(new Coordinate(2, 2));
        thingy.hurtEntity(990);
        world.getTile(new Coordinate(4, 4)).setEntity(thingy);
        spriteMap.put(thingy, ImageMaker.makeVehicleDisplayable());

        //Add npc
        Coordinate npcLoc = new Coordinate(-2, 0);
        Entity npc = createNPC (npcLoc, player, true, true);


        WeaponItem axe = ItemFactory.makeAxe(world, false);
//        npc.getController().getEquipment().add(axe);
//
        SkillCommand skill = new SkillCommand(SkillType.TWOHANDEDWEAPON, npc.getSkillLevel(SkillType.TWOHANDEDWEAPON), -1, new ModifyHealthCommand(), null);
        WeaponItem w = new WeaponItem ("Bob", false, 300, SkillType.TWOHANDEDWEAPON, 5, 5, 1, 1, 300, InfluenceType.LINEARINFLUENCE, skill, false);
        npc.getController().getEquipment().add(w);
        //must add overworld as observer
        w.registerObserver(world);
//        axe.registerObserver(world);

        spawnerMap.put(w, ImageMaker.makeBlueProjectileDisplayable());
//        spawnerMap.put(axe,new ImageDisplayable(new Point(16,16),ImageMaker.makeBorderedCircle(Color.blue),1000));
        world.getTile(npcLoc).setEntity(npc);
        spriteMap.put(npc, ImageMaker.makeEnemyDisplayable1());

        axe = ItemFactory.makeBadAxe(world, true);
        world.getTile(new Coordinate(-6, -2)).addEI(axe);
        spriteMap.put(axe, ImageMaker.makeTwoHandedWeaponDisplayable());
        spawnerMap.put(axe, ImageMaker.makeBlueProjectileDisplayable());

        axe = ItemFactory.makeAxe(world, true);
        world.getTile(new Coordinate(-6, -1)).addEI(axe);
        spriteMap.put(axe, ImageMaker.makeTwoHandedWeaponDisplayable());
        spawnerMap.put(axe, ImageMaker.makeBlueProjectileDisplayable());

        axe = ItemFactory.makeGoodAxe(world, true);
        world.getTile(new Coordinate(-6, 0)).addEI(axe);
        spriteMap.put(axe, ImageMaker.makeTwoHandedWeaponDisplayable());
        spawnerMap.put(axe, ImageMaker.makeBlueProjectileDisplayable());

        WeaponItem sword = ItemFactory.makeBadSword(world, true);
        world.getTile(new Coordinate(-5, -2)).addEI(sword);
        spriteMap.put(sword, ImageMaker.makeLaserSwordDisplayable());
        spawnerMap.put(sword, ImageMaker.makeBlueProjectileDisplayable());

        sword = ItemFactory.makeSword(world, true);
        world.getTile(new Coordinate(-5, -1)).addEI(sword);
        spriteMap.put(sword, ImageMaker.makeLaserSwordDisplayable());
        spawnerMap.put(sword, ImageMaker.makeBlueProjectileDisplayable());

        sword = ItemFactory.makeGoodSword(world, true);
        world.getTile(new Coordinate(-5, 0)).addEI(sword);
        spriteMap.put(sword, ImageMaker.makeLaserSwordDisplayable());
        spawnerMap.put(sword, ImageMaker.makeBlueProjectileDisplayable());

        WeaponItem brawling = ItemFactory.makeBadGlove(world, true);
        world.getTile(new Coordinate(-4, -2)).addEI(brawling);
        spriteMap.put(brawling, ImageMaker.makeBrawlingWeaponDisplayable());
        spawnerMap.put(brawling, ImageMaker.makeBlueProjectileDisplayable());

        brawling = ItemFactory.makeGlove(world, true);
        world.getTile(new Coordinate(-4, -1)).addEI(brawling);
        spriteMap.put(brawling, ImageMaker.makeBrawlingWeaponDisplayable());
        spawnerMap.put(brawling, ImageMaker.makeBlueProjectileDisplayable());

        brawling = ItemFactory.makeGoodGlove(world, true);
        world.getTile(new Coordinate(-4, 0)).addEI(brawling);
        spriteMap.put(brawling, ImageMaker.makeBrawlingWeaponDisplayable());
        spawnerMap.put(brawling, ImageMaker.makeBlueProjectileDisplayable());

        for(int i = 2; i <= 10; ++i) {
            npc = createNPC (new Coordinate(-6, i), player, false, false);
            world.getTile(new Coordinate(-6, i)).setEntity(npc);
            spriteMap.put(npc, ImageMaker.makeEnemyDisplayable1());
        }



        return world;
    }

    private LocalWorld createLocalWorld2() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();

        for(int x = -10; x <= 10; ++x) {
            for(int z = -10; z <= 10; ++z) {
                Coordinate coordinate = new Coordinate(x, z);
                if(coordinate.y() > 10 || coordinate.y() < -10) {
                    continue;
                }

                LocalWorldTile tile = new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>());
                tiles.put(coordinate, tile);
            }
        }

        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        WeaponItem gadget = ItemFactory.makeConfuseGadget(world, true);
        world.getTile(new Coordinate(-6, -2)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable1());
        spawnerMap.put(gadget, ImageMaker.makeYellowProjectileDisplayable());

        gadget = ItemFactory.makeParalyzeGadget(world, true);
        world.getTile(new Coordinate(-6, -1)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable1());
        spawnerMap.put(gadget, ImageMaker.makeYellowProjectileDisplayable());

        gadget = ItemFactory.makePacifyGadget(world, true);
        world.getTile(new Coordinate(-6, 0)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable1());
        spawnerMap.put(gadget, ImageMaker.makeYellowProjectileDisplayable());

        gadget = ItemFactory.makeHealGadget(world, true);
        world.getTile(new Coordinate(-5, -2)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable2());
        spawnerMap.put(gadget, ImageMaker.makeGreenProjectileDisplayable());

        gadget = ItemFactory.makeStaminaRegenGadget(world, true);
        world.getTile(new Coordinate(-5, -1)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable2());
        spawnerMap.put(gadget, ImageMaker.makeGreenProjectileDisplayable());

        gadget = ItemFactory.makeStrongHealGadget(world, true);
        world.getTile(new Coordinate(-5, 0)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable2());
        spawnerMap.put(gadget, ImageMaker.makeGreenProjectileDisplayable());

        gadget = ItemFactory.makeLinearBaneGadget(world, true);
        world.getTile(new Coordinate(-4, -2)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable3());
        spawnerMap.put(gadget, ImageMaker.makeRedProjectileDisplayable());

        gadget = ItemFactory.makeAngularBaneGadget(world, true);
        world.getTile(new Coordinate(-4, -1)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable3());
        spawnerMap.put(gadget, ImageMaker.makeRedProjectileDisplayable());

        gadget = ItemFactory.makeCircularBaneGadget(world, true);
        world.getTile(new Coordinate(-4, 0)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable3());
        spawnerMap.put(gadget, ImageMaker.makeRedProjectileDisplayable());

        gadget = ItemFactory.makeBadStaff(world, true);
        world.getTile(new Coordinate(-3, -2)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable4());
        spawnerMap.put(gadget, ImageMaker.makeBlueProjectileDisplayable());

        gadget = ItemFactory.makeStaff(world, true);
        world.getTile(new Coordinate(-3, -1)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable4());
        spawnerMap.put(gadget, ImageMaker.makeBlueProjectileDisplayable());

        gadget = ItemFactory.makeGoodStaff(world, true);
        world.getTile(new Coordinate(-3, 0)).addEI(gadget);
        spriteMap.put(gadget, ImageMaker.makeGadgetDisplayable4());
        spawnerMap.put(gadget, ImageMaker.makeBlueProjectileDisplayable());

        for(int i = 2; i <= 10; ++i) {
            Entity npc = createNPC (new Coordinate(-6, i), player, false, true);
            world.getTile(new Coordinate(-6, i)).setEntity(npc);
            spriteMap.put(npc, ImageMaker.makeEnemyDisplayable2());
        }

        Vehicle thingy = createVehicle (new Coordinate(-4, -4));
        world.getTile(new Coordinate(-4, -4)).setEntity(thingy);
        spriteMap.put(thingy, ImageMaker.makeVehicleDisplayable());

        return world;
    }

    private LocalWorld createLocalWorld3() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();

        for(int x = -10; x <= 10; ++x) {
            for(int z = -10; z <= 10; ++z) {
                Coordinate coordinate = new Coordinate(x, z);
                if(coordinate.y() > 10 || coordinate.y() < -10) {
                    continue;
                }

                LocalWorldTile tile = new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>());
                tiles.put(coordinate, tile);
            }
        }

        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        WeaponItem gun = ItemFactory.makeBadGun(world, true);
        world.getTile(new Coordinate(-6, -2)).addEI(gun);
        spriteMap.put(gun, ImageMaker.makeRangedWeaponDisplayable());
        spawnerMap.put(gun, ImageMaker.makeRedProjectileDisplayable());

        gun = ItemFactory.makeGun(world, true);
        world.getTile(new Coordinate(-6, -1)).addEI(gun);
        spriteMap.put(gun, ImageMaker.makeRangedWeaponDisplayable());
        spawnerMap.put(gun, ImageMaker.makeRedProjectileDisplayable());

        gun = ItemFactory.makeGoodGun(world, true);
        world.getTile(new Coordinate(-6, 0)).addEI(gun);
        spriteMap.put(gun, ImageMaker.makeRangedWeaponDisplayable());
        spawnerMap.put(gun, ImageMaker.makeRedProjectileDisplayable());


        for(int i = 2; i <= 10; ++i) {
            Entity npc = createNPC (new Coordinate(-6, i), player, false, false);
            world.getTile(new Coordinate(-6, i)).setEntity(npc);
            spriteMap.put(npc, ImageMaker.makeEnemyDisplayable3());
        }

        for(int i = -2; i <= 4; ++i) {
            Trap trap = new Trap(new ModifyHealthCommand(-20), false, 25, false);
            spriteMap.put(trap, ImageMaker.makeTrapDisplayable(trap));
            world.getTile(new Coordinate(6, i)).addMLC(trap);
            world.getTile(new Coordinate(6, i)).addEI(trap);
        }

        return world;
    }

    private LocalWorld createLocalWorld4() {
        Map<Coordinate, LocalWorldTile> tiles = new HashMap<>();

        for(int x = -10; x <= 10; ++x) {
            for(int z = -10; z <= 10; ++z) {
                Coordinate coordinate = new Coordinate(x, z);
                if(coordinate.y() > 10 || coordinate.y() < -10) {
                    continue;
                }
                if((x == 8 && coordinate.y() == 0 && z == -8) || (x == 9 && coordinate.y() == 0 && z == -9) || (x == 7 && coordinate.y() == 0 && z == -7)){
                    LocalWorldTile tile = new LocalWorldTile(new HashSet<>(), Terrain.WATER, null, new HashSet<>(), new HashSet<>());
                    tiles.put(coordinate, tile);
                }
                else if((x == 5 && coordinate.y() == 3 && z == -8) || (x == 6 && coordinate.y() == 3 && z == -9) || (x == 7 && coordinate.y() == 3 && z == -10)){
                    LocalWorldTile tile = new LocalWorldTile(new HashSet<>(), Terrain.MOUNTAIN, null, new HashSet<>(), new HashSet<>());
                    tiles.put(coordinate, tile);
                }
                else {
                    LocalWorldTile tile = new LocalWorldTile(new HashSet<>(), Terrain.GRASS, null, new HashSet<>(), new HashSet<>());
                    if((x == 5 && coordinate.y() == 0 && z == -5) || (x == 4 && coordinate.y() == 0 && z == -4)){
                        Obstacle ob = ItemFactory.makeBarrelObstacle();
                        tile.addMLC(ob);
                        spriteMap.put(ob,ImageMaker.makeBarrelDisplayable());
                    }
                    tiles.put(coordinate, tile);
                }
            }
        }

        LocalWorld world = new LocalWorld(tiles, new HashSet<>());

        Item item = ItemFactory.makeHealthPotion(10);
        world.getTile(new Coordinate(-5, -2)).addEI(item);
        spriteMap.put(item, ImageMaker.makeConsumableDisplayable2());

        AreaEffect damageEffect = new InfiniteAreaEffect(new ModifyHealthCommand(-10), 1000, 0, "Damage Area Effect");
        spriteMap.put(damageEffect, ImageMaker.makeSkullDisplayable());
        tiles.get(new Coordinate(-6, -1)).addEI(damageEffect);
        tiles.get(new Coordinate(-7, -1)).addEI(damageEffect);
        tiles.get(new Coordinate(-6, 0)).addEI(damageEffect);
        tiles.get(new Coordinate(-7, 0)).addEI(damageEffect);

        AreaEffect healEffect = new InfiniteAreaEffect(new ModifyHealthCommand(10), 1000, 0, "Heal Area Effect");
        spriteMap.put(healEffect, ImageMaker.makeHeartDisplayable());
        tiles.get(new Coordinate(-6, 2)).addEI(healEffect);
        tiles.get(new Coordinate(-7, 2)).addEI(healEffect);
        tiles.get(new Coordinate(-6, 3)).addEI(healEffect);
        tiles.get(new Coordinate(-7, 3)).addEI(healEffect);

        AreaEffect killEffect = new OneShotAreaEffect(new KillCommand(), false, "Kill Area Effect");
        spriteMap.put(killEffect, ImageMaker.makeSkullDisplayable());
        tiles.get(new Coordinate(-6, 5)).addEI(killEffect);
        killEffect = new OneShotAreaEffect(new KillCommand(), false, "Kill Area Effect");
        spriteMap.put(killEffect, ImageMaker.makeSkullDisplayable());
        tiles.get(new Coordinate(-7, 5)).addEI(killEffect);
        killEffect = new OneShotAreaEffect(new KillCommand(), false, "Kill Area Effect");
        spriteMap.put(killEffect, ImageMaker.makeSkullDisplayable());
        tiles.get(new Coordinate(-6, 6)).addEI(killEffect);
        killEffect = new OneShotAreaEffect(new KillCommand(), false, "Kill Area Effect");
        spriteMap.put(killEffect, ImageMaker.makeSkullDisplayable());
        tiles.get(new Coordinate(-7, 6)).addEI(killEffect);

        AreaEffect levelUp = new OneShotAreaEffect(new LevelUpCommand(), false, "Level Up Area Effect");
        spriteMap.put(levelUp, ImageMaker.makeArrowDisplayable());
        tiles.get(new Coordinate(-6, 8)).addEI(levelUp);

        levelUp = new OneShotAreaEffect(new LevelUpCommand(), false, "Level Up Area Effect");
        spriteMap.put(levelUp, ImageMaker.makeArrowDisplayable());
        tiles.get(new Coordinate(-7, 8)).addEI(levelUp);

        levelUp = new OneShotAreaEffect(new LevelUpCommand(), false, "Level Up Area Effect");
        spriteMap.put(levelUp, ImageMaker.makeArrowDisplayable());
        tiles.get(new Coordinate(-6, 9)).addEI(levelUp);

        levelUp = new OneShotAreaEffect(new LevelUpCommand(), false, "Level Up Area Effect");
        spriteMap.put(levelUp, ImageMaker.makeArrowDisplayable());
        tiles.get(new Coordinate(-7, 9)).addEI(levelUp);

        for(int i = -5; i <= 1; ++i) {
            River river = new River(new Vector(Direction.S, 29));
            tiles.get(new Coordinate(7, i)).addTM(river);
            spriteMap.put(river, ImageMaker.makeRiverDisplayable(Direction.S));
        }
        River river = new River(new Vector(Direction.SW, 29));
        tiles.get(new Coordinate(7, 2)).addTM(river);
        spriteMap.put(river, ImageMaker.makeRiverDisplayable(Direction.SW));
        for(int i = 3; i >= -3; --i) {
            river = new River(new Vector(Direction.N, 29));
            tiles.get(new Coordinate(6, i)).addTM(river);
            spriteMap.put(river, ImageMaker.makeRiverDisplayable(Direction.N));
        }
        river = new River(new Vector(Direction.NE, 29));
        tiles.get(new Coordinate(6, -4)).addTM(river);
        spriteMap.put(river, ImageMaker.makeRiverDisplayable(Direction.NE));

        Vehicle thingy = createVehicle(new Coordinate(2, 2));
        tiles.get(new Coordinate(-4, -4)).setEntity(thingy);
        spriteMap.put(thingy, ImageMaker.makeVehicleDisplayable());

        BuffHealthCommand command = new BuffHealthCommand(100);
        System.out.println("here2 " + command);
        WearableItem armor = new WearableItem("Weak Armor", true, command, EquipSlot.ARMOUR);
        world.getTile(new Coordinate(-1, 9)).addEI(armor);
        spriteMap.put(armor, ImageMaker.makeArmorDisplayable());

        armor = new WearableItem("Strong Armor", true, new BuffHealthCommand(1000), EquipSlot.ARMOUR);
        world.getTile(new Coordinate(0, 9)).addEI(armor);
        spriteMap.put(armor, ImageMaker.makeArmorDisplayable());


        WearableItem ring = new WearableItem("Stamina Regen Ring", true, new TimedStaminaRegenCommand(false, 0, 2), EquipSlot.RING);
        world.getTile(new Coordinate(1, 8)).addEI(ring);
        spriteMap.put(ring, ImageMaker.makeRingDisplayable());

        return world;
    }

    public Game getGame(){
        return game;
    }

    private Vehicle createVehicle (Coordinate loc) {
        Vehicle thingy = new Vehicle(new Vector(), new EntityStats(), new ArrayList<>(), new ArrayList<>(), new Inventory(), true, null);
        thingy.increaseBaseMoveSpeed(1000);
        Inventory i = new Inventory();
        Equipment e = new Equipment(5, i , thingy);
        EntityController vehicleController = new NpcEntityController(thingy, e, loc, null, null, false);
        thingy.setController(vehicleController);
        thingy.addCompatibleTerrain(Terrain.WATER);
        return thingy;
    }
}