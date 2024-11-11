package enitystates;

import effect.Dash;
import entities.Player;
import entities.monsters.*;
import entities.Sprite;
import inputs.KeyboardInputs;

import static java.lang.Math.abs;
import static utils.Constants.Screen.TILE_SIZE;

public class Walk extends EntityStateMethods{

    public Walk(Sprite entity, int totalAnimationFrames, int frameDuration, String state) {
        super(entity, totalAnimationFrames, frameDuration);
        this.state = state;
    }

    public Walk(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "WALK";
    }

    public Walk(Sprite entity) {
        super(entity);
        state = "WALK";
    }

    @Override
    public void update(Sprite entity) {
        Player player = entity.getPlaying().getPlayer();

        if (entity instanceof Slime slime) {
            entity.move();
            if (slime.canAttack(true)) {
                entity.currentState = EntityState.ATTACK;
            }
            else slime.stateChanger();
        }

        if (entity instanceof SwordKnight sw) {
            if (!sw.canSeePlayer()) {
                sw.currentState = EntityState.IDLE;
            }
            else {
                if (sw.canAttack(true)) sw.currentState = EntityState.ATTACK;
                else sw.move();
            }
        }

        if (entity instanceof Demon demon) {
            if (!demon.canSeePlayer()) {
                demon.currentState = EntityState.IDLE;
            }
            else {
                if (demon.canAttack(true)) demon.currentState = EntityState.ATTACK;
                else demon.move();
            }
        }
        if (entity instanceof BringerOfDeath bringerOfDeath) {
            if (!bringerOfDeath.canSeePlayer()) {
                bringerOfDeath.currentState = EntityState.IDLE;
            }
            else {
                if (bringerOfDeath.canAttack(true)) bringerOfDeath.currentState = EntityState.ATTACK;
                else bringerOfDeath.move();
            }
        }
        if (entity instanceof Samurai samurai) {
            if (!samurai.canSeePlayer()) {
                samurai.currentState = EntityState.IDLE;
            }
            else {
                if (samurai.canAttack(true)) samurai.currentState = EntityState.ATTACK;
                else samurai.move();
            }
        }

    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        player.speed = player.getSpeed();
        player.isIdling = false;
        if (keyboardInputs.upPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "left_up";
            else if (keyboardInputs.rightPressed)
                player.direction = "right_up";
            else player.direction = "up";
        } else if (keyboardInputs.downPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "left_down";
            else if (keyboardInputs.rightPressed)
                player.direction = "right_down";
            else player.direction = "down";
        } else if (keyboardInputs.leftPressed)
            player.direction = "left";
        else if (keyboardInputs.rightPressed)
            player.direction = "right";
        else player.isIdling = true;

        player.getPlaying().getGame().getCollisionChecker().checkTile(player);

        // Condition for changing player state
        stateChanger(player, keyboardInputs);

        if (player.isIdling) player.currentState = EntityState.IDLE;
        if (!player.collisionOn && !player.isIdling) {
            player.move();
        }

    }
    public void stateChanger(Player player, KeyboardInputs keyboardInputs) {
        if (!keyboardInputs.attackPressed || player.currentWeapon.equals("NORMAL")) {
            if (player.isIdling) player.currentState = EntityState.WALK;
            if (!player.isIdling && keyboardInputs.shiftPressed) player.currentState = EntityState.RUN;
        }
        else {
            if (player.currentWeapon.equals("SPEAR") || player.currentMana - player.manaCostPerShot >= 0) {
                player.attack.lastState = EntityState.WALK;
                player.currentState = EntityState.ATTACK;
            }
        }
    }
}
