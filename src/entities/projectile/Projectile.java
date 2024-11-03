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
    public Projectile(Playing playing, String image_path, int worldX, int worldY, String direction, int speed) {
        super("PROJECTILE", image_path, playing, Constants.Projectile.WIDTH, Constants.Projectile.HEIGHT);
        this.playing = playing;
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.speed = speed;
        image = HelpMethods.setUp(image_path, Constants.Projectile.WIDTH, Constants.Projectile.HEIGHT);
        solidArea = new Rectangle(0, 0, Constants.Projectile.WIDTH, Constants.Projectile.HEIGHT);
    }

    public void update() {
        move();
        if (collisionOn) {
            currentState = DEATH;
        }
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
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

}
