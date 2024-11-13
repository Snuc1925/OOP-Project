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
import main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import map.GameMap;
import map.MapManager;
import map.MapParser;
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
    public ArrayList<Entity> entityList;
    public Entity[] entityArray;

    private ProjectileManager projectileManager;
    public CameraShake cameraShake;

    private MonsterAttackSystem monsterAttackSystem;
    private ImageManager imageManager;
    public static GameMap currentMap;

    public Playing(Game game) {
        super(game);
        player = new Player(this);
        tileManager = new TileManager(player);
        projectileManager = new ProjectileManager(this);

        monsters = new Monster[8];
        monsters[1] = new Slime(this,  9 * TILE_SIZE, 9 * TILE_SIZE);
        monsters[2] = new Slime(this, 11 * TILE_SIZE, 9 * TILE_SIZE);
        monsters[3] = new Slime(this, 7 * TILE_SIZE, 7 * TILE_SIZE);
        monsters[4] = new Slime(this, 10 * TILE_SIZE, 13 * TILE_SIZE);
        monsters[5] = new Slime(this, 13 * TILE_SIZE, 13 * TILE_SIZE);
        monsters[6] = new Morph(this, 41 * TILE_SIZE, 44 * TILE_SIZE);
        monsters[7] = new Samurai(this, 10 * TILE_SIZE, 41 * TILE_SIZE);
        monsters[0] = new BringerOfDeath(this, 41 * TILE_SIZE, 9 * TILE_SIZE);

        entityList = new ArrayList<>();
        entityList.add(player);
        entityList.addAll(Arrays.asList(monsters));

        entityArray = entityList.toArray(new Entity[0]);

        cameraShake = new CameraShake(20);
        monsterAttackSystem = new MonsterAttackSystem(this);

        ImageLoader.initialize();
        imageManager = ImageLoader.imageManager;

        MapParser.loadMap( "map_test" ,"res/map/map2d_after_fixed.tmx");
        currentMap = MapManager.getGameMap("map_test");
        currentMap.buildTileManager(tileManager);
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

    public ProjectileManager getProjectileManager() { return projectileManager; }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void update() {
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
    }

    @Override
    public void draw(Graphics2D g2) {

        currentMap.render(g2, player);


        entityList.sort(Comparator.comparingDouble(Entity::getRenderOrder));

        for (Entity entity : entityList) if (entity.isOnTheScreen()){
            entity.draw(g2);
        }
        projectileManager.draw(g2);


        // Draw player status GUI
        ImageLoader.initialize();
        ImageManager imageManager = ImageLoader.imageManager;
        BufferedImage ui_bar_decor = imageManager.getGuiImage("UI_BAR_DECORATION");
        ui_bar_decor = HelpMethods.scaleImage(ui_bar_decor, 0.25);
        g2.drawImage(ui_bar_decor, 0, 0, null);

        if (player.currentHealth > 0) {
            BufferedImage player_health_bar = imageManager.getGuiImage("HEALTH_BAR");
            player_health_bar = HelpMethods.scaleImage(player_health_bar, 0.25);
            player_health_bar = HelpMethods.getBarImage(player_health_bar, 1.0 * player.currentHealth / player.maxHealth);
            g2.drawImage(player_health_bar, 49, 10, null);
        }

        if (player.currentArmor > 0) {
            BufferedImage player_armor_bar = imageManager.getGuiImage("ARMOR_BAR");
            player_armor_bar = HelpMethods.scaleImage(player_armor_bar, 0.25);
            player_armor_bar = HelpMethods.getBarImage(player_armor_bar, 1.0 * player.currentArmor / player.maxArmor);
            g2.drawImage(player_armor_bar, 49, 43, null);
        }

        if (player.currentMana > 0) {
            BufferedImage player_mana_bar = imageManager.getGuiImage("MANA_BAR");
            player_mana_bar = HelpMethods.scaleImage(player_mana_bar, 0.25);
            player_mana_bar = HelpMethods.getBarImage(player_mana_bar, 1.0 * player.currentMana / player.maxMana);
            g2.drawImage(player_mana_bar, 49, 75, null);
        }

        Font pixelFont = HelpMethods.loadFont("PixelFont");
        String text = player.currentHealth + "/" + player.maxHealth;
        g2.setFont(pixelFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.WHITE);
        g2.drawString(text, 107, 27);

        text = player.currentArmor + "/" + player.maxArmor;
        g2.drawString(text, 107, 60);

        text = player.currentMana + "/" + player.maxMana;
        g2.drawString(text, 90, 93);

    }


}
