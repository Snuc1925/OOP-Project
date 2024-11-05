package entities.monsters;

import enitystates.Attack;
import enitystates.Death;
import enitystates.EntityState;
import enitystates.Idle;
import gamestates.Playing;
import entities.*;

import java.awt.*;

import static utils.Constants.Screen.SCALE;
import static utils.Constants.Screen.TILE_SIZE;

public class PlantMelee extends Monster {
    public Rectangle attackBox;
    public int knockbackDuration = 15;
    public PlantMelee(Playing playing, int worldX, int worldY) {
        super("PlantMelee", "monster/PlantMelee/Idle/All/1", playing, 96 * SCALE, 96 * SCALE);
        currentHealth = 20;
        maxHealth = 20;
        attackPoints = 3;
        attack = new Attack(this, 11, 5);
        idle = new Idle(this, 14, 10);
        death = new Death(this, 6, 10);
        attackBox = new Rectangle(0, 0, width, height);

        solidArea = new Rectangle(2 * TILE_SIZE, 2 * TILE_SIZE, 2 * TILE_SIZE, 2 * TILE_SIZE);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.worldX = worldX;
        this.worldY = worldY;
    }

    @Override
    public int getWorldX() {
        return worldX + TILE_SIZE * 3;
    }

    @Override
    public int getWorldY() {
        return worldY + TILE_SIZE * 3;
    }

    int cooldown = 0;
    public boolean canAttack() {
        boolean result;
        Player player = playing.getPlayer();
        player.solidArea.x += player.worldX;
        player.solidArea.y += player.worldY;
        attackBox.x += worldX;
        attackBox.y += worldY;

        result = player.solidArea.intersects(attackBox);

        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        attackBox.x = 0;
        attackBox.y = 0;

        if (result) {
            cooldown++;
            if (cooldown >= 180) {
                cooldown = 0;
                return true;
            }
        } else cooldown = 0;

        return false;
    }

    int frameCounter = 0;
    public void attack() {
        Player player = playing.getPlayer();
        frameCounter++;
        int totalFrame = attack.totalAnimationFrames * attack.frameDuration;
        if (frameCounter >= totalFrame - knockbackDuration) {
            getDirectionForAttacking();
            player.knock_back(10, direction);
            if (frameCounter == totalFrame) {
                player.currentHealth -= attackPoints;
                if (player.currentHealth <= 0) {
                    player.currentState = EntityState.DEATH;
                }
                currentState = EntityState.IDLE;
                frameCounter = 0;
            }

        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        // Draw health bar
        int healthBarWidth = (int) ( 2 * TILE_SIZE * ((double) currentHealth / maxHealth));
        if (healthBarWidth > 0) {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX() + 2 * TILE_SIZE, getScreenY() + TILE_SIZE, healthBarWidth, 4 * 3);
        }
    }


}
