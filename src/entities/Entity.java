package entities;


import enitystates.EntityState;
import gamestates.Playing;
import utils.Constants;
import utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;

import static enitystates.EntityState.IDLE;
import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;
import static utils.Constants.Screen.TILE_SIZE;

public class Entity {
    public String name;
    public BufferedImage image;

    public int width, height;
    protected String image_path;
    protected boolean collision;
    public boolean collisionOn = false;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int worldX, worldY;
    protected Playing playing;
    public EntityState currentState = IDLE;


    public Playing getPlaying() {return playing;}

    // For wall and super objects
    public Entity(String name, String image_path, Playing Playing) {
        this.name = name;
        this.playing = Playing;
        this.image_path = image_path;

        width = Constants.Screen.TILE_SIZE;
        height = Constants.Screen.TILE_SIZE;
        image = HelpMethods.setUp(image_path, width, height);
        solidArea = new Rectangle(0, 0, Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    // For player, npc and monster
    public Entity(String name, String image_path, Playing playing, int width, int height) {
        this.playing = playing;
        this.name = name;
        this.image_path = image_path;
        this.width = width;
        this.height = height;

        solidArea = new Rectangle(0, 0, Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        image = HelpMethods.setUp(image_path, width, height);
    }

    public void setSolidArea(int x, int y, int width, int height) {
        solidArea = new Rectangle(x, y, width, height);
    }
    public void setImageSize(int width, int height) {
        this.width = width;
        this.height = height;
        image = HelpMethods.setUp(image_path, width, height);
    }

    public void draw(Graphics2D g2) {
        if (isOnTheScreen()) {
            g2.drawImage(image, getScreenX(), getScreenY(), width, height, null);
            // Draw solid area for debugging purposes
//            g2.setColor(Color.WHITE);
//            g2.setStroke(new BasicStroke(3));
//            g2.drawRect(getScreenX() + solidArea.x, getScreenY() + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void update(){

    }

    public int getWorldY() {
        if (this.currentState == EntityState.DEATH) return -10000;
        if (name.equals("Player")) return worldY + TILE_SIZE * 2;
        if (name.equals("Slime")) return worldY + TILE_SIZE * 3 / 2;
        return worldY + height / 2;
    }
    public int getWorldX() {
        if (name.equals("Player")) return worldX + TILE_SIZE * 3 / 2;
        if (name.equals("Slime")) return worldX + TILE_SIZE/2;
        return worldX + width / 2;
    }

    public int getScreenX() {
        return worldX - (playing.getPlayer().worldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE);
    }
    public int getScreenY() {
        return worldY - (playing.getPlayer().worldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE);
    }
    public boolean isOnTheScreen() {
        Player player = playing.getPlayer();
        int playerWorldX = player.worldX;
        int playerWorldY = player.worldY;
        return worldX > (playerWorldX + TILE_SIZE) - (PLAYER_SCREEN_X + TILE_SIZE) - TILE_SIZE
                && worldX < (playerWorldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE) + TILE_SIZE
                && worldY > (playerWorldY + TILE_SIZE) - (PLAYER_SCREEN_Y + TILE_SIZE) - TILE_SIZE
                && worldY < (playerWorldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE) + TILE_SIZE;
    }
}
