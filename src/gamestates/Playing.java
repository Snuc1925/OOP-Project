package gamestates;

import effect.CameraShake;
import enitystates.EntityState;
import entities.*;
import entities.monsters.*;
import entities.projectile.ProjectileManager;
import main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import entities.npc.Npc;
import entities.npc.WhiteSamurai;
import main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import main.UI;
import map.GameMap;
import map.MapManager;
import map.MapParser;
import system.CollectibleSystem;
import system.RenderSystem;
import tile.TileManager;
import utils.HelpMethods;
import utils.ImageLoader;
import utils.ImageManager;

import system.MonsterAttackSystem;

import static utils.Constants.Screen.SCREEN_X;
import static utils.Constants.Screen.TILE_SIZE;

public class Playing extends State implements Statemethods {
    private final Player player;
    private final TileManager tileManager;

    // Array of monsters
    public Monster[] monsters;

    // List and array of entities
    public ArrayList<Sprite> entityList;
    public Entity[] entityArray;

    private ProjectileManager projectileManager;
    public CameraShake cameraShake;

    // Game map
    private CollectibleSystem collectibleSystem;

    private MonsterAttackSystem monsterAttackSystem;

    private RenderSystem renderSystem;

    private ImageManager imageManager;
    public static GameMap currentMap;

    // Npc
    public Npc npcTalking = null;

    // UI
    public UI ui;

    // Npc
    public Npc[] npcArray;

    public Playing(Game game) {
        super(game);
        player = new Player(this);
        tileManager = new TileManager(player);
        projectileManager = new ProjectileManager(this);
        collectibleSystem = new CollectibleSystem(this);
        renderSystem = new RenderSystem(this);

        monsters = new Monster[1];
        monsters[0] = new PlantMelee(this, 8 * TILE_SIZE, 12 * TILE_SIZE);
        npcArray = new Npc[1];
        npcArray[0] = new WhiteSamurai(this, 13 * TILE_SIZE, 5 * TILE_SIZE);

        entityList = new ArrayList<>();
        entityList.add(player);
        entityList.addAll(Arrays.asList(monsters));
        entityList.addAll(Arrays.asList(npcArray));
        entityArray = entityList.toArray(new Entity[0]);

        cameraShake = new CameraShake(20);
        monsterAttackSystem = new MonsterAttackSystem(this);

        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;

        MapParser.loadMap( "level1" ,"res/map/map_level1.tmx");
        currentMap = MapManager.getGameMap("level1");
        currentMap.buildTileManager(tileManager);

        ui = new UI(this);
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
        // System.out.println(player.getWorldX()/TILE_SIZE + " " + player.getWorldY()/TILE_SIZE);
    }

    @Override
    public void draw(Graphics2D g2) {

        currentMap.render(g2, player);
        entityList.sort(Comparator.comparingDouble(Entity::getRenderOrder));
        for (Sprite entity : entityList) if (entity.isOnTheScreen()){
            entity.draw(g2);
            currentMap.render2(g2, entity, player);
        }
        projectileManager.draw(g2);

        ui.drawPlayerUI(g2);

        if (npcTalking != null) {
            ui.drawDialogueScreen(npcTalking.talk(), g2);
        }
    }


}
