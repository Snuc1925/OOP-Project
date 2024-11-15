package entities.monsters;

import enitystates.*;
import static enitystates.EntityState.*;
import entities.Player;
import gamestates.Playing;

import java.awt.*;
import java.util.Random;

import static utils.Constants.Screen.TILE_SIZE;

public class Samurai extends Monster{
    int currentPhase = 1;
    public Idle transform, idlePhase1, idlePhase2;
    public Walk walkPhase1, walkPhase2;
    public Attack attack1Phase1, attack2Phase1, attack1Phase2, attack2Phase2;
    public Death deathPhase2;
    int attackType = 2;
    public Samurai(Playing playing, int worldX, int worldY) {
        super("Samurai", playing, 8 * TILE_SIZE, 6 * TILE_SIZE);
        this.worldX = worldX;
        this.worldY = worldY;

        // Phase1's boxes, same as Phase2's boxes
        solidArea = new Rectangle(3 * TILE_SIZE + TILE_SIZE/2, 4 * TILE_SIZE + TILE_SIZE/2, TILE_SIZE,3 * TILE_SIZE/2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        visionBox = new Rectangle(-4 * TILE_SIZE, 0, 16 * TILE_SIZE, 10 * TILE_SIZE);
        hitBox = new Rectangle(3 * TILE_SIZE, 3 * TILE_SIZE, 2 * TILE_SIZE,3 * TILE_SIZE);
        attackBox = new Rectangle(TILE_SIZE, 3 * TILE_SIZE, 6 * TILE_SIZE, 4 * TILE_SIZE);

        // State
        transform = new Idle(this, 20, 5, "PHASE1_TRANSFORM");
        idlePhase1 = new Idle(this, 6, 5, "PHASE1_IDLE");
        idlePhase2 = new Idle(this, 6, 5, "PHASE2_IDLE");
        walkPhase1 = new Walk(this, 8, 5, "PHASE1_WALK");
        walkPhase2 = new Walk(this, 8, 5, "PHASE2_WALK");
        attack1Phase1 = new Attack(this, 18, 5, "PHASE1_ATTACK1");
        attack1Phase2 = new Attack(this, 20, 5, "PHASE2_ATTACK1");
        attack2Phase1 = new Attack(this, 6, 5, "PHASE1_ATTACK2");
        attack2Phase2 = new Attack(this, 7, 5, "PHASE2_ATTACK2");
        deathPhase2 = new Death(this, 29, 10, "PHASE2_DEATH");

        maxHealth = 50;
        currentHealth = maxHealth;
        attackPoints = 2;
        attackRate = 90;
        speed = 5;
    }

    @Override
    public void update() {
//        System.out.println(currentState);
        if (currentPhase == 1) {
            switch (currentState) {
                case IDLE:
                    idlePhase1.update(this);
                    image = idlePhase1.getImage();
                    break;
                case WALK:
                    walkPhase1.update(this);
                    image = walkPhase1.getImage();
                    break;
                case ATTACK:
                    if (attackType == 1) {
                        attack1();
                        image = attack1Phase1.getImage();
                    } else {
                        attack2();
                        image = attack2Phase1.getImage();
                    }
                    break;
                case DEATH:
                    image = transform.getImage();
                    if (transform.numAnimationFrames + 1 == transform.totalAnimationFrames) {
                        switchPhase();
                    }
                    break;
            }
        } else {
            switch (currentState) {
                case IDLE:
                    idlePhase2.update(this);
                    image = idlePhase2.getImage();
                    break;
                case WALK:
                    walkPhase2.update(this);
                    image = walkPhase2.getImage();
                    break;
                case ATTACK:
                    if (attackType == 1) {
                        attack1();
                        image = attack1Phase2.getImage();
                    } else {
                        attack2();
                        image = attack2Phase2.getImage();
                    }
                    break;
                case DEATH:
                    image = deathPhase2.getImage();
                    break;
            }
        }
    }
    Random random = new Random();
    public void changeAttackType() {
        attackType = random.nextInt(2) + 1;
//        attackType = 2;
    }

    int frameCounter = 0;
    public void attack1() {
        Player player = playing.getPlayer();
        frameCounter++;
        getDirectionForAttacking();
        int totalFrame, totalAnimationFrames, frameDuration;
        if (currentPhase == 1) {
            totalAnimationFrames = attack1Phase1.totalAnimationFrames;
            frameDuration = attack1Phase1.frameDuration;
        } else {
            totalAnimationFrames = attack1Phase2.totalAnimationFrames;
            frameDuration = attack1Phase2.frameDuration;
        }
        totalFrame = totalAnimationFrames * frameDuration;

        if (frameCounter == 4 * frameDuration || frameCounter == 10 * frameDuration || frameCounter == 16 * frameDuration) {
            if (canAttack(false))
                player.getHurt(attackPoints);
        }
        if (frameCounter == totalFrame) {
            changeAttackType();
            currentState = IDLE;
            frameCounter = 0;
        }
    }

    int frameCounter2 = 0;
    public void attack2() {
        frameCounter2++;
        getDirectionForAttacking();
        int totalFrame, totalAnimationFrames, frameDuration;
        if (currentPhase == 1) {
            totalAnimationFrames = attack2Phase1.totalAnimationFrames;
            frameDuration = attack2Phase1.frameDuration;
        } else {
            totalAnimationFrames = attack2Phase2.totalAnimationFrames;
            frameDuration = attack2Phase2.frameDuration;
        }
        totalFrame = totalAnimationFrames * frameDuration;
        if (frameCounter2 == 4 * frameDuration) {
            if (canAttack(false))
                playing.getPlayer().getHurt(attackPoints * 2);
        }
        if (frameCounter2 == totalFrame) {
            attackType = 1;
            currentState = IDLE;
            frameCounter2 = 0;
        }
    }

    public void switchPhase() {
        maxHealth = 100;
        currentHealth = maxHealth;
        attackRate = 60;
        attackPoints = 3;

        currentPhase = 2;
        currentState = IDLE;
        frameCounter = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        super.drawLockOn(g2, 4 * TILE_SIZE, 9 * TILE_SIZE / 2, 0, - TILE_SIZE);

        int healthBarWidth = (int) ( 2 * TILE_SIZE * ((double) currentHealth / maxHealth));
        if (healthBarWidth > 0) {
            g2.setColor(Color.RED);
            g2.fillRect(getScreenX() + 3 * TILE_SIZE, getScreenY() + 5 * TILE_SIZE / 2, healthBarWidth, 4 * 3);
        }

    }

    @Override
    public int getWorldY() {
        return worldY + height - TILE_SIZE / 2;
    }

    @Override
    public void move() {
        getDirectionForAttacking();
        super.move();
    }

}
