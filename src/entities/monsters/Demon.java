package entities.monsters;

import enitystates.*;
import gamestates.Playing;
import entities.Player;

import java.awt.*;

import static utils.Constants.Screen.*;

public class Demon extends Monster{
    public Demon(Playing playing, int worldX, int worldY) {
        super("Demon", "monster/Demon/idle/left/1", playing, 288 * SCALE, 160 * SCALE);
        solidArea = new Rectangle(7 * TILE_SIZE, 7 * TILE_SIZE, 4 * TILE_SIZE, 3 * TILE_SIZE);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.worldX = worldX;
        this.worldY = worldY;

        attackBox = new Rectangle(TILE_SIZE, 5 * TILE_SIZE, 16 * TILE_SIZE, 6 * TILE_SIZE);
        visionBox = new Rectangle(0, 0, 18 * TILE_SIZE, 16 * TILE_SIZE);
        hitBox = (Rectangle) solidArea.clone();

        maxHealth = 50;
        currentHealth = maxHealth;
        attackPoints = 4;
        speed = 4;

        attack = new Attack(this, 15, 5);
        idle = new Idle(this, 6, 10);
        death = new Death(this, 23, 10);
        walk = new Walk(this, 12, 5);
    }

    int frameCounter = 0;
    public void attack() {
        Player player = playing.getPlayer();
        frameCounter++;
        getDirectionForAttacking();
        int totalFrame = attack.totalAnimationFrames * attack.frameDuration;
        if (frameCounter == 10 * attack.frameDuration && player.dash == null) {
            if (canAttack(false))
                player.getHurt(attackPoints);
        }
        if (frameCounter == totalFrame) {
            currentState = EntityState.IDLE;
            frameCounter = 0;
        }
    }

    @Override
    public void move() {
        getDirectionForAttacking();
        super.move();
    }

    @Override
    public int getWorldY() {
        return worldY + height / 3 * 2;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        // Draw health bar
        int healthBarWidth = (int) ( 4 * TILE_SIZE * ((double) currentHealth / maxHealth));
        if (healthBarWidth > 0) {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX() + 7 * TILE_SIZE, getScreenY() + 4 * TILE_SIZE, healthBarWidth, 4 * 3);
        }

        super.drawLockOn(g2, 8 * TILE_SIZE, 10 * TILE_SIZE, 0, 0);
    }
}
