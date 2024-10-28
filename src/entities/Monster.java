package entities;

import enitystates.Attack;
import enitystates.EntityState;
import enitystates.Idle;
import enitystates.Walk;
import gamestates.Playing;

import java.awt.*;
import java.util.Random;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;
import static utils.Constants.Screen.TILE_SIZE;

public class Monster extends Sprite{
    protected EntityState currentState;
    protected Attack attack;
    protected Idle idle;
    protected Walk walk;
    public boolean isBeingLockOn = false;
    public Monster(String name, String image_path, Playing playing, int width, int height) {
        super(name, image_path, playing, width, height);
        currentState = EntityState.IDLE;
    }

    public void update() {
        switch (currentState) {
            case IDLE:
                if (idle == null) return;
                idle.update(this);
                image = idle.getImage();
                break;
            case WALK:
                if (walk == null) return;
                walk.update(this);
                image = walk.getImage();
                break;
            case ATTACK:
                if (attack == null) return;
                attack.update(this);
                image = attack.getImage();
                break;
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (isBeingLockOn) {
            Player player = playing.getPlayer();
            int playerWorldX = player.worldX;
            int playerWorldY = player.worldY;
            int screenX = worldX - (playerWorldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE);
            int screenY = worldY - (playerWorldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }


    private int frameCounter = 0;
    public void stateChanger() {
        frameCounter++;
        int totalFrames = 60;
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
