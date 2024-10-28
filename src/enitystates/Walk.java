package enitystates;

import entities.Player;
import entities.Slime;
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

        if (entity.name.equals("Slime")) {
            if (abs(player.getWorldX() - entity.getWorldX()) < 3 * TILE_SIZE && abs(player.getWorldY() - entity.getWorldY()) < 3 * TILE_SIZE) {
                entity.currentState = EntityState.ATTACK;
                return;
            }
            Slime slime = (Slime) entity;
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
        if (player.isIdling) player.currentState = EntityState.WALK;
        if (!player.isIdling && keyboardInputs.shiftPressed) player.currentState = EntityState.RUN;
    }
}
