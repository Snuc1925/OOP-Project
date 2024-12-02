package gamestates;

import data.SaveLoad;
import data.SaveLoadSystem;
import effect.CameraShake;
import effect.EnergyOrb;
import effect.NextLevel;
import enitystates.EntityState;
import entities.*;
import entities.monsters.*;
import entities.monsters.bosses.*;
import entities.projectile.ProjectileManager;
import inputs.KeyboardInputs;
import main.Game;
import java.awt.*;
import entities.npc.Npc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import map.GameMap;
import map.MapManager;
import map.MapParser;
import system.*;
import tile.TileManager;
import utils.ImageLoader;
import utils.ImageManager;
import main.Sound;

import system.MonsterAttackSystem;

public class Playing extends State implements Statemethods {
    private Player player;
    private TileManager tileManager;

    // Array of monsters
    public Monster[] monsters;

    // List and array of entities
    public ArrayList<Sprite> entityList;
    public Entity[] entityArray;

    // Camera shake
    public CameraShake cameraShake;

    private ProjectileManager projectileManager;
    private CollectibleSystem collectibleSystem;
    public DoorSystem doorSystem;
    private RenderSystem renderSystem;
    public MonsterAreaSystem monsterAreaSystem;

    private SaveLoadSystem saveLoadSystem;

    private final ImageManager imageManager;

    // Game map
    public static GameMap currentMap;

    // Npc
    public Npc npcTalking = null;
    public Npc[] npcArray;

    // Save load
    // Level
    public String currentLevel = "level2";
    public EnergyOrb energyOrb = null;
    public NextLevel nextLevel = null;



    public Playing(Game game) {
        super(game);

        cameraShake = new CameraShake(20);

        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;
        setDefaultValues();

        soundtrack = new Sound();
        setLevelTheme();

        saveLoadSystem = new SaveLoadSystem(this);
        saveLoadSystem.loadGame("level2");
//        saveLoadSystem.saveGame();
    }

    public void setDefaultValues() {
        player = new Player(this);
        tileManager = new TileManager(player);
        doorSystem = new DoorSystem();
        monsterAreaSystem = new MonsterAreaSystem();
        renderSystem = new RenderSystem(this);

        loadMap();
        monsters = new Monster[3];
//        monsters[0] = new Shadow(this, 1125 - 4 * TILE_SIZE, 377 + 13 * TILE_SIZE);
        npcArray = new Npc[0];
        setUpList();
    }

    public void loadMap() {
        MapParser.loadMap( currentLevel ,"res/map/map_" + currentLevel + ".tmx");
        currentMap = MapManager.getGameMap(currentLevel);
        currentMap.buildTileManager(tileManager);
    }

    public void setUpList() {
        entityList = new ArrayList<>();
        entityList.add(player);
        entityList.addAll(Arrays.asList(monsters));
        entityList.addAll(Arrays.asList(npcArray));
        entityArray = entityList.toArray(new Entity[0]);
    }

    public Game getGame() {
        return game;
    }
    public Player getPlayer() {
        return player;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    public RenderSystem getRenderSystem() { return renderSystem; }

    public ProjectileManager getProjectileManager() { return projectileManager; }

    public DoorSystem getDoorSystem() { return doorSystem; }

    public MonsterAreaSystem getMonsterAreaSystem() { return monsterAreaSystem; }

    public SaveLoadSystem getSaveLoadSystem() { return saveLoadSystem; }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void update() {
        for (int i = 0; i < entityArray.length; i++)
            if (entityArray[i] != null){
                if (entityArray[i].image == null && entityArray[i].currentState == EntityState.DEATH) {
                    entityArray[i] = null;
                }
            }
        if (!currentLevel.equals("level4") && nextLevel == null) {
            boolean allMonstersNull = true;
            for (Monster monster : monsters) {
                if (monster == null || (monster.image == null && monster.currentState == EntityState.DEATH)) continue;

                if (monster.currentState != EntityState.DEATH ||
                        (monster instanceof Demon && ((Demon) monster).currentPhase == 1) ||
                        (monster instanceof Samurai && ((Samurai) monster).currentPhase == 1)) {
                    allMonstersNull = false;
                    break;
                }
            }
            if (allMonstersNull) {
                nextLevel = new NextLevel(this, player.getWorldX(), player.getWorldY());
            }
            // For debugging
            nextLevel = new NextLevel(this, player.getWorldX(), player.getWorldY());
        }

        // NPC talk, other entity stop update
        if (npcTalking != null) {
            npcTalking.update();
            return;
        }

        cameraShake.update();
        for (Entity entity : entityArray) {
            if (entity != null && entity.isOnTheScreen()){
                entity.update();
            }
        }
        if (player.currentState != EntityState.DEATH)
            player.lockOn();

//        projectileManager.update();
//        collectibleSystem.update();
        if (monsterAreaSystem != null) monsterAreaSystem.update();
        if (doorSystem != null) doorSystem.update();
//          System.out.println(player.getWorldX()/TILE_SIZE + " " + player.getWorldY()/TILE_SIZE);
//        System.out.println(player.worldX + " " + player.worldY);
        if (KeyboardInputs.isPressedValid("pause", game.getKeyboardInputs().pausePressed)) {
            Gamestate.state = Gamestate.PAUSE;
        }

        if (player.currentState == EntityState.DEATH) {
            Gamestate.state = Gamestate.GAME_OVER;
        }

        if (energyOrb != null) energyOrb.update();
        if (nextLevel != null) {
//            System.out.println("Asdasdasdasdasdasdasdasd");
            nextLevel.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        currentMap.render(g2, player);

//        collectibleSystem.draw(g2);
        if (doorSystem != null) doorSystem.draw(g2);

        for (Entity entity : entityList) {
            if (entity instanceof SkeletonReaper && entity.image == null && entity.currentState == EntityState.DEATH) {
                energyOrb = new EnergyOrb(this, entity.getWorldX(), entity.getWorldY());
            }
        }

        entityList.removeIf(Objects::isNull);
        entityList.removeIf(entity -> entity.image == null && entity.currentState == EntityState.DEATH);
        entityList.stream()
                .sorted(Comparator.comparingDouble(Entity::getRenderOrder))
                .forEach(entity -> {
                    if (entity.isOnTheScreen() && entity.image != null) {
                        entity.draw(g2);
                        // currentMap.render2(g2, entity, player);
                    }
                });


        game.getUI().drawPlayerUI(g2);

        if (npcTalking != null) game.getUI().drawDialogueScreen(npcTalking.talk(), g2);


        for (Monster monster : monsters) {
            if (monster instanceof Demon || monster instanceof BringerOfDeath || monster instanceof Samurai ||
                monster instanceof SkeletonReaper) {
                Boss boss = (Boss) monster;
                boss.drawBossIntro(g2);
            }
            if (monster instanceof  SkeletonReaper) {
                ((SkeletonReaper) monster).drawDialogue(g2);
            }
        }

        if (energyOrb != null) energyOrb.draw(g2);
        if (nextLevel != null) nextLevel.draw(g2);
    }

    // Sound
    public Sound soundtrack;
    public void setLevelTheme() {
        switch (currentLevel) {
            case "level1":
                soundtrack.playMusic(4);
                break;
            case "level2":
                soundtrack.playMusic(5);
                break;
            case "level3":
                soundtrack.playMusic(6);
                break;
            default:
                soundtrack.playMusic(7);
                break;
        }
    }
}
