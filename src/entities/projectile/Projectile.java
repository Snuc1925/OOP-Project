package entities.projectile;
//import
import static enitystates.EntityState.*;

import enitystates.EntityState;
import enitystates.EntityStateMethods;
import entities.Entity;
import entities.Player;
import entities.Sprite;
import entities.monsters.Monster;
import gamestates.Playing;
import utils.Constants;
import utils.HelpMethods;

import java.awt.*;
import java.util.ArrayList;
import enitystates.Attack;
import enitystates.Death;
import utils.ImageLoader;
import utils.ImageManager;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;

public class Projectile extends Sprite {
    public int attackPoints;
    private Attack attack;
    private Death death;

    public Projectile(Playing playing, String name, Sprite entity) {
        super(name, playing, Constants.Screen.TILE_SIZE, Constants.Screen.TILE_SIZE);
        this.worldX = entity.worldX;
        this.worldY = entity.worldY;
        this.direction = entity.direction;
        this.currentState = ATTACK;

        if (entity instanceof Monster) {
            this.speed = Constants.Monster.Projectile.SPEED;
            this.attackPoints = Constants.Monster.Projectile.ATTACK_POINTS;
            this.attack = new Attack(this, Constants.Monster.Projectile.TOTAL_FRAME, Constants.Monster.Projectile.FRAME_DURATION);
            this.death = new Death(this, Constants.Monster.Projectile.EXPLOSION_TOTAL_FRAME, Constants.Monster.Projectile.EXPLOSION_FRAME_DURATION);
        }
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
        if (currentState == ATTACK) {
            move();
            System.out.println("UPDATEEEEEEEEEEE");
            this.image = attack.getImage();
        } else {
            this.image = death.getImage();
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
