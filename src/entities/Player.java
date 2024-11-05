package entities;

import enitystates.*;
import entities.monsters.Monster;
import gamestates.Playing;
import inputs.KeyboardInputs;
import utils.HelpMethods;

import java.awt.*;

import static utils.Constants.Player.*;
import static utils.Constants.Screen.*;

public class Player extends Sprite {
    // Player's states
    public Attack attack;
    Idle idle;
    Run run;
    Walk walk;
    Death death;

    // Player's attributes
    public int maxHealth, maxArmor, maxMana, currentArmor, currentHealth, currentMana;
    public int attackPointSpear, attackPointGun, manaCostPerShot;
    public int spearAttackRange, gunAttackRange;


    // Player's weapons
    public String currentWeapon = "NORMAL";


    public Player(Playing playing) {
        super("Player", "player/Idle/Normal/down/1", playing, PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT);
        setDefaultValues();
        attack = new Attack(this);
        idle = new Idle(this);
        run = new Run(this);
        walk = new Walk(this);
        death = new Death(this);
    }

    public void setDefaultValues() {
        solidArea = new Rectangle();
        solidArea.setBounds(18 * SCALE, 32 * SCALE, 13 * SCALE, 12 * SCALE);
        worldX = TILE_SIZE * 10;
        worldY = TILE_SIZE * 10;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        speed = 4;
        maxArmor = 10;
        maxHealth = 1000;
        maxMana = 50;
        currentArmor = maxArmor;
        currentHealth = maxHealth;
        currentMana = maxMana;
        attackPointSpear = 3;
        attackPointGun = 2;
        spearAttackRange = 3 * TILE_SIZE;
        gunAttackRange = 10 * TILE_SIZE;
        manaCostPerShot = 1;
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.drawImage(image, PLAYER_SCREEN_X, PLAYER_SCREEN_Y, null);
//        g2.drawRect(tempScreenX + solidArea.x, tempScreenY + solidArea.y, solidArea.width, solidArea.height);

    }

    int frameCounter = 0;
    int weaponSwitchDelayed = 60;
    @Override
    public void update() {
        KeyboardInputs keyboardInputs = playing.getGame().getKeyboardInputs();
        frameCounter++;
        if (keyboardInputs.enterPressed && frameCounter > weaponSwitchDelayed) {
            switch (currentWeapon) {
                case "NORMAL" -> currentWeapon = "SPEAR";
                case "SPEAR" -> currentWeapon = "GUN";
                case "GUN" -> currentWeapon = "NORMAL";
            }
            frameCounter = 0;
        }

        switch (currentState) {
            case IDLE:
                idle.update(this, keyboardInputs);
                image = idle.getImage();
                break;
            case RUN:
                run.update(this, keyboardInputs);
                image = run.getImage();
                break;
            case WALK:
                walk.update(this, keyboardInputs);
                image = walk.getImage();
                break;
            case ATTACK:
                attack.update(this, keyboardInputs);
                image = attack.getImage();
                break;
            case DEATH:
                death.update(this);
                image = death.getImage();
                break;
        }
    }

    public void lockOn() {
        int angle = getAngleAuto();
        if (angle == 181) return;
        if (angle <= 15 && angle >= -15) this.direction = "left";
        else if (angle < -15 && angle > -75) this.direction = "left_down";
        else if (angle <= -75 && angle >= -105) this.direction = "down";
        else if (angle <= -105 && angle >= -170) this.direction = "right_down";
        else if (angle > 105 && angle < 170) this.direction = "right_up";
        else if (angle >= 75 && angle <= 105) this.direction = "up";
        else if (angle > 15 && angle < 75) this.direction = "left_up";
        else this.direction = "right";
    }
    public int getAngleMouse() {
        KeyboardInputs keyboardInputs = playing.getGame().getKeyboardInputs();
        int mouseX = keyboardInputs.getMouseX();
        int mouseY = keyboardInputs.getMouseY();
        int angle;
        int dx = -mouseX + PLAYER_SCREEN_X + TILE_SIZE * 3 / 2;
        int dy = -mouseY + PLAYER_SCREEN_Y + TILE_SIZE * 3 / 2;
        angle = (int) (Math.atan2(dy, dx) * 180 / Math.PI);
        return angle;
    }

    private int getAngleAuto() {
        int distance = Integer.MAX_VALUE;
        int angle = 0;
        Monster lockedMonster = null;
        Monster[] entities = this.getPlaying().monsters;
        for (Monster entity : entities)
            if (entity != null) {
                entity.isBeingLockOn = false;
                if (entity.currentState != EntityState.DEATH && HelpMethods.canSeeEntity(playing, this, entity)) {
                    int newDistance = (this.getWorldY() - entity.getWorldY()) * (this.getWorldY() - entity.getWorldY()) +
                            (this.getWorldX() - entity.getWorldX()) * (this.getWorldX() - entity.getWorldX());
                    switch (currentWeapon) {
                        case "SPEAR":
                            if (newDistance > spearAttackRange * spearAttackRange) continue;
                            break;
                        case "GUN":
                            if (newDistance > gunAttackRange * gunAttackRange) continue;
                            break;
                    }
                    if (newDistance < distance && entity.isOnTheScreen()) {
                        lockedMonster = entity;
                        distance = newDistance;
                        int dx = -entity.getWorldX() + this.getWorldX();
                        int dy = -entity.getWorldY() + this.getWorldY();
                        angle = (int) (Math.atan2(dy, dx) * 180 / Math.PI);
                    }
                }
            }
        if (lockedMonster == null) return 181;
        lockedMonster.isBeingLockOn = true;
        return angle;
    }

    @Override
    public int getWorldX() {
        return worldX + TILE_SIZE * 3 / 2;
    }

    @Override
    public int getWorldY() {
        return worldY + TILE_SIZE * 2;
    }
}
