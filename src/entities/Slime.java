package entities;

import enitystates.Attack;
import enitystates.EntityState;
import enitystates.Idle;
import enitystates.Walk;
import gamestates.Playing;

import java.awt.*;
import java.util.Random;

import static utils.Constants.Screen.TILE_SIZE;

public class Slime extends Sprite {
    EntityState currentState;
    Attack attack;
    Idle idle;
    Walk walk;

    public Slime(Playing playing, int worldX, int worldY) {
        super("Slime", "Monster/Slime/Idle/Down/1", playing, TILE_SIZE, TILE_SIZE * 2, worldX, worldY);
        currentState = EntityState.IDLE;
        attack = new Attack(this, 6, 10);
        idle = new Idle(this, 5, 10);
        walk = new Walk(this, 5, 10);
        this.worldX = worldX;
        this.worldY = worldY;
        speed = 2;

        solidArea = new Rectangle(0, TILE_SIZE/2, TILE_SIZE, TILE_SIZE/2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void update() {
        switch (currentState) {
            case IDLE:
                idle.update(this);
                image = idle.getImage();
                break;
            case WALK:
                walk.update(this);
                image = walk.getImage();
                break;
            case ATTACK:
                System.out.println(currentState);
                attack.update(this);
                image = attack.getImage();
                break;
        }

    }

    int frameCounter = 0;
    int totalFrames = 60;

    public void stateChanger() {
        frameCounter++;
        if (frameCounter < totalFrames) return;
        frameCounter = 0;
        Random random = new Random();
        int randomState = random.nextInt(3); // Generates a random number between 0 (inclusive) and 3 (exclusive)

        switch (randomState) {
            case 0:
                currentState = EntityState.IDLE;
                break;
            case 1:
                currentState = EntityState.WALK;
                break;
            case 2:
                currentState = EntityState.ATTACK;
                break;

        }

        int randomDirection = random.nextInt(4);
        switch (randomDirection) {
            case 0:
                direction = "down";
                break;
            case 1:
                direction = "left";
                break;
            case 2:
                direction = "right";
                break;
            case 3:
                direction = "up";
                break;
        }
    }


}
