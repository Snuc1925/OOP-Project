package entities;

import enitystates.Attack;
import enitystates.EntityState;
import enitystates.Idle;
import enitystates.Walk;
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
        this.worldX = worldX;
        this.worldY = worldY;
        speed = 2;

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
        if (frameCounter > attack.totalAnimationFrames * attack.frameDuration) {
            if (playing.getGame().getCollisionChecker().checkPlayer(this)) {
                playing.getPlayer().currentHealth -= 1;
            }
            currentState = EntityState.IDLE;
            frameCounter = 0;
        }
        speed = 2;
    }

    public void getDirectionForAttacking() {
        int dx = playing.getPlayer().getWorldX() - worldX;
        int dy = playing.getPlayer().getWorldY() - worldY;
        int angle = (int) (Math.atan2(dy, dx) * 180 / Math.PI);
        if (angle <= 45 && angle >= -45) direction = "right";
        else if (angle > 45 && angle < 135) direction = "down";
        else if (angle >= 135 || angle <= -135) direction = "left";
        else direction = "up";
    }

}
