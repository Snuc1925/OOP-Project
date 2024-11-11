package entities.monsters;

import enitystates.*;
import entities.Player;
import entities.Sprite;
import entities.projectile.Projectile;
import gamestates.Playing;
import utils.HelpMethods;
import utils.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Player.PLAYER_SCREEN_X;
import static utils.Constants.Player.PLAYER_SCREEN_Y;
import static utils.Constants.Screen.TILE_SIZE;

public class Monster extends Sprite {
    protected Attack attack;
    protected Idle idle;
    protected Walk walk;
    protected Death death;
    public boolean isBeingLockOn = false;
    public int currentHealth, maxHealth, attackPoints;

    public Rectangle attackBox;
    public Rectangle visionBox;
    public Rectangle hitBox;



    public int attackRate = 180; // Total frames between 2 attack
    public Monster(String name, String image_path, Playing playing, int width, int height) {
        super(name, image_path, playing, width, height);
        currentState = EntityState.IDLE;
    }

    public void update() {

//        System.out.println(worldX + " " + worldY);
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

        // Draw hitBox
        if (playing.getGame().getKeyboardInputs().enterPressed) {
            g2.setColor(Color.GREEN);
            g2.drawRect(hitBox.x + getScreenX(), hitBox.y + getScreenY(), hitBox.width, hitBox.height);

            g2.setColor(Color.YELLOW);
            g2.drawRect(solidArea.x + getScreenX(), solidArea.y + getScreenY(), solidArea.width, solidArea.height);
            g2.drawRect(visionBox.x + getScreenX(), visionBox.y + getScreenY(), visionBox.width, visionBox.height);
            g2.drawRect(attackBox.x + getScreenX(), attackBox.y + getScreenY(), attackBox.width, attackBox.height);
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
    public void getDirectionForAttacking() {
        int dx = playing.getPlayer().getWorldX() - getWorldX();
        int dy = playing.getPlayer().getWorldY() - getWorldY();

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

    // Use needCooldown = true if checking player is in attack range at the last attack frame
    // Use needCooldown = false for changing other state to attack state
    int cooldown = 0;
    public boolean canAttack(boolean needCooldown) {
        boolean result;
        Player player = playing.getPlayer();
        if (player.currentState == EntityState.DEATH) return false;

        int currentAttackX = attackBox.x;
        int currentAttackY = attackBox.y;
        player.solidArea.x += player.worldX;
        player.solidArea.y += player.worldY;
        attackBox.x += worldX;
        attackBox.y += worldY;

        result = player.solidArea.intersects(attackBox) && HelpMethods.canSeeEntity(playing, this, player);

        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        attackBox.x = currentAttackX;
        attackBox.y = currentAttackY;
        if (!needCooldown) return result;

        if (result) {
            cooldown++;
            if (cooldown >= attackRate) {
                cooldown = 0;
                return true;
            }
        } else cooldown = 0;

        return false;
    }

    public boolean canSeePlayer() {
        Player player = playing.getPlayer();
        if (player.currentState == EntityState.DEATH) return false;
        int visionDefaultX = visionBox.x;
        int visionDefaultY = visionBox.y;

        player.solidArea.x += player.worldX;
        player.solidArea.y += player.worldY;
        visionBox.x += worldX;
        visionBox.y += worldY;

        boolean result = visionBox.intersects(player.solidArea);

        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        visionBox.x = visionDefaultX;
        visionBox.y = visionDefaultY;

        return result;
    }

    public void drawLockOn(Graphics2D g2, int effectWidth, int effectHeight, int xDiff, int yDiff) {
        // Draw auto lockOn effect
        if (isBeingLockOn && currentState != EntityState.DEATH) {
            effectCounter++;
            Player player = playing.getPlayer();
            int playerWorldX = player.worldX;
            int playerWorldY = player.worldY;

            int screenX = getWorldX() + xDiff - playerWorldX + PLAYER_SCREEN_X - effectWidth / 2;
            int screenY = getWorldY() + yDiff - playerWorldY + PLAYER_SCREEN_Y - effectHeight / 2;

            if (effectCounter > 2) {
                numEffectFrame = (numEffectFrame + 1) % 8;
                effectCounter = 0;
            }
            g2.drawImage(ImageLoader.imageManager.getEffectImage("LockOn", numEffectFrame), screenX, screenY, effectWidth, effectHeight, null);
        }
    }

    // Attack: projectile animation, Death: explosion animation
//    public void attackLongRange(Attack attack, Death death) {
//        getDirectionForAttacking();
//        String projectileDirection = this.direction;
//        int speed = 4;
//        int attackPoints = 1;
//        int numAnimationFrame = 3;
//        String name = "MONSTER_SLIME";
//        Projectile projectile = new Projectile(playing, name, worldX, worldY, projectileDirection, speed, attackPoints, numAnimationFrame);
//        playing.getProjectileManager().addProjectile(projectile);
//    }

    public void getHurt(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            currentState = EntityState.DEATH;
        }
    }
}
