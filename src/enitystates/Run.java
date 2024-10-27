package enitystates;

import entities.Sprite;
import inputs.KeyboardInputs;
import entities.Player;

public class Run extends EntityStateMethods{
    public Run(Sprite entity, int totalAnimationFrames, int frameDuration) {
        super(entity, totalAnimationFrames, frameDuration);
        state = "RUN";
    }

    public Run(Sprite entity) {
        super(entity);
        state = "RUN";
    }

    @Override
    public void update(Sprite entity) {

    }
    public void update(Player player, KeyboardInputs keyboardInputs) {
        player.speed = 5;
        player.isIdling = false;
        if (keyboardInputs.upPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "up_left";
            else if (keyboardInputs.rightPressed)
                player.direction = "up_right";
            else player.direction = "up";
        } else if (keyboardInputs.downPressed) {
            if (keyboardInputs.leftPressed)
                player.direction = "down_left";
            else if (keyboardInputs.rightPressed)
                player.direction = "down_right";
            else player.direction = "down";
        } else if (keyboardInputs.leftPressed)
            player.direction = "left";
        else if (keyboardInputs.rightPressed)
            player.direction = "right";
        else player.isIdling = true;

        player.getPlaying().getGame().getCollisionChecker().checkTile(player);

        // Condition to change player state
        stateChanger(player, keyboardInputs);

        if (!player.collisionOn && !player.isIdling) {
            player.move();
        }

    }

    public void stateChanger(Player player, KeyboardInputs keyboardInputs) {
        if (player.isIdling) {
            player.currentState = EntityState.IDLE;
        }
        if (!keyboardInputs.shiftPressed && !player.isIdling) {
            player.currentState = EntityState.WALK;
        }
    }
}
