package gamestates;

import data.SaveLoad;
import effect.CameraShake;
import enitystates.EntityState;
import entities.*;
import entities.monsters.*;
import entities.monsters.bosses.*;
import entities.projectile.ProjectileManager;
import inputs.KeyboardInputs;
import main.Game;
import java.awt.*;
import entities.npc.Npc;
import entities.npc.WhiteSamurai;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import map.GameMap;
import map.MapManager;
import map.MapParser;
import system.CollectibleSystem;
import system.DoorSystem;
import system.RenderSystem;
import tile.TileManager;
import utils.ImageLoader;
import utils.ImageManager;
import main.Sound;

import system.MonsterAttackSystem;

import static utils.Constants.Screen.TILE_SIZE;

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
    private DoorSystem doorSystem;
    private MonsterAttackSystem monsterAttackSystem;
    private RenderSystem renderSystem;

    private final ImageManager imageManager;

    // Game map
    public static GameMap currentMap;

    // Npc
    public Npc npcTalking = null;
    public Npc[] npcArray;

    // Save load
    SaveLoad saveLoad = new SaveLoad(this);

    // Level
    public String currentLevel = "level4";

    public Sound soundtrack;

    public Playing(Game game) {
        super(game);

//        projectileManager = new ProjectileManager(this);
//        collectibleSystem = new CollectibleSystem(this);
//        doorSystem = new DoorSystem(this);
//        renderSystem = new RenderSystem(this);

        cameraShake = new CameraShake(20);
//        monsterAttackSystem = new MonsterAttackSystem(this);

        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;
        setDefaultValues();

        soundtrack = new Sound();
        soundtrack.setTheme(0);
        soundtrack.play();
        soundtrack.loop();
        soundtrack.setVolume(0.15f);
    }

    public void setDefaultValues() {
        player = new Player(this);
        tileManager = new TileManager(player);

        loadMap();

        monsters = new Monster[6];
        monsters[0] = new SkeletonReaper(this, 23 *  TILE_SIZE, 6 * TILE_SIZE);
        npcArray = new Npc[1];
        npcArray[0] = new WhiteSamurai(this, 13 * TILE_SIZE, 5 * TILE_SIZE);

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

//        monsterAttackSystem.update();
//        projectileManager.update();
//        collectibleSystem.update();
//        doorSystem.update();
          System.out.println(player.getWorldX()/TILE_SIZE + " " + player.getWorldY()/TILE_SIZE);

        if (KeyboardInputs.isPressedValid("pause", game.getKeyboardInputs().pausePressed)) {
            Gamestate.state = Gamestate.PAUSE;
        }

        if (player.currentState == EntityState.DEATH) {
            Gamestate.state = Gamestate.GAME_OVER;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        currentMap.render(g2, player);

//        projectileManager.draw(g2);
//        collectibleSystem.draw(g2);
//        doorSystem.draw(g2);

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
    }


}
