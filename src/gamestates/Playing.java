package gamestates;

import data.SaveLoad;
import effect.CameraShake;
import enitystates.EntityState;
import entities.*;
import entities.monsters.*;
import entities.projectile.ProjectileManager;
import inputs.KeyboardInputs;
import main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import entities.npc.Npc;
import entities.npc.WhiteSamurai;
import main.Game;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import map.GameMap;
import map.MapManager;
import map.MapParser;
import system.CollectibleSystem;
import system.DoorSystem;
import system.RenderSystem;
import tile.TileManager;
import utils.ImageLoader;
import utils.ImageManager;

import system.MonsterAttackSystem;

import static utils.Constants.Screen.SCREEN_X;
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

    // Game map
    private final ImageManager imageManager;

    // Game map
    public static GameMap currentMap;

    // Npc
    public Npc npcTalking = null;

    // Npc
    public Npc[] npcArray;

    // Save load
    SaveLoad saveLoad = new SaveLoad(this);

    // Level
    public String currentLevel = "level1";

    public Playing(Game game) {
        super(game);

        projectileManager = new ProjectileManager(this);
        collectibleSystem = new CollectibleSystem(this);
        doorSystem = new DoorSystem(this);
        renderSystem = new RenderSystem(this);

        cameraShake = new CameraShake(20);
        monsterAttackSystem = new MonsterAttackSystem(this);

        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;

        setDefaultValues();

    }

    public void setDefaultValues() {
        player = new Player(this);
        tileManager = new TileManager(player);

        MapParser.loadMap( "level1" ,"res/map/map_" + currentLevel + ".tmx");
        currentMap = MapManager.getGameMap("level1");
        currentMap.buildTileManager(tileManager);

        monsters = new Monster[1];
        monsters[0] = new SwordKnight(this, 8 * TILE_SIZE, 12 * TILE_SIZE);
        npcArray = new Npc[1];
        npcArray[0] = new WhiteSamurai(this, 13 * TILE_SIZE, 5 * TILE_SIZE);

        setUpList();
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

        monsterAttackSystem.update();
        projectileManager.update();
        collectibleSystem.update();
        doorSystem.update();
        // System.out.println(player.getWorldX()/TILE_SIZE + " " + player.getWorldY()/TILE_SIZE);

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
        entityList.sort(Comparator.comparingDouble(Entity::getRenderOrder));
        for (Sprite entity : entityList) if (entity.isOnTheScreen()){
            entity.draw(g2);
//            currentMap.render2(g2, entity, player);
        }
        projectileManager.draw(g2);
        collectibleSystem.draw(g2);
        doorSystem.draw(g2);

        game.getUI().drawPlayerUI(g2);

        if (npcTalking != null) {
            game.getUI().drawDialogueScreen(npcTalking.talk(), g2);
        }
    }


}
