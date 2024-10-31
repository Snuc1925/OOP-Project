package entities;

import enitystates.Attack;
import enitystates.EntityState;
import enitystates.Idle;
import enitystates.Walk;
import gamestates.Playing;
import utils.ImageLoader;

import java.awt.*;
import java.util.Random;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;
import static utils.Constants.Screen.TILE_SIZE;

public class Monster extends Sprite{
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

    int effectCounter = 0;
    int numEffectFrame = 0;
    public void draw(Graphics2D g2) {
        super.draw(g2);

        // Draw auto lockOn effect
        if (isBeingLockOn) {
            effectCounter++;
            Player player = playing.getPlayer();
            int playerWorldX = player.worldX;
            int playerWorldY = player.worldY;

            int effectWidth = TILE_SIZE * 5 / 2;
            int effectHeight = TILE_SIZE * 5 / 2;

            int screenX = getWorldX() - (playerWorldX + TILE_SIZE) + (PLAYER_SCREEN_X + TILE_SIZE) - effectWidth / 2;
            int screenY = getWorldY() - (playerWorldY + TILE_SIZE) + (PLAYER_SCREEN_Y + TILE_SIZE) - effectHeight / 2;

            if (effectCounter > 2) {
                numEffectFrame = (numEffectFrame + 1) % 8;
                effectCounter = 0;
            }
            g2.drawImage(ImageLoader.imageManager.getEffectImage("LockOn", numEffectFrame), screenX, screenY, effectWidth, effectHeight, null);
        }
    }


    private int frameCounter = 0;
    // This method needs to be called 60 times before it starts changing state
    public void stateChanger() {
        frameCounter++;
        int totalFrames = 60;
        if (frameCounter < totalFrames) return;
        frameCounter = 0;
        Random random = new Random();
        int randomState = random.nextInt(2); // Generates a random number between 0 (inclusive) and 3 (exclusive)
        switch (randomState) {
            case 0:
                currentState = EntityState.IDLE;
                break;
            case 1:
                currentState = EntityState.WALK;
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
