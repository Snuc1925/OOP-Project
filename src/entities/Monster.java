package entities;

import enitystates.*;
import entities.projectile.Projectile;
import entities.projectile.ProjectileManager;
import gamestates.Playing;
import utils.ImageLoader;
import utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;
import static utils.Constants.Screen.TILE_SIZE;

public class Monster extends Sprite{
    protected Attack attack;
    protected Idle idle;
    protected Walk walk;
    protected Death death;
    public boolean isBeingLockOn = false;
    public int currentHealth, maxHealth, attackPoints;
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
            case DEATH:
                death.update(this);
                image = death.getImage();
                break;
        }
    }

    int effectCounter = 0;
    int numEffectFrame = 0;
    public void draw(Graphics2D g2) {
        super.draw(g2);
        // Draw health bar
        int healthBarWidth = (int) (15 * 3 * ((double) currentHealth / maxHealth));
        if (healthBarWidth > 0) {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX(), getScreenY() + 5 * 3, healthBarWidth, 4 * 3);
        }

        // Draw auto lockOn effect
        if (isBeingLockOn && currentState != EntityState.DEATH) {
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

    void attackLongRange() {
        String projectileDirection = getDirectionForAttacking(playing.getPlayer());
//        BufferedImage projectileImage = ImageManager.getInstance().getProjectileImage("MONSTER_SLIME", projectileDirection);
        int speed = 4;
        int attackPoints = 2;
        String image_path = "projectile/monster/slime" + projectileDirection;
        Projectile projectile = new Projectile(playing, image_path, worldX, worldY, projectileDirection, speed, attackPoints);
        playing.getProjectileManager().addProjectile(projectile);
    }
}
