package entities.monsters;

import enitystates.*;
import entities.Player;
import gamestates.Playing;

import java.awt.*;

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

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        // Draw health bar
        int healthBarWidth = (int) (15 * 3 * ((double) currentHealth / maxHealth));
        if (healthBarWidth > 0) {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX(), getScreenY() + 5 * 3, healthBarWidth, 4 * 3);
        }
    }

    @Override
    public int getWorldX() {
        return worldX + TILE_SIZE / 2;
    }

    @Override
    public int getWorldY() {
        return worldY + TILE_SIZE * 3 / 2;
    }

}
