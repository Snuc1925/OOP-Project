package entities.projectile;
//import
import static enitystates.EntityState.*;
import entities.Player;
import entities.Sprite;
import gamestates.Playing;
import utils.Constants;
import utils.HelpMethods;

import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;

public class Projectile extends Sprite {
    public int attackPoints;
    public int totalAnimationFrame;
    public int numAnimationFrame;

    public Projectile(Playing playing,
                      String image_path,
                      int worldX, int worldY,
                      String direction, int speed,
                      int attackPoints,
                      int totalAnimationFrame) {
        super("PROJECTILE", image_path, playing, Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE);
        this.playing = playing;
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.speed = speed;
        this.attackPoints = attackPoints;
        this.numAnimationFrame = 0;
        this.totalAnimationFrame = totalAnimationFrame;
    }

    public void move() {
        if (direction.equals("down")) {
            worldY += speed;
        }
        if (direction.equals("up")) {
            worldY -= speed;
        }
        if (direction.equals("left")) {
            worldX -= speed;
        }
        if (direction.equals("right")) {
            worldX += speed;
        }
        if (direction.equals("right_down")) {
            worldX += speed - 1;
            worldY += speed - 1;
        }
        if (direction.equals("left_up")) {
            worldX -= speed - 1;
            worldY -= speed - 1;
        }
        if (direction.equals("left_down")) {
            worldX -= speed - 1;
            worldY += speed - 1;
        }
        if (direction.equals("right_up")) {
            worldX += speed - 1;
            worldY -= speed - 1;
        }
    }

    public void update() {
        move();
    }

    public void draw(Graphics2D g2) {
        Player player = playing.getPlayer();
        int playerWorldX = player.worldX;
        int playerWorldY = player.worldY;
        int screenX = worldX - playerWorldX + PLAYER_SCREEN_X;
        int screenY = worldY - playerWorldY + PLAYER_SCREEN_Y;


        if (isOnTheScreen()) {

            g2.drawImage(image, screenX, screenY, width, height, null);
            // Draw solid area for debugging purposes
//            g2.setColor(Color.WHITE);
//            g2.setStroke(new BasicStroke(3));
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

}
