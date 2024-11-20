package entities.monsters.bosses;

import effect.Brushed;
import enitystates.*;
import gamestates.Playing;
import entities.Player;
import utils.HelpMethods;
import utils.ImageLoader;

import java.awt.*;

import static utils.Constants.Screen.*;

public class SkeletonReaper extends Boss {
    Attack normalAttack, castAttack;
    String currentAttackType = "NORMAL";
    Brushed brushed;

    public SkeletonReaper(Playing playing, int worldX, int worldY) {
        super("SkeletonReaper", playing, 11 * TILE_SIZE + TILE_SIZE / 2, 5 * TILE_SIZE);

        solidArea = new Rectangle(5 * TILE_SIZE, 3 * TILE_SIZE + TILE_SIZE / 2, TILE_SIZE, 3 * TILE_SIZE / 2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackBox = new Rectangle(0, 3 * TILE_SIZE, width, 3 * TILE_SIZE + TILE_SIZE / 2);
        visionBox = new Rectangle(- 5 * TILE_SIZE, - 4 * TILE_SIZE, width + 10 * TILE_SIZE, height + 8 * TILE_SIZE);
        hitBox = new Rectangle(4 * TILE_SIZE, 3 * TILE_SIZE, 3 * TILE_SIZE, 2 * TILE_SIZE);
        attackRate = 100;

        maxHealth = 100;
        currentHealth = maxHealth;
        attackPoints = 4;
        speed = 4;

        normalAttack = new Attack(this, 14, 5);
        castAttack = new Attack(this, 8, 10, "CAST");
        death = new Death(this, 14, 10);
        idle = new Idle(this, 10, 5);
        walk = new Walk(this, 16, 5);
        attack = normalAttack;

        brushed = new Brushed(this, worldX + 58 * SCALE, worldY + 62 * SCALE);
    }

    @Override
    public void attack() {
        if (attack == normalAttack) normalAttack();
        else {
            castAttack();
        }
    }

    private void castAttack() {

    }


    int frameCounter = 0;
    private void normalAttack() {
        Player player = playing.getPlayer();
        frameCounter++;
        getDirectionForAttacking();
        int totalFrame = normalAttack.totalAnimationFrames * normalAttack.frameDuration;
        if (frameCounter == 6 * normalAttack.frameDuration) {
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
        return worldY + solidArea.y + solidArea.height / 2;
    }

    @Override
    public int getWorldX() {
        return worldX + solidArea.x + solidArea.width / 2;
    }

    @Override
    public void update() {
        brushed.update();
        super.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        brushed.draw(g2);
        super.draw(g2);

        super.drawLockOn(g2, 4 * TILE_SIZE, 9 * TILE_SIZE / 2, 0, - TILE_SIZE);

        int healthBarWidth = (int) ( 2 * TILE_SIZE * ((double) currentHealth / maxHealth));
        if (healthBarWidth > 0) {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX() + 5 * TILE_SIZE - 5, getScreenY() + TILE_SIZE, healthBarWidth, 4 * 3);
        }

    }

    @Override
    public void drawBossIntro(Graphics2D g2) {
        if (bossImage == null) {
            bossImage = ImageLoader.imageManager.getMonsterImage(
                    "SkeletonReaper", "Idle", "Left",
                    2, width, height);
            bossImage = HelpMethods.scaleImage(bossImage, 1296f / width);
            imageX = SCREEN_WIDTH - bossImage.getWidth() + TILE_SIZE * 10;
        }
        bossIntro(g2, "The Reaper", bossImage);
    }

}
