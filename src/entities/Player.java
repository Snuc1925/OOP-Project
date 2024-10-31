package entities;

import enitystates.*;
import gamestates.Playing;
import inputs.KeyboardInputs;

import java.awt.*;

import static utils.Constants.Player.*;
import static utils.Constants.Screen.*;

public class Player extends Sprite {
    // Player's states
    public Attack attack;
    Idle idle;
    Run run;
    Walk walk;

    // Player's attributes
    public int maxHealth, maxArmor, maxMana, currentArmor, currentHealth, currentMana;
    public int attackPointSpear, attackPointGun;

    // Player's weapons
    public String currentWeapon = "NORMAL";


    public Player(Playing playing) {
        super("Player", "player/Idle/Normal/down/1", playing, PLAYER_IMAGE_WIDTH, PLAYER_IMAGE_HEIGHT);
        setDefaultValues();
        attack = new Attack(this);
        idle = new Idle(this);
        run = new Run(this);
        walk = new Walk(this);


    }

    public void setDefaultValues() {
        solidArea = new Rectangle();
        solidArea.setBounds(18 * SCALE, 32 * SCALE, 13 * SCALE, 12 * SCALE);
        worldX = TILE_SIZE * 5;
        worldY = TILE_SIZE * 5;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        speed = 4;
        maxArmor = 10;
        maxHealth = 12;
        maxMana = 150;
        currentArmor = 8;
        currentHealth = 5;
        currentMana = 102;
        attackPointSpear = 3;
        attackPointGun = 2;

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
        }
    }

    public void lockOn() {
        int angle = getAngleMouse();
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
        for (Monster entity : entities) {
            entity.isBeingLockOn = false;
            int newDistance = (this.getWorldY() - entity.getWorldY()) * (this.getWorldY() - entity.getWorldY()) +
                    (this.getWorldX() - entity.getWorldX()) * (this.getWorldX() - entity.getWorldX());
            if (newDistance < distance && entity.isOnTheScreen()) {
                lockedMonster = entity;
                distance = newDistance;
                int dx = -entity.getWorldX() + this.getWorldX();
                int dy = -entity.getWorldY() + this.getWorldY();
                angle = (int) (Math.atan2(dy, dx) * 180 / Math.PI);
            }
        }
        if (lockedMonster == null) return 181;
        lockedMonster.isBeingLockOn = true;
        return angle;
    }
}
