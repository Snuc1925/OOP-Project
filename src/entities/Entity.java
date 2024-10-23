package entities;


import gamestates.Playing;
import utils.Constants;
import utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public String name;
    public BufferedImage image;

    public int width, height;
    protected String image_path;
    protected boolean collision;
    protected boolean collisionOn = false;
    protected Rectangle solidArea;
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected int worldX, worldY;
    protected Playing playing;

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
        Player player = playing.getPlayer();
        int screenX = worldX - player.worldX + Constants.Player.PLAYER_SCREEN_X;
        int screenY = worldY - player.worldY + Constants.Player.PLAYER_SCREEN_Y;

        // Check if entity is inside the screen to decide drawing or not
        if (worldX > player.worldX - Constants.Player.PLAYER_SCREEN_X - Constants.Screen.TILE_SIZE
                && worldX < player.worldX + Constants.Player.PLAYER_SCREEN_X + Constants.Screen.TILE_SIZE
                && worldY > player.worldY - Constants.Player.PLAYER_SCREEN_Y - Constants.Screen.TILE_SIZE
                && worldY < player.worldY + Constants.Player.PLAYER_SCREEN_Y + Constants.Screen.TILE_SIZE) {

            g2.drawImage(image, screenX, screenY, Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE, null);

            // Draw solid area for debugging purposes
//            g2.setColor(Color.WHITE);
//            g2.setStroke(new BasicStroke(3));
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public void update(){

    }
}
