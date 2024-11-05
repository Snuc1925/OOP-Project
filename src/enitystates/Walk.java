package enitystates;

import entities.Player;
import entities.monsters.Slime;
import entities.Sprite;
import inputs.KeyboardInputs;

import static java.lang.Math.abs;
import static utils.Constants.Screen.TILE_SIZE;

public class Walk extends EntityStateMethods{
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
        entity.move();

        if (entity instanceof Slime slime) {
            if (abs(player.getWorldX() - entity.getWorldX()) < TILE_SIZE * 2 && abs(player.getWorldY() - entity.getWorldY()) < TILE_SIZE * 2) {
                entity.currentState = EntityState.ATTACK;
                return;
            }
            slime.stateChanger();
        }
    }

    public void update(Player player, KeyboardInputs keyboardInputs) {
        player.speed = 4;
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
        if (!keyboardInputs.spacePressed || player.currentWeapon.equals("NORMAL")) {
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
