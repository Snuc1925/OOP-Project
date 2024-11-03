package entities;

import enitystates.*;
import gamestates.Playing;

import java.awt.*;
import java.util.Random;

import static utils.Constants.Screen.TILE_SIZE;

public class Slime extends Monster {

    public Slime(Playing playing, int worldX, int worldY) {
        super("Slime", "Monster/Slime/Idle/Down/1", playing, TILE_SIZE, TILE_SIZE * 2);
        currentState = EntityState.WALK;
        attack = new Attack(this, 6, 10);
        idle = new Idle(this, 5, 10);
        walk = new Walk(this, 5, 10);
        death = new Death(this, 5, 10);
        this.worldX = worldX;
        this.worldY = worldY;
        speed = 2;
        attackPoints = 1;
        currentHealth = 10;
        maxHealth = 10;

        solidArea = new Rectangle(0, TILE_SIZE*3/2, TILE_SIZE, TILE_SIZE/2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    int frameCounter = 0;
    public void attack() {
        getDirectionForAttacking();
        speed = 4;
        move();
        frameCounter++;
        Player player = playing.getPlayer();
        if (frameCounter > attack.totalAnimationFrames * attack.frameDuration) {
            if (playing.getGame().getCollisionChecker().checkPlayer(this)) {
                player.currentHealth -= attackPoints;
                if (player.currentHealth <= 0) {
                    player.currentState = EntityState.DEATH;
                }
            }
            currentState = EntityState.IDLE;
            frameCounter = 0;
        }
        speed = 2;
    }

    public void getDirectionForAttacking() {
        int dx = playing.getPlayer().getWorldX() - worldX;
        int dy = playing.getPlayer().getWorldY() - worldY;
        double angle = (Math.atan2(dy, dx) * 180 / Math.PI);
        if (angle >= -22 && angle < 22) direction = "right";
        else if (angle >= 22 && angle < 67) direction = "right_down";
        else if (angle >= 67 && angle < 112) direction = "down";
        else if (angle >= 112 && angle < 157) direction = "left_down";
        else if (angle >= 157 || angle < -157) direction = "left";
        else if (angle >= -157 && angle < -112) direction = "left_up";
        else if (angle >= -112 && angle < -67) direction = "up";
        else direction = "right_up";
    }

}
