package entities.monsters.bosses;

import effect.*;
import enitystates.*;
import gamestates.Playing;
import entities.Player;
import inputs.KeyboardInputs;
import utils.HelpMethods;
import utils.ImageLoader;

import java.awt.*;
import java.util.Random;

import static inputs.KeyboardInputs.isPressedValid;
import static utils.Constants.Screen.*;

public class SkeletonReaper extends Boss {
    Attack normalAttack, castAttack;
    Brushed brushed;
    Portal[] portals;
    ElectricBurst electricBurst = null;
    Dash dash = null;

    public SkeletonReaper(Playing playing, int worldX, int worldY) {
        super("SkeletonReaper", playing, 11 * TILE_SIZE + TILE_SIZE / 2, 5 * TILE_SIZE);

        this.worldX = worldX;
        this.worldY = worldY;
        solidArea = new Rectangle(5 * TILE_SIZE, 3 * TILE_SIZE + TILE_SIZE / 2, TILE_SIZE, 3 * TILE_SIZE / 2);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackBox = new Rectangle(0, 3 * TILE_SIZE, width, 3 * TILE_SIZE + TILE_SIZE / 2);
        visionBox = new Rectangle(- 5 * TILE_SIZE, - 4 * TILE_SIZE, width + 10 * TILE_SIZE, height + 8 * TILE_SIZE);
        hitBox = new Rectangle(4 * TILE_SIZE, 3 * TILE_SIZE, 3 * TILE_SIZE, 2 * TILE_SIZE);
        attackRate = 110;

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
        portals = new Portal[3];
    }

    @Override
    public void attack() {
        if (attack == normalAttack) normalAttack();
        else {
            castAttack();
        }
    }

    int smCnt = 0;
    private void castAttack() {
        Player player = playing.getPlayer();
        frameCounter++;
        getDirectionForAttacking();
        int totalFrame = castAttack.totalAnimationFrames * castAttack.frameDuration;
        if (frameCounter == totalFrame) {
            frameCounter = 0;
            if (currentHealth < maxHealth / 2 && smCnt < 2) {
                smCnt++;
                portals[0] = new Portal(this, getWorldX() - TILE_SIZE * 2, getWorldY(), 0);
                portals[1] = new Portal(this, getWorldX() + TILE_SIZE * 2, getWorldY(), 1);
//                portals[2] = new Portal(this, getWorldX(), getWorldY() + TILE_SIZE * 2, 2);
            }
            if (electricBurst == null)
                electricBurst = new ElectricBurst(this, player.getWorldX(), player.getWorldY(), 0);
            attack = normalAttack;
            currentState = EntityState.IDLE;
        }
    }

    int frameCounter = 0, attack1Counter = 0, numAttackToCast = 2;
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
            attack1Counter++;
            if (attack1Counter % numAttackToCast == 0) {
                attack = castAttack;
            }
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

    Random random = new Random();
    boolean isDialogueDraw = false;
    @Override
    public void update() {
        brushed.update();
        if (currentHealth < maxHealth / 2) {
            if (!isDialogueDraw) {
                currentState = EntityState.IDLE;
            }
            else if (currentState == EntityState.WALK && dash == null) {
                KeyboardInputs kb = playing.getGame().getKeyboardInputs();
                if (KeyboardInputs.isPressedValid("attack", kb.attackPressed) && random.nextInt(3) == 1)
                    dash = new Dash(this, 20);
            }
        }
        super.update();

        if (electricBurst != null) electricBurst.update();
        for (Portal portal : portals)
            if (portal != null) portal.update();

        if (dash != null) {
            speed = 5;
            dash.update();
        }
        else speed = 4;
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

        if (electricBurst != null) electricBurst.draw(g2);
        for (Portal portal : portals)
            if (portal != null) portal.draw(g2);

        if (dash != null) dash.draw(g2);
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

    public void removeElectricBurst(int index) {
        electricBurst = null;
    }
    public void removePortal(int index) {
        portals[index] = null;
    }

    @Override
    public void removeDash() {
        dash = null;
    }

    @Override
    public void getHurt(int damage) {
        if (dash == null) super.getHurt(damage);
    }

    String[] dialogues = {
         "Ugh...",
         "You are really strong",
         "But that is not enough to defeat me!!",
         "Pay attention, this is my REAL POWER!"
    };

    public void drawDialogue(Graphics2D g2) {
        if (currentHealth < maxHealth / 2) {
            if (!isDialogueDraw) {
                playing.getGame().getUI().drawDialogueScreen(talk(2), g2);
            }
        }
//        else if (currentHealth == maxHealth && canSeePlayer()) {
//            if (!isDialogueDraw) {
//                playing.getGame().getUI().drawDialogueScreen(talk(1), g2);
//            }
//        }
    }

    int dialogueCounter = 0;
    public String talk(int dialogueTpe) {
        KeyboardInputs kb = playing.getGame().getKeyboardInputs();
        Player player = playing.getPlayer();

        // Make player idle
        player.currentState = EntityState.IDLE;

        if (isPressedValid("enter", kb.enterPressed)) {
            dialogueCounter++;
            if (dialogueCounter >= dialogues.length) {
                isDialogueDraw = true;
                dialogueCounter = 0;
                return null;
            }
        }
        return dialogues[dialogueCounter];
    }
}
